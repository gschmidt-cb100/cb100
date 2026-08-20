package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e10;

import java.util.Objects;

/**
 * Ordenamiento por seleccion (selection sort).
 *
 * En cada pasada busca el minimo de la porcion no ordenada y lo intercambia
 * con el primer elemento de esa porcion.
 *
 * Complejidad temporal: O(n^2) SIEMPRE (mejor, promedio y peor caso), porque
 * los dos bucles anidados recorren el arreglo independientemente de su orden:
 * el numero de comparaciones es fijo (n(n-1)/2).
 * Complejidad espacial: O(1) (ordena in situ).
 */
public final class SelectionSort {

    private SelectionSort() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Ordena el arreglo {@code a} en forma ascendente, in situ.
     *
     * @param a arreglo a ordenar (no nulo).
     */
    public static void ordenar(int[] a) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMin = i;
            // Se busca el minimo en la porcion no ordenada [i+1, n-1].
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[indiceMin]) {
                    indiceMin = j;
                }
            }
            if (indiceMin != i) {
                int tmp = a[i];
                a[i] = a[indiceMin];
                a[indiceMin] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6};
        ordenar(a);
        System.out.println(java.util.Arrays.toString(a)); // [1, 2, 5, 5, 6, 9]
    }
}
