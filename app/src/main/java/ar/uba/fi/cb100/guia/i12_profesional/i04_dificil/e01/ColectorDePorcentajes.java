package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e01;

import ar.uba.fi.cb100.material.i12_profesional.InformeDeVentas.Venta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;

/**
 * TECNICA: COLLECTOR PROPIO (Collector.of) EN UNA SOLA PASADA.
 *
 * Para calcular el porcentaje de facturacion de cada vendedor necesitas dos
 * numeros: cuanto vendio cada uno y cuanto se vendio EN TOTAL. La solucion
 * ingenua recorre la lista dos veces (una para el total, otra para los
 * parciales). Un {@link Collector} propio lo resuelve en UNA pasada: el
 * acumulador guarda las dos cosas a la vez (un {@code HashMap} de la U7 con
 * los totales por vendedor + el total general), y el <b>finisher</b> recien
 * al final divide parcial/total para obtener los porcentajes.
 *
 * Las cuatro piezas de {@code Collector.of}:
 * <ul>
 *   <li><b>supplier</b>: crea el acumulador vacio;</li>
 *   <li><b>accumulator</b>: suma una venta (al vendedor y al total);</li>
 *   <li><b>combiner</b>: une dos acumuladores (necesario si el stream se
 *       parte en paralelo, ver e09);</li>
 *   <li><b>finisher</b>: transforma el acumulador en el resultado final
 *       (aca: divide y arma un {@code TreeMap} de la U8 para salida
 *       ordenada y determinista).</li>
 * </ul>
 */
public final class ColectorDePorcentajes {

    /** Acumulador mutable: totales por vendedor + total general, juntos. */
    private static final class Acumulador {
        final Map<String, Double> porVendedor = new HashMap<>();
        double total = 0.0;

        void sumar(Venta venta) {
            porVendedor.merge(venta.vendedor(), (double) venta.monto(), Double::sum);
            total += venta.monto();
        }

        Acumulador combinarCon(Acumulador otro) {
            otro.porVendedor.forEach((vendedor, monto) ->
                    porVendedor.merge(vendedor, monto, Double::sum));
            total += otro.total;
            return this;
        }
    }

    /**
     * El collector: {@code Collector<Venta, ?, Map<String, Double>>} que
     * devuelve vendedor -&gt; porcentaje sobre el total (0 a 100).
     */
    public static Collector<Venta, ?, Map<String, Double>> porcentajePorVendedor() {
        return Collector.of(
                Acumulador::new,                       // supplier
                Acumulador::sumar,                     // accumulator
                Acumulador::combinarCon,               // combiner
                acumulador -> {                        // finisher: recien aca se divide
                    Map<String, Double> porcentajes = new TreeMap<>();
                    acumulador.porVendedor.forEach((vendedor, monto) ->
                            porcentajes.put(vendedor, 100.0 * monto / acumulador.total));
                    return porcentajes;
                });
    }

    private ColectorDePorcentajes() {
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

        Map<String, Double> porcentajes = ventas.stream().collect(porcentajePorVendedor());
        porcentajes.forEach((vendedor, porcentaje) ->
                System.out.printf("%-6s %6.2f%%%n", vendedor, porcentaje));
        // Ana ~35.02%, Beto ~16.04%, Carla ~48.94%
    }
}
