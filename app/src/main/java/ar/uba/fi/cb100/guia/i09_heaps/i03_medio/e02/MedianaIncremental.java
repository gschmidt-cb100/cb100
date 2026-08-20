package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e02;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * e02: mediana de un stream, actualizada con cada valor que llega.
 *
 * <p>El truco clásico de los dos heaps: partimos los valores vistos en dos
 * mitades.</p>
 * <ul>
 *   <li>{@code mitadBaja}: MAX-heap con la mitad menor (su raíz es el mayor
 *       de los chicos);</li>
 *   <li>{@code mitadAlta}: MIN-heap con la mitad mayor (su raíz es el menor
 *       de los grandes).</li>
 * </ul>
 *
 * <p>Si mantenemos los tamaños balanceados (difieren a lo sumo en 1), la
 * mediana está siempre en las raíces: es la raíz del heap más grande, o el
 * promedio de ambas raíces si empatan. Cada {@code agregar} cuesta O(log n)
 * y cada {@code mediana} O(1).</p>
 */
public final class MedianaIncremental {

    /** Mitad menor de los valores; max-heap, la raíz es su máximo. */
    private final PriorityQueue<Integer> mitadBaja =
            new PriorityQueue<>(Comparator.reverseOrder());

    /** Mitad mayor de los valores; min-heap, la raíz es su mínimo. */
    private final PriorityQueue<Integer> mitadAlta = new PriorityQueue<>();

    /**
     * Incorpora un valor al stream en O(log n).
     *
     * @param valor nuevo valor observado
     */
    public void agregar(int valor) {
        // 1. Elegimos mitad: si no supera al máximo de los chicos, va abajo.
        if (mitadBaja.isEmpty() || valor <= mitadBaja.peek()) {
            mitadBaja.offer(valor);
        } else {
            mitadAlta.offer(valor);
        }
        // 2. Rebalanceo: los tamaños no pueden diferir en más de 1.
        if (mitadBaja.size() > mitadAlta.size() + 1) {
            mitadAlta.offer(mitadBaja.poll());
        } else if (mitadAlta.size() > mitadBaja.size() + 1) {
            mitadBaja.offer(mitadAlta.poll());
        }
    }

    /**
     * Mediana de todos los valores agregados hasta ahora, en O(1).
     *
     * @return la mediana (promedio de los dos centrales si hay cantidad par)
     * @throws IllegalStateException si todavía no se agregó ningún valor
     */
    public double mediana() {
        if (cantidad() == 0) {
            throw new IllegalStateException("No hay valores todavía");
        }
        if (mitadBaja.size() > mitadAlta.size()) {
            return mitadBaja.peek();
        }
        if (mitadAlta.size() > mitadBaja.size()) {
            return mitadAlta.peek();
        }
        return (mitadBaja.peek() + mitadAlta.peek()) / 2.0;
    }

    /** @return cuántos valores se agregaron hasta ahora */
    public int cantidad() {
        return mitadBaja.size() + mitadAlta.size();
    }

    public static void main(String[] args) {
        MedianaIncremental m = new MedianaIncremental();
        for (int valor : new int[] {5, 15, 1, 3}) {
            m.agregar(valor);
            System.out.println("Agrego " + valor + " → mediana = " + m.mediana());
        }
    }
}
