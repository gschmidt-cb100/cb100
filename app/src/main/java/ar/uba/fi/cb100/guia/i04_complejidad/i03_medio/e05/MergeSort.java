package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e05;

import java.util.Objects;

/**
 * Ordenamiento por mezcla (merge sort), ejemplo clasico de Division y Conquista.
 *
 * Se divide el arreglo en dos mitades, se ordena cada una recursivamente y
 * luego se mezclan (merge) las dos mitades ordenadas.
 *
 * Recurrencia: T(n) = 2 T(n/2) + O(n)
 *   - 2 T(n/2): las dos llamadas recursivas sobre cada mitad.
 *   - O(n): el costo de mezclar las dos mitades ordenadas.
 * Por el Teorema Maestro (a=2, b=2, f(n)=O(n), n^(log_b a)=n) => caso 2:
 *   T(n) = O(n log n).
 * Complejidad espacial: O(n) por el arreglo auxiliar usado en la mezcla.
 */
public final class MergeSort {

    private MergeSort() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Ordena el arreglo {@code a} en forma ascendente, in situ.
     *
     * @param a arreglo a ordenar (no nulo).
     */
    public static void ordenar(int[] a) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        if (a.length < 2) {
            return; // Caso base: 0 o 1 elemento ya esta ordenado.
        }
        int[] aux = new int[a.length];
        ordenarRango(a, aux, 0, a.length - 1);
    }

    // Ordena el subrango [inicio, fin] de forma recursiva.
    private static void ordenarRango(int[] a, int[] aux, int inicio, int fin) {
        if (inicio >= fin) {
            return; // Caso base.
        }
        int medio = inicio + (fin - inicio) / 2;
        ordenarRango(a, aux, inicio, medio);       // T(n/2)
        ordenarRango(a, aux, medio + 1, fin);      // T(n/2)
        mezclar(a, aux, inicio, medio, fin);       // O(n)
    }

    // Mezcla los dos subrangos ordenados [inicio, medio] y [medio+1, fin].
    private static void mezclar(int[] a, int[] aux, int inicio, int medio, int fin) {
        // Copia del rango al arreglo auxiliar.
        for (int k = inicio; k <= fin; k++) {
            aux[k] = a[k];
        }
        int i = inicio;
        int j = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (i > medio) {
                a[k] = aux[j++];
            } else if (j > fin) {
                a[k] = aux[i++];
            } else if (aux[i] <= aux[j]) {
                a[k] = aux[i++]; // <= mantiene la estabilidad.
            } else {
                a[k] = aux[j++];
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6, 3};
        ordenar(a);
        System.out.println(java.util.Arrays.toString(a)); // [1, 2, 3, 5, 5, 6, 9]
    }
}
