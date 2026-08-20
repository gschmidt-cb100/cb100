package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e07;

import ar.uba.fi.cb100.material.i12_profesional.InformeDeVentas.Venta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TECNICA: FACHADA FLUIDA Y PEREZOSA SOBRE STREAMS (mini motor de consultas).
 *
 * Un SELECT de SQL en Java: {@code Consulta.desde(ventas).donde(...)
 * .ordenadoPor(...).tomar(2).lista()}. La gracia esta en COMO se logra la
 * pereza sin reinventar streams: cada paso intermedio NO ejecuta nada,
 * solo guarda una funcion {@code Stream<T> -> Stream<T>} en una lista (las
 * funciones son valores, U12). Recien las operaciones terminales
 * ({@code lista()} / {@code agrupadoPor()}) abren el stream de la coleccion
 * y le aplican los pasos acumulados, uno tras otro.
 *
 * Es el mismo patron builder/fachada que usan JPA, jOOQ o LINQ: una API
 * chica y declarativa adelante, y toda la maquinaria (streams) escondida
 * atras. Cada llamada devuelve una Consulta NUEVA (inmutable, U3): una
 * consulta a medio armar se puede guardar y reusar sin miedo.
 *
 * @param <T> el tipo de las filas consultadas
 */
public final class Consulta<T> {

    private final Collection<T> datos;
    private final List<UnaryOperator<Stream<T>>> pasos;

    private Consulta(Collection<T> datos, List<UnaryOperator<Stream<T>>> pasos) {
        this.datos = datos;
        this.pasos = pasos;
    }

    /** Punto de partida: FROM coleccion. */
    public static <T> Consulta<T> desde(Collection<T> coleccion) {
        return new Consulta<>(coleccion, List.of());
    }

    /** WHERE: guarda el filtro como paso pendiente, no filtra nada aun. */
    public Consulta<T> donde(Predicate<? super T> condicion) {
        return conPaso(stream -> stream.filter(condicion));
    }

    /** ORDER BY: guarda el ordenamiento como paso pendiente. */
    public Consulta<T> ordenadoPor(Comparator<? super T> comparador) {
        return conPaso(stream -> stream.sorted(comparador));
    }

    /** LIMIT: guarda el corte como paso pendiente. */
    public Consulta<T> tomar(int cantidad) {
        return conPaso(stream -> stream.limit(cantidad));
    }

    /** Operacion TERMINAL: recien aca se ejecuta todo el pipeline. */
    public List<T> lista() {
        return ejecutar().toList();
    }

    /** Operacion TERMINAL: GROUP BY con el groupingBy de la U7/U12. */
    public <K> Map<K, List<T>> agrupadoPor(Function<? super T, ? extends K> clasificador) {
        return ejecutar().collect(Collectors.groupingBy(
                clasificador, LinkedHashMap::new, Collectors.toList()));
    }

    private Consulta<T> conPaso(UnaryOperator<Stream<T>> paso) {
        List<UnaryOperator<Stream<T>>> nuevos = new ArrayList<>(pasos);
        nuevos.add(paso);
        return new Consulta<>(datos, List.copyOf(nuevos));
    }

    private Stream<T> ejecutar() {
        Stream<T> stream = datos.stream();
        for (UnaryOperator<Stream<T>> paso : pasos) {
            stream = paso.apply(stream);
        }
        return stream;
    }

    public static void main(String[] args) {
        List<Venta> ventas = List.of(
                new Venta("Ana", "cuaderno", 3500),
                new Venta("Beto", "lapicera", 1200),
                new Venta("Ana", "mochila", 25000),
                new Venta("Carla", "cuaderno", 3500),
                new Venta("Beto", "resma", 8000),
                new Venta("Ana", "lapicera", 1200),
                new Venta("Carla", "calculadora", 30000),
                new Venta("Beto", "cuaderno", 3500),
                new Venta("Carla", "resma", 8000),
                new Venta("Beto", "corrector", 900));

        // SELECT * FROM ventas WHERE vendedor='Carla' ORDER BY monto DESC LIMIT 2
        System.out.println(Consulta.desde(ventas)
                .donde(venta -> venta.vendedor().equals("Carla"))
                .ordenadoPor(Comparator.comparingInt(Venta::monto).reversed())
                .tomar(2)
                .lista());

        // SELECT * FROM ventas GROUP BY producto
        Consulta.desde(ventas)
                .agrupadoPor(Venta::producto)
                .forEach((producto, grupo) ->
                        System.out.println(producto + " -> " + grupo.size() + " ventas"));
    }
}
