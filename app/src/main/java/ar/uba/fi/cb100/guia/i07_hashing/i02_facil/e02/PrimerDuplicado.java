package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * e02: encontrar el primer valor que aparece por segunda vez en un arreglo.
 * Con un {@link HashSet} el chequeo "¿ya lo vi?" es O(1) promedio,
 * así el algoritmo completo queda O(n).
 */
public final class PrimerDuplicado {

    private PrimerDuplicado() {
    }

    /**
     * Devuelve el primer valor que aparece por segunda vez al recorrer {@code a}
     * de izquierda a derecha, o {@code null} si no hay duplicados.
     *
     * @param a arreglo de enteros
     * @return el primer duplicado, o {@code null} si todos son distintos
     */
    public static Integer primerDuplicado(int[] a) {
        Set<Integer> vistos = new HashSet<>();
        for (int valor : a) {
            // add devuelve false si el valor ya estaba en el conjunto.
            if (!vistos.add(valor)) {
                return valor;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] datos = {3, 1, 4, 1, 5, 3};
        System.out.println("Arreglo: " + Arrays.toString(datos));
        System.out.println("Primer duplicado: " + primerDuplicado(datos));
    }
}
