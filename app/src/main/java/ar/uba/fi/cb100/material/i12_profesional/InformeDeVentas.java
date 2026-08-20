package ar.uba.fi.cb100.material.i12_profesional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <b>Ejemplo integrador de la Unidad 12</b>: el reporte que te piden la
 * primera semana de trabajo. Una lista de ventas y las preguntas de siempre
 * — total, ranking, agrupados — respondidas con streams y collectors.
 * <p>
 * Fijate el patrón: los datos son un {@code record} inmutable, cada pregunta
 * es UN pipeline corto y legible, y los agrupados usan
 * {@code Collectors.groupingBy} — el {@code HashMap} de la U7 armado solo.
 */
public final class InformeDeVentas {

    public record Venta(String vendedor, String producto, int monto) {}

    private final List<Venta> ventas;

    public InformeDeVentas(List<Venta> ventas) {
        this.ventas = List.copyOf(ventas);         // copia defensiva e inmutable (U3)
    }

    /** La facturación total: reduce a un único número. */
    public int totalRecaudado() {
        return ventas.stream()
                .mapToInt(Venta::monto)
                .sum();
    }

    /** ¿Cuánto vendió cada uno? groupingBy = un Map armado por el stream. */
    public Map<String, Integer> totalPorVendedor() {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::vendedor,                       // la clave del mapa
                        TreeMap::new,                          // ordenado por nombre (U8)
                        Collectors.summingInt(Venta::monto))); // qué acumular por grupo
    }

    /** Ranking: los vendedores de mayor a menor facturación. */
    public List<String> rankingDeVendedores() {
        return totalPorVendedor().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
    }

    /** El producto con más operaciones (no más plata): groupingBy + counting. */
    public String productoMasVendido() {
        return ventas.stream()
                .collect(Collectors.groupingBy(Venta::producto, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("no hay ventas"));
    }

    /** Las ventas grandes, ordenadas de mayor a menor. */
    public List<Venta> ventasDesde(int montoMinimo) {
        return ventas.stream()
                .filter(venta -> venta.monto() >= montoMinimo)
                .sorted(Comparator.comparingInt(Venta::monto).reversed())
                .toList();
    }

    /** Promedio por operación (double: la división entera miente, U1B). */
    public double promedioPorVenta() {
        return ventas.stream()
                .mapToInt(Venta::monto)
                .average()
                .orElse(0.0);
    }

    public static void main(String[] args) {
        InformeDeVentas informe = new InformeDeVentas(List.of(
                new Venta("Ana", "cuaderno", 3500),
                new Venta("Beto", "lapicera", 1200),
                new Venta("Ana", "mochila", 25000),
                new Venta("Carla", "cuaderno", 3500),
                new Venta("Beto", "resma", 8000),
                new Venta("Ana", "lapicera", 1200),
                new Venta("Carla", "calculadora", 30000),
                new Venta("Beto", "cuaderno", 3500),
                new Venta("Carla", "resma", 8000),
                new Venta("Beto", "corrector", 900)));

        System.out.println(informe.totalRecaudado());       // 84800
        System.out.println(informe.totalPorVendedor());     // {Ana=29700, Beto=13600, Carla=41500}
        System.out.println(informe.rankingDeVendedores());  // [Carla, Ana, Beto]
        System.out.println(informe.productoMasVendido());   // cuaderno  (3 operaciones)
        System.out.println(informe.ventasDesde(8000));
        // [Venta[vendedor=Carla, producto=calculadora, monto=30000],
        //  Venta[vendedor=Ana, producto=mochila, monto=25000],
        //  Venta[vendedor=Beto, producto=resma, monto=8000],
        //  Venta[vendedor=Carla, producto=resma, monto=8000]]
        System.out.println(informe.promedioPorVenta());     // 8480.0
    }
}
