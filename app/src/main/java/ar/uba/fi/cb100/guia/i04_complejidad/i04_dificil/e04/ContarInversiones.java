package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e04;

import java.util.Arrays;

/**
 * Cuenta la cantidad de inversiones de un arreglo: pares (i, j) con i < j
 * pero a[i] > a[j]. Es una medida de "cuán desordenado" está el arreglo.
 *
 * Se resuelve con una variante de MergeSort: al fusionar dos mitades
 * ordenadas, cada vez que un elemento de la mitad derecha se coloca antes que
 * elementos restantes de la izquierda, esos elementos forman inversiones.
 *
 * Complejidad temporal: O(n log n), igual que MergeSort.
 * Complejidad espacial: O(n) por los arreglos auxiliares.
 *
 * Se usa long para el conteo porque el máximo es n*(n-1)/2, que desborda int
 * para n grande.
 */
public final class ContarInversiones {

    private ContarInversiones() {
    }

    /** Devuelve la cantidad de inversiones del arreglo (no lo modifica). */
    public static long contar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        int[] copia = Arrays.copyOf(a, a.length);
        int[] aux = new int[a.length];
        return ordenarYContar(copia, aux, 0, a.length - 1);
    }

    private static long ordenarYContar(int[] a, int[] aux, int desde, int hasta) {
        if (desde >= hasta) {
            return 0;
        }
        int medio = desde + (hasta - desde) / 2;
        long inv = 0;
        inv += ordenarYContar(a, aux, desde, medio);
        inv += ordenarYContar(a, aux, medio + 1, hasta);
        inv += fusionarYContar(a, aux, desde, medio, hasta);
        return inv;
    }

    private static long fusionarYContar(int[] a, int[] aux, int desde, int medio, int hasta) {
        int i = desde;
        int j = medio + 1;
        int k = desde;
        long inv = 0;
        while (i <= medio && j <= hasta) {
            if (a[i] <= a[j]) {
                aux[k++] = a[i++];
            } else {
                // a[i] > a[j]: a[j] es menor que todos los que quedan en la
                // mitad izquierda (i..medio), o sea (medio - i + 1) inversiones.
                aux[k++] = a[j++];
                inv += (medio - i + 1);
            }
        }
        while (i <= medio) {
            aux[k++] = a[i++];
        }
        while (j <= hasta) {
            aux[k++] = a[j++];
        }
        for (int t = desde; t <= hasta; t++) {
            a[t] = aux[t];
        }
        return inv;
    }

    public static void main(String[] args) {
        int[] datos = {2, 4, 1, 3, 5};
        System.out.println("Arreglo: " + Arrays.toString(datos));
        System.out.println("Inversiones: " + contar(datos)); // (2,1),(4,1),(4,3) = 3
    }
}
