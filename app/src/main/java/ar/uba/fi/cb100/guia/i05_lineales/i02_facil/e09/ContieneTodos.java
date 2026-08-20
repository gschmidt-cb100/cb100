package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e09;

import java.util.List;

/**
 * e09: determinar si una lista contiene todos los elementos requeridos.
 */
public final class ContieneTodos {

    private ContieneTodos() {
    }

    /**
     * Indica si {@code l} contiene todos los elementos de {@code req}.
     *
     * @param l   lista donde se busca
     * @param req elementos requeridos
     * @return true si todos los requeridos estan en la lista
     */
    public static boolean contieneTodos(List<Integer> l, List<Integer> req) {
        // containsAll verifica que todos los elementos de req esten en l.
        return l.containsAll(req);
    }

    public static void main(String[] args) {
        List<Integer> lista = List.of(1, 2, 3, 4, 5);
        System.out.println(lista + " contiene [2, 4]? " + contieneTodos(lista, List.of(2, 4)));
        System.out.println(lista + " contiene [2, 9]? " + contieneTodos(lista, List.of(2, 9)));
    }
}
