package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e07;

import java.util.ArrayList;
import java.util.List;

/**
 * e07: filtrar los numeros pares de una lista, preservando el orden.
 */
public final class FiltrarPares {

    private FiltrarPares() {
    }

    /**
     * Devuelve una nueva lista con los numeros pares de {@code l}.
     *
     * @param l lista original (no se modifica)
     * @return nueva lista con los elementos pares
     */
    public static List<Integer> pares(List<Integer> l) {
        List<Integer> resultado = new ArrayList<>();
        for (int n : l) {
            // Un numero es par si el resto de dividir por 2 es 0.
            if (n % 2 == 0) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6);
        System.out.println("Original: " + numeros);
        System.out.println("Pares:    " + pares(numeros));
    }
}
