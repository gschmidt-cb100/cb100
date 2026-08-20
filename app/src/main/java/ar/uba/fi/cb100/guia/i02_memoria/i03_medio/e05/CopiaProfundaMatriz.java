package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e05;

import java.util.Arrays;

/**
 * Ejercicio 05 (CopiaProfundaMatriz).
 *
 * Una matriz int[][] es un arreglo de arreglos. Copiar solo el nivel
 * externo (copia superficial) comparte las filas internas. La copia
 * PROFUNDA duplica tambien cada fila, de modo que modificar la copia no
 * toca el original.
 */
public final class CopiaProfundaMatriz {

    private CopiaProfundaMatriz() {
    }

    public static int[][] copiaProfunda(int[][] m) {
        int[][] copia = new int[m.length][];
        for (int i = 0; i < m.length; i++) {
            copia[i] = Arrays.copyOf(m[i], m[i].length);
        }
        return copia;
    }

    public static void main(String[] args) {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copia = copiaProfunda(original);
        copia[0][0] = 99;
        System.out.println("Original: " + Arrays.deepToString(original)); // [[1, 2], [3, 4]]
        System.out.println("Copia:    " + Arrays.deepToString(copia));    // [[99, 2], [3, 4]]
    }
}
