package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e04;

import java.util.Objects;

/**
 * Ordenamiento por insercion (insertion sort).
 *
 * Recorre el arreglo de izquierda a derecha y va insertando cada elemento en
 * su posicion correcta dentro de la porcion ya ordenada a su izquierda.
 *
 * Complejidad temporal:
 *   - Peor caso y caso promedio: O(n^2) (arreglo en orden inverso).
 *   - Mejor caso: O(n) cuando el arreglo ya esta ordenado (el while nunca entra).
 * Complejidad espacial: O(1) (ordena in situ).
 */
public final class InsertionSort {

    private InsertionSort() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Ordena el arreglo {@code a} en forma ascendente, in situ.
     *
     * @param a arreglo a ordenar (no nulo).
     */
    public static void ordenar(int[] a) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        for (int i = 1; i < a.length; i++) {
            int clave = a[i];
            int j = i - 1;
            // Se corren a la derecha los mayores que la clave.
            while (j >= 0 && a[j] > clave) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = clave;
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6};
        ordenar(a);
        System.out.println(java.util.Arrays.toString(a)); // [1, 2, 5, 5, 6, 9]
    }
}
