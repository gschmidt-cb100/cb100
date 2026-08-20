package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e06;

import java.util.Collections;
import java.util.List;

/**
 * e06: obtener el maximo de una lista de enteros.
 * Lanza excepcion si la lista esta vacia.
 */
public final class MaximoLista {

    private MaximoLista() {
    }

    /**
     * Devuelve el maximo elemento de {@code l}.
     *
     * @param l lista no vacia
     * @return el valor maximo
     * @throws IllegalArgumentException si la lista esta vacia
     */
    public static int maximo(List<Integer> l) {
        if (l.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacia");
        }
        return Collections.max(l);
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(3, 9, 1, 7, 4);
        System.out.println("Maximo de " + numeros + " = " + maximo(numeros));
    }
}
