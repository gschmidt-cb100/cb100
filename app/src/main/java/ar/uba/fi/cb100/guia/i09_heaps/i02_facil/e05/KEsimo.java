package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e05;

import java.util.PriorityQueue;

/**
 * e05: encontrar el k-ésimo menor elemento de un arreglo sin ordenarlo
 * por completo: encolamos todo en un min-heap y desencolamos k veces.
 * La última extracción es la respuesta.
 */
public final class KEsimo {

    private KEsimo() {
    }

    /**
     * Devuelve el k-ésimo menor elemento de {@code a} (con k arrancando en 1:
     * {@code kEsimo(a, 1)} es el mínimo).
     *
     * <p>Costo: O(n log n) por encolar más O(k log n) por desencolar.</p>
     *
     * @param a arreglo de entrada (no se modifica)
     * @param k posición pedida, entre 1 y {@code a.length}
     * @return el k-ésimo menor valor
     * @throws IllegalArgumentException si {@code k} está fuera de rango
     */
    public static int kEsimo(int[] a, int k) {
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException(
                    "k debe estar entre 1 y " + a.length + ", vino " + k);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : a) {
            heap.offer(x);
        }
        // Descartamos los primeros k-1 mínimos...
        for (int i = 1; i < k; i++) {
            heap.poll();
        }
        // ...y el que sigue es el k-ésimo.
        return heap.poll();
    }

    public static void main(String[] args) {
        int[] datos = {50, 20, 40, 10, 30};
        System.out.println("Arreglo: [50, 20, 40, 10, 30]");
        System.out.println("1er menor: " + kEsimo(datos, 1));
        System.out.println("3er menor: " + kEsimo(datos, 3));
        System.out.println("5to menor: " + kEsimo(datos, 5));
    }
}
