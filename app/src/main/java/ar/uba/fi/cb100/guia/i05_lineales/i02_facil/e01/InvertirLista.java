package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * e01: invertir el orden de una lista devolviendo una nueva lista.
 * Se usa la API de {@code java.util} (no se modifica la lista original).
 */
public final class InvertirLista {

    private InvertirLista() {
    }

    /**
     * Devuelve una nueva lista con los elementos de {@code l} en orden inverso.
     *
     * @param l lista original (no se modifica)
     * @return nueva lista invertida
     */
    public static List<Integer> invertir(List<Integer> l) {
        // Copiamos para no modificar la lista original.
        List<Integer> resultado = new ArrayList<>(l);
        Collections.reverse(resultado);
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> original = List.of(1, 2, 3, 4, 5);
        System.out.println("Original:  " + original);
        System.out.println("Invertida: " + invertir(original));
    }
}
