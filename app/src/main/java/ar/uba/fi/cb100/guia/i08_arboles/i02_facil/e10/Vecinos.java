package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e10;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * e10: vecinos estrictos de un valor. {@code lower(x)} devuelve el mayor
 * elemento estrictamente menor que x, y {@code higher(x)} el menor elemento
 * estrictamente mayor. A diferencia de floor/ceiling, acá x nunca se
 * devuelve a sí mismo aunque esté en el conjunto.
 */
public final class Vecinos {

    private Vecinos() {
    }

    /**
     * Devuelve el vecino anterior y el siguiente de {@code x} en el conjunto.
     *
     * @param valores conjunto ordenado de enteros
     * @param x       valor de referencia (puede estar o no en el conjunto)
     * @return arreglo {lower(x), higher(x)}, con {@code null} en los bordes
     */
    public static Integer[] vecinos(TreeSet<Integer> valores, int x) {
        return new Integer[] { valores.lower(x), valores.higher(x) };
    }

    public static void main(String[] args) {
        TreeSet<Integer> pisos = new TreeSet<>();
        pisos.add(2);
        pisos.add(5);
        pisos.add(8);
        System.out.println("Pisos con parada: " + pisos);
        System.out.println("Vecinos del 5: " + Arrays.toString(vecinos(pisos, 5)));
        System.out.println("Vecinos del 2 (borde): " + Arrays.toString(vecinos(pisos, 2)));
    }
}
