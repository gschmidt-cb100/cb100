package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e02;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * e02: eliminar duplicados de una lista preservando el orden de aparicion.
 * Se usa {@link LinkedHashSet}, que mantiene el orden de insercion.
 */
public final class SinDuplicados {

    private SinDuplicados() {
    }

    /**
     * Devuelve una nueva lista sin elementos repetidos, respetando el orden
     * en que aparecen por primera vez.
     *
     * @param l lista original (no se modifica)
     * @return nueva lista sin duplicados
     */
    public static List<Integer> sinDuplicados(List<Integer> l) {
        // LinkedHashSet descarta repetidos y conserva el orden de insercion.
        return new ArrayList<>(new LinkedHashSet<>(l));
    }

    public static void main(String[] args) {
        List<Integer> original = List.of(3, 1, 3, 2, 1, 5);
        Integer[] valores = {1, 2, 3, 4, 5};
        System.out.println("Original:       " + original);
        System.out.println("Sin duplicados: " + sinDuplicados(original));
    }
}
