package ar.uba.fi.cb100.material.i04_complejidad;

import java.util.Arrays;

/**
 * Quicksort. En <b>promedio</b> parte en dos mitades: T(n)=2·T(n/2)+O(n) →
 * <b>O(n log n)</b>. En el <b>peor caso</b> (pivote siempre el menor/mayor) parte
 * en 1 y n−1: T(n)=T(n−1)+O(n) → <b>O(n²)</b>.
 */
public class QuickSort {

    public static void ordenar(int[] a) {
        ordenar(a, 0, a.length - 1);
    }

    private static void ordenar(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = particionar(a, lo, hi);
        ordenar(a, lo, p - 1);
        ordenar(a, p + 1, hi);
    }

    private static int particionar(int[] a, int lo, int hi) {
        int pivote = a[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivote) { i++; intercambiar(a, i, j); }
        }
        intercambiar(a, i + 1, hi);
        return i + 1;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 5, 6};
        ordenar(a);
        System.out.println(Arrays.toString(a));   // [1, 2, 5, 5, 6, 9]
    }
}
