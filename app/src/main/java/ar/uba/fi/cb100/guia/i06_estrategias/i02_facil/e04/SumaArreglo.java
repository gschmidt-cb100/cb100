package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e04;

import java.util.Arrays;

/**
 * e04: suma de los elementos de un arreglo de forma recursiva.
 *
 * <p>Se usa un metodo auxiliar con un indice que avanza sobre el arreglo:</p>
 * <ul>
 *   <li>Caso base: si el indice llego al final, la suma es 0</li>
 *   <li>Paso recursivo: a[i] + suma del resto (desde i+1)</li>
 * </ul>
 */
public final class SumaArreglo {

    private SumaArreglo() {
    }

    /**
     * Suma todos los elementos de {@code a} recursivamente.
     *
     * @param a arreglo de enteros (no nulo)
     * @return la suma de sus elementos, o 0 si esta vacio
     */
    public static int sumar(int[] a) {
        return sumarDesde(a, 0);
    }

    /**
     * Metodo auxiliar: suma los elementos de {@code a} desde la posicion {@code i}.
     *
     * @param a arreglo de enteros
     * @param i indice a partir del cual sumar
     * @return la suma parcial desde {@code i} hasta el final
     */
    private static int sumarDesde(int[] a, int i) {
        if (i == a.length) {
            return 0; // caso base: no quedan elementos
        }
        return a[i] + sumarDesde(a, i + 1); // paso recursivo
    }

    public static void main(String[] args) {
        int[] numeros = {3, 9, 1, 7, 4};
        System.out.println("suma de " + Arrays.toString(numeros) + " = " + sumar(numeros));
        System.out.println("suma de arreglo vacio = " + sumar(new int[0]));
    }
}
