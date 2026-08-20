package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e03: la {@link PriorityQueue} de Java es un <em>min-heap</em> por defecto,
 * pero pasándole {@link Comparator#reverseOrder()} en el constructor
 * la convertimos en un <em>max-heap</em>: el que sale primero es el mayor.
 */
public final class Maximos {

    private Maximos() {
    }

    /**
     * Devuelve el máximo de {@code a} usando un max-heap.
     *
     * <p>Ojo: para buscar solo el máximo alcanza con un recorrido O(n);
     * acá usamos el heap con fines didácticos, para ver que {@code peek()}
     * devuelve la "cabeza" según el comparador elegido.</p>
     *
     * @param a arreglo de entrada (no puede estar vacío)
     * @return el mayor elemento
     * @throws IllegalArgumentException si el arreglo está vacío
     */
    public static int maximo(int[] a) {
        if (a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacio");
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : a) {
            maxHeap.offer(x);
        }
        // peek() mira la cabeza sin sacarla: con reverseOrder es el máximo.
        return maxHeap.peek();
    }

    /**
     * Devuelve los elementos de {@code a} ordenados de mayor a menor,
     * desencolando un max-heap hasta vaciarlo.
     *
     * @param a arreglo de entrada (no se modifica)
     * @return lista con los mismos valores, de mayor a menor
     */
    public static List<Integer> enOrdenDescendente(int[] a) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : a) {
            maxHeap.offer(x);
        }
        List<Integer> resultado = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            resultado.add(maxHeap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {3, 8, 1, 8, 5};
        System.out.println("Maximo de [3, 8, 1, 8, 5]: " + maximo(datos));
        System.out.println("Descendente: " + enOrdenDescendente(datos));
    }
}
