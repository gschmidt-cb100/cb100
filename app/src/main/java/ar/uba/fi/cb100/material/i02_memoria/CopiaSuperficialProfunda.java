package ar.uba.fi.cb100.material.i02_memoria;

import java.util.Arrays;

/**
 * Copia <b>superficial</b> (shallow): copia las referencias, no los objetos
 * internos (que quedan compartidos). Copia <b>profunda</b> (deep): copia también
 * los objetos internos, quedando totalmente independiente.
 */
public class CopiaSuperficialProfunda {

    public static void main(String[] args) {
        // Copia de un arreglo de primitivos: queda independiente.
        int[] a = {1, 2, 3};
        int[] copia = Arrays.copyOf(a, a.length);
        copia[0] = 99;
        System.out.println(Arrays.toString(a) + " / " + Arrays.toString(copia)); // [1,2,3] / [99,2,3]

        // Copia SUPERFICIAL de una matriz: se comparten las filas internas.
        int[][] matriz = {{1, 2}, {3, 4}};
        int[][] superficial = matriz.clone();     // copia las referencias a las filas
        superficial[0][0] = 99;
        System.out.println("la superficial afectó al original: " + matriz[0][0]);  // 99

        // Copia PROFUNDA: copiamos también cada fila.
        int[][] profunda = new int[matriz.length][];
        for (int i = 0; i < matriz.length; i++) {
            profunda[i] = Arrays.copyOf(matriz[i], matriz[i].length);
        }
        profunda[1][1] = -1;
        System.out.println("la profunda NO afectó al original: " + matriz[1][1]);  // 4
    }
}
