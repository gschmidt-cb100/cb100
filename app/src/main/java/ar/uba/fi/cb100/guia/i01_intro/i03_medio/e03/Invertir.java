package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e03;

import java.util.Arrays;

/**
 * Ejercicio 03: Invertir un arreglo.
 * Devuelve un arreglo nuevo con los elementos en orden inverso.
 */
public class Invertir {

    /**
     * Invierte un arreglo devolviendo uno nuevo (no modifica el original).
     *
     * @param v arreglo original
     * @return arreglo nuevo invertido
     * @throws IllegalArgumentException si el arreglo es nulo
     */
    public static int[] invertir(int[] v) {
        if (v == null) {
            throw new IllegalArgumentException("El arreglo no puede ser nulo");
        }
        int[] resultado = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            resultado[i] = v[v.length - 1 - i];
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 3, 4, 5};
        System.out.println("Original:  " + Arrays.toString(datos));
        System.out.println("Invertido: " + Arrays.toString(invertir(datos)));
    }
}
