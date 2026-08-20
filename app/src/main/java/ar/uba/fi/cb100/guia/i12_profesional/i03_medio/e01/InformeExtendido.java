package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e01;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * e01: informe de ventas extendido — más preguntas sobre los datos del apunte.
 *
 * <p><b>Elección de estructuras:</b> los agrupados van a {@code TreeMap}
 * (U8: claves ordenadas alfabéticamente, cómodo para imprimir un reporte)
 * y el {@code Collectors.toMap} con función de merge resuelve "quedate con
 * la venta más grande" sin ordenar nada: una pasada O(n) con lookups O(1)
 * de hash (U7).</p>
 */
public final class InformeExtendido {

    /** Una venta: quién, qué y por cuánto. Record inmutable (U3). */
    public record Venta(String vendedor, String producto, int monto) {}

    private final List<Venta> ventas;

    public InformeExtendido(List<Venta> ventas) {
        this.ventas = List.copyOf(ventas);          // copia defensiva e inmutable
    }

    /** Cuánto se facturó por cada producto, ordenado por nombre (U8). */
    public Map<String, Integer> totalPorProducto() {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::producto,
                        TreeMap::new,
                        Collectors.summingInt(Venta::monto)));
    }

    /**
     * La venta más grande de cada vendedor. El truco es la función de
     * <b>merge</b> de {@code toMap}: cuando dos ventas caen en la misma
     * clave, se queda con la de mayor monto. Una pasada, sin ordenar.
     */
    public Map<String, Venta> mayorVentaPorVendedor() {
        return ventas.stream()
                .collect(Collectors.toMap(
                        Venta::vendedor,
                        venta -> venta,
                        BinaryOperator.maxBy(Comparator.comparingInt(Venta::monto)),
                        TreeMap::new));
    }

    /** Los vendedores cuyo total llega al umbral, en orden alfabético. */
    public List<String> vendedoresConTotalDesde(int umbral) {
        return totalPorVendedor().entrySet().stream()
                .filter(entrada -> entrada.getValue() >= umbral)
                .map(Map.Entry::getKey)
                .toList();                          // el TreeMap ya viene ordenado
    }

    /** Auxiliar: total por vendedor (mismo patrón que el apunte). */
    public Map<String, Integer> totalPorVendedor() {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::vendedor,
                        TreeMap::new,
                        Collectors.summingInt(Venta::monto)));
    }

    /** Los datos del apunte de la U12, para probar contra totales conocidos. */
    public static List<Venta> ventasDelApunte() {
        return List.of(
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
    }

    public static void main(String[] args) {
        InformeExtendido informe = new InformeExtendido(ventasDelApunte());
        System.out.println(informe.totalPorProducto());
        // {calculadora=30000, corrector=900, cuaderno=10500, lapicera=2400, mochila=25000, resma=16000}
        System.out.println(informe.mayorVentaPorVendedor());
        // Ana -> mochila 25000, Beto -> resma 8000, Carla -> calculadora 30000
        System.out.println(informe.vendedoresConTotalDesde(20000));  // [Ana, Carla]
    }
}
