package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e01;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e01: el "hola mundo" de las colas de prioridad. Encolamos todos los
 * elementos en una {@link PriorityQueue} (que por defecto es un min-heap)
 * y los desencolamos: siempre sale primero el mínimo, así que el resultado
 * queda ordenado de menor a mayor. Esto es, ni más ni menos, heapsort.
 */
public final class MinimosSucesivos {

    private MinimosSucesivos() {
    }

    /**
     * Devuelve los elementos de {@code a} ordenados de menor a mayor,
     * usando una cola de prioridad como estructura auxiliar.
     *
     * <p>Costo: n inserciones y n extracciones de O(log n) cada una,
     * o sea O(n log n) total.</p>
     *
     * @param a arreglo de entrada (no se modifica)
     * @return lista con los mismos valores, de menor a mayor
     */
    public static List<Integer> enOrden(int[] a) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : a) {
            heap.offer(x);
        }
        List<Integer> resultado = new ArrayList<>();
        while (!heap.isEmpty()) {
            // poll() saca siempre el mínimo actual del heap.
            resultado.add(heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {7, 2, 9, 2, 5};
        System.out.println("Entrada: [7, 2, 9, 2, 5]");
        System.out.println("Salida:  " + enOrden(datos));
    }
}
