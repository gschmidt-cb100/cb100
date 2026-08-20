package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e06;

import java.util.Objects;

/**
 * Suma de los elementos de un arreglo en forma recursiva.
 *
 * Recurrencia: T(n) = T(n-1) + O(1)
 *   - T(n-1): se resuelve la suma del resto del arreglo.
 *   - O(1): la suma del elemento actual.
 * Desarrollando: T(n) = O(n).
 * Complejidad espacial: O(n) por la pila de llamadas recursivas.
 */
public final class SumaRecursiva {

    private SumaRecursiva() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Suma todos los elementos del arreglo {@code a}.
     *
     * @param a arreglo a sumar (no nulo).
     * @return la suma de los elementos (0 si el arreglo esta vacio).
     */
    public static long sumar(int[] a) {
        Objects.requireNonNull(a, "El arreglo no puede ser nulo");
        return sumarDesde(a, 0);
    }

    // Suma el subarreglo a partir del indice i.
    private static long sumarDesde(int[] a, int i) {
        if (i == a.length) {
            return 0; // Caso base: no quedan elementos.
        }
        return a[i] + sumarDesde(a, i + 1);
    }

    public static void main(String[] args) {
        System.out.println(sumar(new int[]{1, 2, 3, 4, 5})); // 15
        System.out.println(sumar(new int[]{}));              // 0
        System.out.println(sumar(new int[]{-1, 1, -2, 2}));  // 0
    }
}
