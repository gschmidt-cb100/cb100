package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e02;

import java.util.Arrays;

/**
 * Ejercicio 02 (Duplicar).
 *
 * Modifica el arreglo "in place" (en el mismo lugar). Como el arreglo se
 * pasa por referencia, el cambio queda reflejado en la variable de afuera.
 */
public final class DuplicarEnArreglo {

    private DuplicarEnArreglo() {
    }

    /**
     * Duplica cada elemento del arreglo modificandolo directamente.
     */
    public static void duplicar(int[] v) {
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i] * 2;
        }
    }

    public static void main(String[] args) {
        int[] v = {1, 2, 3, 4};
        duplicar(v);
        System.out.println("Arreglo duplicado: " + Arrays.toString(v)); // [2, 4, 6, 8]
    }
}
