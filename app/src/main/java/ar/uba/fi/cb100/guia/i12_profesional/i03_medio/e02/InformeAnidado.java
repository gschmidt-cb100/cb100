package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e02;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * e02: groupingBy anidado — un mapa de mapas en un solo pipeline.
 *
 * <p>La pregunta "¿cuánto vendió cada vendedor de cada producto?" pide una
 * tabla de dos niveles: vendedor → (producto → total). Con collectors se
 * arma anidando un {@code groupingBy} adentro de otro: el de afuera parte
 * por vendedor y el de adentro, por producto. Usamos {@code TreeMap} en los
 * dos niveles (U8) para que el reporte salga ordenado alfabéticamente; si
 * sólo importara buscar, un {@code HashMap} con sus O(1) alcanzaba (U7).</p>
 */
public final class InformeAnidado {

    public record Venta(String vendedor, String producto, int monto) {}

    private final List<Venta> ventas;

    public InformeAnidado(List<Venta> ventas) {
        this.ventas = List.copyOf(ventas);
    }

    /** Vendedor → (producto → total facturado de ese producto). */
    public Map<String, Map<String, Integer>> totalPorVendedorYProducto() {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::vendedor,
                        TreeMap::new,
                        Collectors.groupingBy(
                                Venta::producto,
                                TreeMap::new,
                                Collectors.summingInt(Venta::monto))));
    }

    /** Los datos del apunte de la U12. */
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
        InformeAnidado informe = new InformeAnidado(ventasDelApunte());
        informe.totalPorVendedorYProducto()
                .forEach((vendedor, porProducto) ->
                        System.out.println(vendedor + " -> " + porProducto));
        // Ana -> {cuaderno=3500, lapicera=1200, mochila=25000}
        // Beto -> {corrector=900, cuaderno=3500, lapicera=1200, resma=8000}
        // Carla -> {calculadora=30000, cuaderno=3500, resma=8000}
    }
}
