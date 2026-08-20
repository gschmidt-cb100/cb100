package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e03;

import java.util.Arrays;

/**
 * MergeSort iterativo (bottom-up), sin recursión.
 *
 * Idea: en lugar de dividir recursivamente, se empieza fusionando bloques de
 * tamaño 1, luego 2, 4, 8... hasta cubrir todo el arreglo. En cada pasada se
 * fusionan pares de bloques adyacentes ya ordenados.
 *
 * Complejidad temporal: O(n log n) en todos los casos.
 *   - Hay log n pasadas (el tamaño de bloque se duplica cada vez).
 *   - Cada pasada fusiona en total O(n) elementos.
 * Complejidad espacial: O(n) por el arreglo auxiliar.
 */
public final class MergeSortIterativo {

    private MergeSortIterativo() {
    }

    /** Ordena el arreglo in-place de menor a mayor. */
    public static void ordenar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        int n = a.length;
        int[] aux = new int[n];
        // ancho = tamaño de los bloques que ya están ordenados internamente.
        for (int ancho = 1; ancho < n; ancho *= 2) {
            for (int desde = 0; desde < n; desde += 2 * ancho) {
                int medio = Math.min(desde + ancho, n);
                int hasta = Math.min(desde + 2 * ancho, n);
                fusionar(a, aux, desde, medio, hasta);
            }
        }
    }

    /**
     * Fusiona dos bloques ordenados a[desde..medio) y a[medio..hasta) usando
     * aux como buffer y copia el resultado de vuelta en a.
     */
    private static void fusionar(int[] a, int[] aux, int desde, int medio, int hasta) {
        int i = desde;   // recorre el bloque izquierdo
        int j = medio;   // recorre el bloque derecho
        int k = desde;   // posición de escritura en aux
        while (i < medio && j < hasta) {
            if (a[i] <= a[j]) {
                aux[k++] = a[i++];
            } else {
                aux[k++] = a[j++];
            }
        }
        while (i < medio) {
            aux[k++] = a[i++];
        }
        while (j < hasta) {
            aux[k++] = a[j++];
        }
        for (int t = desde; t < hasta; t++) {
            a[t] = aux[t];
        }
    }

    public static void main(String[] args) {
        int[] datos = {5, 2, 9, 1, 7, 3, 8, 4, 6, 0};
        System.out.println("Original: " + Arrays.toString(datos));
        ordenar(datos);
        System.out.println("Ordenado: " + Arrays.toString(datos));
    }
}
