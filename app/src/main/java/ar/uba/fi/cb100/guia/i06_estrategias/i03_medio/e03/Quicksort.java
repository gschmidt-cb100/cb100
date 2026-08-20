package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e03;

import java.util.Arrays;

/**
 * e03: ordenamiento rapido (Quicksort), un algoritmo de division y conquista.
 *
 * <p>Idea:</p>
 * <ul>
 *   <li><b>Dividir:</b> elegir un pivote y particionar el arreglo dejando a la
 *       izquierda los menores y a la derecha los mayores.</li>
 *   <li><b>Conquistar:</b> ordenar recursivamente cada particion.</li>
 *   <li><b>Combinar:</b> no hace falta, la particion ya deja todo en su lugar.</li>
 * </ul>
 *
 * <p>Complejidad: O(n log n) en promedio, O(n^2) en el peor caso. Trabaja
 * in-place sobre una copia para no alterar el arreglo original.</p>
 */
public final class Quicksort {

    private Quicksort() {
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
        ordenar(copia, 0, copia.length - 1);
        return copia;
    }

    /** Ordena in-place el rango cerrado [lo, hi]. */
    private static void ordenar(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return; // caso base: 0 o 1 elemento
        }
        int p = particionar(a, lo, hi);
        ordenar(a, lo, p - 1);  // ordenar los menores al pivote
        ordenar(a, p + 1, hi);  // ordenar los mayores al pivote
    }

    /**
     * Particion de Lomuto usando {@code a[hi]} como pivote. Deja el pivote en su
     * posicion final y devuelve ese indice.
     */
    private static int particionar(int[] a, int lo, int hi) {
        int pivote = a[hi];
        int i = lo - 1; // indice del ultimo elemento menor al pivote
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivote) {
                i++;
                intercambiar(a, i, j);
            }
        }
        intercambiar(a, i + 1, hi); // ubicamos el pivote
        return i + 1;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int[] datos = {5, 2, 9, 1, 5, 6, 3};
        System.out.println("Original: " + Arrays.toString(datos));
        System.out.println("Ordenado: " + Arrays.toString(ordenar(datos)));
    }
}
