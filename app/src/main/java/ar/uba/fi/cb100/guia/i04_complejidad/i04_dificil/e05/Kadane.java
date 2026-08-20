package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e05;

import java.util.Arrays;

/**
 * Algoritmo de Kadane: suma máxima de un subarreglo contiguo (no vacío).
 *
 * Idea: recorriendo el arreglo, se mantiene la mejor suma que termina en la
 * posición actual. En cada paso, o bien extendemos el subarreglo anterior, o
 * bien empezamos uno nuevo en el elemento actual (lo que dé mayor suma).
 *
 * Complejidad temporal: O(n), una sola pasada.
 * Complejidad espacial: O(1).
 *
 * Detalle: para manejar el caso de todos negativos, se inicializa con el
 * primer elemento (el resultado es el mayor de los elementos en ese caso).
 */
public final class Kadane {

    private Kadane() {
    }

    /** Devuelve la máxima suma de un subarreglo contiguo no vacío. */
    public static int maxSubarreglo(int[] a) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede ser null ni vacío");
        }
        int mejorAqui = a[0];   // mejor suma que termina en el índice actual
        int mejorGlobal = a[0]; // mejor suma vista hasta ahora
        for (int i = 1; i < a.length; i++) {
            // Extender el subarreglo previo o arrancar uno nuevo en a[i].
            mejorAqui = Math.max(a[i], mejorAqui + a[i]);
            mejorGlobal = Math.max(mejorGlobal, mejorAqui);
        }
        return mejorGlobal;
    }

    public static void main(String[] args) {
        int[] datos = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Arreglo: " + Arrays.toString(datos));
        System.out.println("Suma máxima: " + maxSubarreglo(datos)); // 6 -> [4,-1,2,1]
    }
}
