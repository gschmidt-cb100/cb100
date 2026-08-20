package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e01;

/**
 * Ejercicio 01: Máximo de un arreglo.
 * Devuelve el mayor elemento de un arreglo de enteros.
 */
public class Maximo {

    /**
     * Calcula el máximo de un arreglo no vacío.
     *
     * @param v arreglo de enteros
     * @return el mayor elemento
     * @throws IllegalArgumentException si el arreglo es nulo o vacío
     */
    public static int maximo(int[] v) {
        if (v == null || v.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacío");
        }
        int max = v[0];
        for (int i = 1; i < v.length; i++) {
            if (v[i] > max) {
                max = v[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] datos = {3, 9, 1, 7, 2};
        System.out.println("Arreglo: [3, 9, 1, 7, 2]");
        System.out.println("Máximo: " + maximo(datos));

        int[] uno = {42};
        System.out.println("Máximo de [42]: " + maximo(uno));
    }
}
