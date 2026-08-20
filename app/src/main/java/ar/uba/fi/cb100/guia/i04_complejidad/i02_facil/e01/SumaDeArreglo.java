package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e01;

import java.util.Objects;

/**
 * e01 - Suma de los elementos de un arreglo.
 *
 * Complejidad: O(n).
 * Justificacion: se recorre el arreglo una unica vez con un solo bucle,
 * realizando una operacion de suma constante O(1) por cada uno de los n
 * elementos. El costo total es proporcional a n.
 */
public final class SumaDeArreglo {

    private SumaDeArreglo() {
    }

    /**
     * Suma todos los elementos del arreglo.
     *
     * @param a arreglo de enteros (no nulo)
     * @return la suma como {@code long} para evitar desbordes
     */
    public static long sumar(int[] a) {
        Objects.requireNonNull(a, "el arreglo no puede ser nulo");
        long acumulador = 0; // O(1)
        for (int i = 0; i < a.length; i++) { // n iteraciones -> O(n)
            acumulador += a[i]; // O(1)
        }
        return acumulador;
    }

    public static void main(String[] args) {
        int[] datos = {1, 2, 3, 4, 5};
        System.out.println("Suma de {1,2,3,4,5} = " + sumar(datos)); // 15
        System.out.println("Suma de {} = " + sumar(new int[0])); // 0
    }
}
