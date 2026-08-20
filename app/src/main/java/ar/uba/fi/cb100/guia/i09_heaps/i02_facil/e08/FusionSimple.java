package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e08;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e08: fusionar dos listas ordenadas en una sola lista ordenada, a fuerza
 * bruta con heap: encolamos todo y desencolamos.
 *
 * <p>Este enfoque cuesta O((n+m) log(n+m)) y ni siquiera aprovecha que las
 * listas vienen ordenadas. El merge "de dos punteros" (como el de merge sort)
 * lo hace en O(n+m); acá el objetivo es practicar la mecánica del heap.
 * En el nivel medio se generaliza a k listas, donde el heap sí paga.</p>
 */
public final class FusionSimple {

    private FusionSimple() {
    }

    /**
     * Devuelve una lista ordenada con todos los elementos de {@code a} y
     * {@code b} (ambas se asumen ordenadas de menor a mayor).
     *
     * @param a primera lista ordenada (no se modifica)
     * @param b segunda lista ordenada (no se modifica)
     * @return lista fusionada, ordenada de menor a mayor
     */
    public static List<Integer> fusionar(List<Integer> a, List<Integer> b) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.addAll(a);
        heap.addAll(b);
        List<Integer> resultado = new ArrayList<>();
        while (!heap.isEmpty()) {
            resultado.add(heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 4, 9);
        List<Integer> b = List.of(2, 3, 10);
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Fusion: " + fusionar(a, b));
    }
}
