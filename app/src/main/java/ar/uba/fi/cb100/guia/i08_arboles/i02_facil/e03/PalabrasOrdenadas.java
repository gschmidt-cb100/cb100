package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e03;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * e03: palabras únicas y ordenadas. Un {@link TreeSet} hace las dos cosas
 * a la vez: descarta repetidos (es un conjunto) y mantiene los elementos
 * ordenados (es un árbol de búsqueda).
 */
public final class PalabrasOrdenadas {

    private PalabrasOrdenadas() {
    }

    /**
     * Devuelve las palabras de la lista sin repetidos y en orden alfabético.
     *
     * @param palabras lista de palabras, posiblemente con repetidos
     * @return lista de palabras únicas ordenadas alfabéticamente
     */
    public static List<String> unicasOrdenadas(List<String> palabras) {
        // El TreeSet elimina duplicados y ordena; después lo volcamos a una lista.
        TreeSet<String> unicas = new TreeSet<>(palabras);
        return new ArrayList<>(unicas);
    }

    public static void main(String[] args) {
        List<String> palabras = List.of("pera", "manzana", "pera", "banana", "manzana", "kiwi");
        System.out.println("Original: " + palabras);
        System.out.println("Únicas y ordenadas: " + unicasOrdenadas(palabras));
    }
}
