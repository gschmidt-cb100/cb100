package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e10;

import java.util.Arrays;

/**
 * Fusiona k arreglos ya ordenados en un único arreglo ordenado, con la
 * estrategia de "fusión por pares" (divide y vencerás sobre la lista de
 * arreglos), análoga a MergeSort.
 *
 * Sea N la cantidad total de elementos y k la cantidad de arreglos.
 * Complejidad temporal: O(N log k). Hay log k niveles de fusión y en cada
 * nivel se procesan en total O(N) elementos.
 * Complejidad espacial: O(N) por los arreglos intermedios.
 *
 * (Fusionar de a uno secuencialmente costaría O(N k); por eso se hace por
 * pares para bajar el factor k a log k.)
 */
public final class MergeKArreglos {

    private MergeKArreglos() {
    }

    /** Fusiona todos los arreglos (cada uno ordenado) en uno solo ordenado. */
    public static int[] fusionar(int[][] arreglos) {
        if (arreglos == null) {
            throw new IllegalArgumentException("El conjunto de arreglos no puede ser null");
        }
        if (arreglos.length == 0) {
            return new int[0];
        }
        for (int[] arr : arreglos) {
            if (arr == null) {
                throw new IllegalArgumentException("Ningún arreglo puede ser null");
            }
        }
        return fusionarRango(arreglos, 0, arreglos.length - 1);
    }

    /** Fusiona por pares (divide y vencerás) los arreglos en [desde..hasta]. */
    private static int[] fusionarRango(int[][] arreglos, int desde, int hasta) {
        if (desde == hasta) {
            return Arrays.copyOf(arreglos[desde], arreglos[desde].length);
        }
        int medio = desde + (hasta - desde) / 2;
        int[] izq = fusionarRango(arreglos, desde, medio);
        int[] der = fusionarRango(arreglos, medio + 1, hasta);
        return fusionarDos(izq, der);
    }

    /** Fusiona dos arreglos ordenados en uno nuevo ordenado. O(|a| + |b|). */
    private static int[] fusionarDos(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                res[k++] = a[i++];
            } else {
                res[k++] = b[j++];
            }
        }
        while (i < a.length) {
            res[k++] = a[i++];
        }
        while (j < b.length) {
            res[k++] = b[j++];
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] datos = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {0, 10}
        };
        System.out.println(Arrays.toString(fusionar(datos)));
    }
}
