package ar.uba.fi.cb100.material.i09_heaps;

import java.util.Arrays;

/**
 * <b>Heapsort</b>: ordena EN EL LUGAR usando un max-heap. Dos fases:
 * (1) heapify del arreglo entero como max-heap, O(n);
 * (2) n veces: el máximo (la raíz) se intercambia con la última posición
 * "viva" y se hunde la nueva raíz, O(log n) cada una.
 * Total O(n log n) SIEMPRE, sin memoria extra (a diferencia de Mergesort) y
 * sin peor caso cuadrático (a diferencia de Quicksort). No es estable.
 */
public final class Heapsort {

    private Heapsort() {}

    public static void ordenar(int[] a) {
        int n = a.length;
        // Fase 1: heapify como MAX-heap (desde el último nodo interno).
        for (int i = n / 2 - 1; i >= 0; i--) {
            hundir(a, i, n);
        }
        // Fase 2: extraer el máximo n veces, dejándolo al final.
        for (int fin = n - 1; fin > 0; fin--) {
            int aux = a[0]; a[0] = a[fin]; a[fin] = aux;   // máximo a su lugar final
            hundir(a, 0, fin);                             // re-armar el heap restante
        }
    }

    /** Hunde a[i] en el max-heap de tamaño {@code n} (con el MAYOR de los hijos). */
    private static void hundir(int[] a, int i, int n) {
        while (true) {
            int izquierdo = 2 * i + 1, derecho = 2 * i + 2, mayor = i;
            if (izquierdo < n && a[izquierdo] > a[mayor]) {
                mayor = izquierdo;
            }
            if (derecho < n && a[derecho] > a[mayor]) {
                mayor = derecho;
            }
            if (mayor == i) {
                break;
            }
            int aux = a[i]; a[i] = a[mayor]; a[mayor] = aux;
            i = mayor;
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 3, 8, 1, 9, 2, 7};
        ordenar(a);
        System.out.println(Arrays.toString(a));   // [1, 2, 3, 5, 7, 8, 9]
    }
}
