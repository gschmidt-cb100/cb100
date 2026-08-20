package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e03;

import java.util.Objects;

/**
 * e03 - Maximo de un arreglo no vacio.
 *
 * Complejidad: O(n).
 * Justificacion: un unico recorrido del arreglo, comparando cada elemento
 * contra el maximo parcial en tiempo constante. n comparaciones -> O(n).
 */
public final class MaximoDeArreglo {

    private MaximoDeArreglo() {
    }

    /**
     * Devuelve el mayor elemento del arreglo.
     *
     * @param a arreglo no nulo y no vacio
     * @return el valor maximo
     * @throws IllegalArgumentException si el arreglo esta vacio
     */
    public static int maximo(int[] a) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        if (a.length == 0) {
            throw new IllegalArgumentException("el arreglo no puede estar vacio");
        }
        int max = a[0]; // O(1)
        for (int i = 1; i < a.length; i++) { // O(n)
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println("Maximo de {3,9,1,9,2} = " + maximo(new int[]{3, 9, 1, 9, 2})); // 9
        System.out.println("Maximo de {-7} = " + maximo(new int[]{-7})); // -7
    }
}
