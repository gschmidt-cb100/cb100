package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * e10: conversiones entre {@code List<Integer>} y {@code int[]}.
 */
public final class ConversionListaArreglo {

    private ConversionListaArreglo() {
    }

    /**
     * Convierte una lista de enteros en un arreglo primitivo.
     *
     * @param l lista de enteros
     * @return arreglo con los mismos elementos en el mismo orden
     */
    public static int[] aArreglo(List<Integer> l) {
        int[] resultado = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            // Desempaquetado (unboxing) de Integer a int.
            resultado[i] = l.get(i);
        }
        return resultado;
    }

    /**
     * Convierte un arreglo primitivo en una lista de enteros.
     *
     * @param a arreglo de enteros
     * @return lista con los mismos elementos en el mismo orden
     */
    public static List<Integer> aLista(int[] a) {
        List<Integer> resultado = new ArrayList<>(a.length);
        for (int n : a) {
            // Empaquetado (autoboxing) de int a Integer.
            resultado.add(n);
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> lista = List.of(1, 2, 3);
        int[] arreglo = aArreglo(lista);
        System.out.println("Lista -> arreglo: " + Arrays.toString(arreglo));
        System.out.println("Arreglo -> lista: " + aLista(arreglo));
    }
}
