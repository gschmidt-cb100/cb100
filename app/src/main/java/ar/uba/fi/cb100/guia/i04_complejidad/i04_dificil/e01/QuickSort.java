package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e01;

import java.util.Arrays;

/**
 * Ordenamiento QuickSort con partición de Lomuto.
 *
 * Complejidad temporal:
 *  - Caso promedio: O(n log n). Con pivotes "equilibrados" el arreglo se
 *    divide en dos mitades aproximadamente iguales en log n niveles,
 *    haciendo O(n) trabajo por nivel.
 *  - Peor caso: O(n^2). Ocurre cuando el pivote siempre queda en un extremo
 *    (por ejemplo arreglo ya ordenado con pivote fijo al final), generando
 *    n niveles de recursión con O(n) trabajo cada uno.
 * Complejidad espacial: O(log n) promedio por la pila de recursión.
 *
 * Nota: la partición de Lomuto usa el último elemento como pivote.
 */
public final class QuickSort {

    private QuickSort() {
    }

    /** Ordena el arreglo in-place de menor a mayor. */
    public static void ordenar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(int[] a, int desde, int hasta) {
        // Caso base: subarreglo de 0 o 1 elemento ya está ordenado.
        if (desde >= hasta) {
            return;
        }
        int p = particionarLomuto(a, desde, hasta);
        quicksort(a, desde, p - 1);
        quicksort(a, p + 1, hasta);
    }

    /**
     * Partición de Lomuto. Toma a[hasta] como pivote y deja a la izquierda
     * los menores o iguales; devuelve la posición final del pivote.
     */
    private static int particionarLomuto(int[] a, int desde, int hasta) {
        int pivote = a[hasta];
        int i = desde - 1; // límite de la zona de "menores o iguales"
        for (int j = desde; j < hasta; j++) {
            if (a[j] <= pivote) {
                i++;
                intercambiar(a, i, j);
            }
        }
        intercambiar(a, i + 1, hasta);
        return i + 1;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int[] datos = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        System.out.println("Original: " + Arrays.toString(datos));
        ordenar(datos);
        System.out.println("Ordenado: " + Arrays.toString(datos));
    }
}
