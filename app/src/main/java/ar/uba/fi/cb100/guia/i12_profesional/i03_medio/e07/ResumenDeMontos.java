package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e07;

import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * e07: estadísticas en UNA pasada con {@code IntSummaryStatistics}.
 *
 * <p>Pedir min, max, suma, promedio y cantidad con cinco pipelines
 * separados recorre los datos cinco veces. {@code summaryStatistics()}
 * acumula las cinco cosas en una sola pasada O(n): es el collector de
 * "resumen numérico" que la JDK ya trae hecho. En un dataset que no entra
 * en memoria o llega en streaming, esa diferencia no es estética.</p>
 */
public final class ResumenDeMontos {

    private final IntSummaryStatistics estadisticas;

    /** Calcula todo el resumen en una sola pasada por la lista. */
    public ResumenDeMontos(List<Integer> montos) {
        this.estadisticas = montos.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
    }

    public int minimo() {
        return estadisticas.getMin();
    }

    public int maximo() {
        return estadisticas.getMax();
    }

    public long suma() {
        return estadisticas.getSum();
    }

    public double promedio() {
        return estadisticas.getAverage();
    }

    public long cantidad() {
        return estadisticas.getCount();
    }

    /** Los montos de las ventas del apunte de la U12. */
    public static List<Integer> montosDelApunte() {
        return List.of(3500, 1200, 25000, 3500, 8000, 1200, 30000, 3500, 8000, 900);
    }

    public static void main(String[] args) {
        ResumenDeMontos resumen = new ResumenDeMontos(montosDelApunte());
        System.out.println("min      = " + resumen.minimo());    // 900
        System.out.println("max      = " + resumen.maximo());    // 30000
        System.out.println("suma     = " + resumen.suma());      // 84800
        System.out.println("promedio = " + resumen.promedio());  // 8480.0
        System.out.println("cantidad = " + resumen.cantidad());  // 10
    }
}
