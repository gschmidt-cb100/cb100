package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * e06: intersección de dos arreglos usando conjuntos.
 * Metemos el primero en un {@link HashSet} y recorremos el segundo
 * preguntando pertenencia en O(1) promedio: total O(n + m),
 * mucho mejor que comparar todos contra todos (O(n·m)).
 */
public final class InterseccionArreglos {

    private InterseccionArreglos() {
    }

    /**
     * Devuelve el conjunto de valores que aparecen en {@code a} y también en {@code b},
     * sin duplicados.
     *
     * @param a primer arreglo
     * @param b segundo arreglo
     * @return conjunto con los valores en común
     */
    public static Set<Integer> interseccion(int[] a, int[] b) {
        Set<Integer> enA = new HashSet<>();
        for (int valor : a) {
            enA.add(valor);
        }
        Set<Integer> comunes = new HashSet<>();
        for (int valor : b) {
            if (enA.contains(valor)) {
                comunes.add(valor);
            }
        }
        return comunes;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 3, 4};
        int[] b = {2, 4, 4, 6};
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("b = " + Arrays.toString(b));
        System.out.println("Interseccion: " + interseccion(a, b));
    }
}
