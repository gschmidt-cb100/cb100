package ar.uba.fi.cb100.material.i04_complejidad;

import java.util.Arrays;

/**
 * Mergesort. Recurrencia <b>T(n) = 2·T(n/2) + O(n)</b>, que por el Teorema
 * Maestro (Caso 2) da <b>O(n log n)</b>, siempre (mejor, peor y promedio).
 */
public class MergeSort {

    public static void ordenar(int[] a) {
        if (a.length > 1) ordenar(a, 0, a.length - 1, new int[a.length]);
    }

    private static void ordenar(int[] a, int lo, int hi, int[] tmp) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        ordenar(a, lo, mid, tmp);          // 2 llamadas sobre la mitad
        ordenar(a, mid + 1, hi, tmp);
        fusionar(a, lo, mid, hi, tmp);     // fusión: O(n)
    }

    private static void fusionar(int[] a, int lo, int mid, int hi, int[] tmp) {
        for (int k = lo; k <= hi; k++) tmp[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                a[k] = tmp[j++];
            else if (j > hi)            a[k] = tmp[i++];
            else if (tmp[i] <= tmp[j])  a[k] = tmp[i++];
            else                        a[k] = tmp[j++];
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6};
        ordenar(a);
        System.out.println(Arrays.toString(a));   // [1, 2, 5, 5, 6, 9]
    }
}
