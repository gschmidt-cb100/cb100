package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e03;

import java.util.Objects;

/**
 * Ordenamiento de burbuja (bubble sort).
 *
 * En cada pasada se comparan elementos adyacentes y se intercambian si estan
 * desordenados, "burbujeando" el mayor hacia el final.
 *
 * Complejidad temporal:
 *   - Peor caso y caso promedio: O(n^2) (arreglo en orden inverso).
 *   - Mejor caso: O(n) gracias a la bandera de corte temprano
 *     (si en una pasada no hubo intercambios, ya esta ordenado).
 * Complejidad espacial: O(1) (ordena en el mismo arreglo).
 */
public final class BubbleSort {

    private BubbleSort() {
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
            boolean huboIntercambio = false;
            // Tras cada pasada, el elemento mayor queda ubicado al final.
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) {
                break; // Ya esta ordenado: corte temprano (mejor caso O(n)).
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6};
        ordenar(a);
        System.out.println(java.util.Arrays.toString(a)); // [1, 2, 5, 5, 6, 9]
    }
}
