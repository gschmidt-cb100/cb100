package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e02;

import java.util.Arrays;

/**
 * e02: ordenamiento por mezcla (Mergesort), un algoritmo de division y conquista.
 *
 * <p>Idea:</p>
 * <ul>
 *   <li><b>Dividir:</b> partir el arreglo por la mitad.</li>
 *   <li><b>Conquistar:</b> ordenar recursivamente cada mitad.</li>
 *   <li><b>Combinar:</b> fusionar las dos mitades ya ordenadas.</li>
 * </ul>
 *
 * <p>Complejidad: O(n log n) en todos los casos. Es estable.</p>
 */
public final class Mergesort {

    private Mergesort() {
    }

    /**
     * Devuelve un nuevo arreglo con los elementos de {@code a} ordenados de forma
     * ascendente. El arreglo original no se modifica.
     *
     * @param a arreglo a ordenar (no se modifica)
     * @return nuevo arreglo ordenado
     */
    public static int[] ordenar(int[] a) {
        int[] copia = Arrays.copyOf(a, a.length);
        if (copia.length > 1) {
            ordenar(copia, 0, copia.length - 1);
        }
        return copia;
    }

    /** Ordena in-place el rango cerrado [lo, hi] de {@code a}. */
    private static void ordenar(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return; // caso base: 0 o 1 elemento ya esta ordenado
        }
        int medio = lo + (hi - lo) / 2;
        ordenar(a, lo, medio);        // ordenar mitad izquierda
        ordenar(a, medio + 1, hi);    // ordenar mitad derecha
        fusionar(a, lo, medio, hi);   // combinar ambas mitades
    }

    /** Fusiona los rangos ordenados [lo, medio] y [medio+1, hi]. */
    private static void fusionar(int[] a, int lo, int medio, int hi) {
        int[] izq = Arrays.copyOfRange(a, lo, medio + 1);
        int[] der = Arrays.copyOfRange(a, medio + 1, hi + 1);
        int i = 0;
        int j = 0;
        int k = lo;
        while (i < izq.length && j < der.length) {
            if (izq[i] <= der[j]) { // <= mantiene la estabilidad
                a[k++] = izq[i++];
            } else {
                a[k++] = der[j++];
            }
        }
        while (i < izq.length) {
            a[k++] = izq[i++];
        }
        while (j < der.length) {
            a[k++] = der[j++];
        }
    }

    public static void main(String[] args) {
        int[] datos = {5, 2, 9, 1, 5, 6, 3};
        System.out.println("Original: " + Arrays.toString(datos));
        System.out.println("Ordenado: " + Arrays.toString(ordenar(datos)));
    }
}
