package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e05;

import java.util.Arrays;

/**
 * e05: minimo y maximo simultaneos con division y conquista.
 *
 * <p>Se parte el arreglo por la mitad, se obtiene el (min, max) de cada mitad y
 * se combinan quedandose con el menor de los minimos y el mayor de los maximos.</p>
 *
 * <p>La complejidad sigue siendo O(n), pero este enfoque hace aproximadamente
 * {@code 3n/2} comparaciones, menos que las {@code 2n} de un recorrido ingenuo.</p>
 */
public final class MinMax {

    private MinMax() {
    }

    /**
     * Devuelve el minimo y el maximo del arreglo.
     *
     * @param a arreglo no vacio
     * @return arreglo de dos posiciones {@code {min, max}}
     * @throws IllegalArgumentException si {@code a} es vacio
     */
    public static int[] minMax(int[] a) {
        if (a.length == 0) {
            throw new IllegalArgumentException("el arreglo no puede ser vacio");
        }
        return minMax(a, 0, a.length - 1);
    }

    /** Devuelve {min, max} del rango cerrado [lo, hi]. */
    private static int[] minMax(int[] a, int lo, int hi) {
        if (lo == hi) {
            return new int[]{a[lo], a[lo]}; // caso base: un solo elemento
        }
        if (hi - lo == 1) { // caso base: dos elementos, una comparacion
            if (a[lo] <= a[hi]) {
                return new int[]{a[lo], a[hi]};
            }
            return new int[]{a[hi], a[lo]};
        }
        int medio = lo + (hi - lo) / 2;
        int[] izq = minMax(a, lo, medio);
        int[] der = minMax(a, medio + 1, hi);
        int min = Math.min(izq[0], der[0]);
        int max = Math.max(izq[1], der[1]);
        return new int[]{min, max};
    }

    public static void main(String[] args) {
        int[] datos = {5, 2, 9, 1, 5, 6, 3};
        int[] r = minMax(datos);
        System.out.println("Datos: " + Arrays.toString(datos));
        System.out.println("min = " + r[0] + ", max = " + r[1]);
    }
}
