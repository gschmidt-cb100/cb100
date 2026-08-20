package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e02;

import java.util.Arrays;

/**
 * QuickSelect: encuentra el k-ésimo menor elemento (k 1-based) sin ordenar
 * todo el arreglo. Reutiliza la partición de Lomuto pero solo recurre sobre
 * el lado que contiene la posición buscada.
 *
 * Complejidad temporal:
 *  - Caso promedio: O(n). Cada partición descarta en promedio la mitad del
 *    arreglo, por lo que el trabajo total es n + n/2 + n/4 + ... = O(n).
 *  - Peor caso: O(n^2) con pivotes siempre desbalanceados.
 * Complejidad espacial: O(1) adicional (versión iterativa).
 */
public final class QuickSelect {

    private QuickSelect() {
    }

    /**
     * Devuelve el k-ésimo menor elemento (k 1-based) de una copia del arreglo.
     * No modifica el arreglo original.
     */
    public static int kesimoMenor(int[] a, int k) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser null ni vacío");
        }
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException("k fuera de rango: " + k);
        }
        int[] copia = Arrays.copyOf(a, a.length);
        int objetivo = k - 1; // pasamos a índice 0-based
        int desde = 0;
        int hasta = copia.length - 1;
        while (desde <= hasta) {
            int p = particionarLomuto(copia, desde, hasta);
            if (p == objetivo) {
                return copia[p];
            } else if (p < objetivo) {
                desde = p + 1;
            } else {
                hasta = p - 1;
            }
        }
        // Inalcanzable con las validaciones previas.
        throw new IllegalStateException("No se encontró el k-ésimo elemento");
    }

    private static int particionarLomuto(int[] a, int desde, int hasta) {
        int pivote = a[hasta];
        int i = desde - 1;
        for (int j = desde; j < hasta; j++) {
            if (a[j] <= pivote) {
                i++;
                intercambiar(a, i, j);
            }
        }
        intercambiar(a, i + 1, hasta);
        return i + 1;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int[] datos = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        for (int k = 1; k <= datos.length; k++) {
            System.out.println("k=" + k + " -> " + kesimoMenor(datos, k));
        }
    }
}
