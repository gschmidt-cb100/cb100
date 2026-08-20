package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e03;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * e03: union de dos listas sin duplicados, con orden estable
 * (primero los elementos de {@code a} y luego los nuevos de {@code b}).
 */
public final class UnionListas {

    private UnionListas() {
    }

    /**
     * Devuelve una nueva lista con los elementos de {@code a} seguidos por los
     * de {@code b}, sin repetidos y respetando el orden de aparicion.
     *
     * @param a primera lista
     * @param b segunda lista
     * @return nueva lista union sin duplicados
     */
    public static List<Integer> union(List<Integer> a, List<Integer> b) {
        // LinkedHashSet garantiza orden estable y descarta repetidos.
        LinkedHashSet<Integer> conjunto = new LinkedHashSet<>();
        conjunto.addAll(a);
        conjunto.addAll(b);
        return new ArrayList<>(conjunto);
    }

    public static void main(String[] args) {
        List<Integer> a = List.of(1, 2, 3);
        List<Integer> b = List.of(3, 4, 5);
        System.out.println("a:     " + a);
        System.out.println("b:     " + b);
        System.out.println("union: " + union(a, b));
    }
}
