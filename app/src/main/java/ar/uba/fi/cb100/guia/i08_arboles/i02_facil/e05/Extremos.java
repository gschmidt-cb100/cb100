package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e05;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * e05: mínimo y máximo de una colección. En un {@link TreeSet} el menor
 * y el mayor están siempre "a mano": {@code first()} y {@code last()}
 * cuestan O(log n) porque son los extremos del árbol.
 */
public final class Extremos {

    private Extremos() {
    }

    /**
     * Devuelve el mínimo y el máximo de la colección.
     *
     * @param valores colección de enteros, no vacía
     * @return arreglo de dos posiciones: {mínimo, máximo}
     * @throws NoSuchElementException si la colección está vacía
     */
    public static int[] minimoYMaximo(Collection<Integer> valores) {
        if (valores.isEmpty()) {
            throw new NoSuchElementException("La colección está vacía: no hay mínimo ni máximo");
        }
        TreeSet<Integer> ordenados = new TreeSet<>(valores);
        // first() es la hoja más a la izquierda; last(), la más a la derecha.
        return new int[] { ordenados.first(), ordenados.last() };
    }

    public static void main(String[] args) {
        List<Integer> temperaturas = List.of(18, 25, 11, 30, 22);
        int[] extremos = minimoYMaximo(temperaturas);
        System.out.println("Temperaturas: " + temperaturas);
        System.out.println("Mínima: " + extremos[0] + ", máxima: " + extremos[1]);
    }
}
