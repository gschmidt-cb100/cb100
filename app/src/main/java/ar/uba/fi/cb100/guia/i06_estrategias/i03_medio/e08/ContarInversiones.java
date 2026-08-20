package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e08;

import java.util.Arrays;

/**
 * e08: conteo de inversiones con una variante de Mergesort en O(n log n).
 *
 * <p>Una inversion es un par de indices {@code (i, j)} con {@code i < j} y
 * {@code a[i] > a[j]}: mide que tan lejos esta el arreglo de estar ordenado.
 * Un arreglo ordenado tiene 0 inversiones; uno ordenado al reves tiene el maximo
 * {@code n(n-1)/2}.</p>
 *
 * <p>La idea es que durante la fusion de Mergesort, cada vez que tomamos un
 * elemento de la mitad derecha antes que uno de la izquierda, ese elemento forma
 * una inversion con todos los que quedan pendientes en la mitad izquierda.</p>
 */
public final class ContarInversiones {

    private ContarInversiones() {
    }

    /**
     * Cuenta la cantidad de inversiones de {@code a} sin modificar el original.
     *
     * @param a arreglo de entrada (no se modifica)
     * @return cantidad de pares {@code (i, j)} con {@code i < j} y {@code a[i] > a[j]}
     */
    public static long contar(int[] a) {
        int[] copia = Arrays.copyOf(a, a.length);
        return ordenarYContar(copia, 0, copia.length - 1);
    }

    /** Ordena in-place [lo, hi] y devuelve las inversiones halladas en ese rango. */
    private static long ordenarYContar(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return 0L; // caso base: 0 o 1 elemento, sin inversiones
        }
        int medio = lo + (hi - lo) / 2;
        long total = ordenarYContar(a, lo, medio);
        total += ordenarYContar(a, medio + 1, hi);
        total += fusionarYContar(a, lo, medio, hi);
        return total;
    }

    /** Fusiona [lo, medio] y [medio+1, hi] contando las inversiones cruzadas. */
    private static long fusionarYContar(int[] a, int lo, int medio, int hi) {
        int[] izq = Arrays.copyOfRange(a, lo, medio + 1);
        int[] der = Arrays.copyOfRange(a, medio + 1, hi + 1);
        int i = 0;
        int j = 0;
        int k = lo;
        long inversiones = 0L;
        while (i < izq.length && j < der.length) {
            if (izq[i] <= der[j]) {
                a[k++] = izq[i++];
            } else {
                // izq[i] > der[j]: der[j] es menor que izq[i] y que todos los
                // elementos restantes de la mitad izquierda.
                a[k++] = der[j++];
                inversiones += izq.length - i;
            }
        }
        while (i < izq.length) {
            a[k++] = izq[i++];
        }
        while (j < der.length) {
            a[k++] = der[j++];
        }
        return inversiones;
    }

    public static void main(String[] args) {
        int[] datos = {2, 4, 1, 3, 5};
        System.out.println("Datos: " + Arrays.toString(datos));
        System.out.println("Inversiones: " + contar(datos));
    }
}
