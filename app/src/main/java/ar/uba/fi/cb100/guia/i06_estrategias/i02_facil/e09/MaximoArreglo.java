package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e09;

import java.util.Arrays;

/**
 * e09: maximo de un arreglo de enteros calculado de forma recursiva.
 *
 * <p>Se usa un metodo auxiliar con un indice:</p>
 * <ul>
 *   <li>Caso base: si solo queda un elemento, ese es el maximo</li>
 *   <li>Paso recursivo: el mayor entre a[i] y el maximo del resto</li>
 * </ul>
 */
public final class MaximoArreglo {

    private MaximoArreglo() {
    }

    /**
     * Devuelve el maximo elemento de {@code a} recursivamente.
     *
     * @param a arreglo no vacio
     * @return el valor maximo
     * @throws IllegalArgumentException si el arreglo esta vacio
     */
    public static int maximo(int[] a) {
        if (a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacio");
        }
        return maximoDesde(a, 0);
    }

    /**
     * Metodo auxiliar: maximo de {@code a} considerando desde la posicion {@code i}.
     *
     * @param a arreglo de enteros
     * @param i indice a partir del cual buscar
     * @return el maximo entre a[i..fin]
     */
    private static int maximoDesde(int[] a, int i) {
        if (i == a.length - 1) {
            return a[i]; // caso base: ultimo elemento
        }
        return Math.max(a[i], maximoDesde(a, i + 1)); // paso recursivo
    }

    public static void main(String[] args) {
        int[] numeros = {3, 9, 1, 7, 4};
        System.out.println("maximo de " + Arrays.toString(numeros) + " = " + maximo(numeros));
    }
}
