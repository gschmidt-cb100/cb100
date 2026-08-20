package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * e05: rotar una lista {@code k} posiciones hacia la derecha, devolviendo
 * una nueva lista. {@code k} puede ser mayor que el tamano de la lista.
 */
public final class RotarDerecha {

    private RotarDerecha() {
    }

    /**
     * Devuelve una nueva lista rotada {@code k} posiciones a la derecha.
     *
     * @param l lista original (no se modifica)
     * @param k cantidad de posiciones (puede ser mayor al tamano)
     * @return nueva lista rotada
     */
    public static List<Integer> rotarDerecha(List<Integer> l, int k) {
        List<Integer> resultado = new ArrayList<>(l);
        if (resultado.isEmpty()) {
            return resultado;
        }
        // Normalizamos k al rango [0, tamano) para soportar k > tamano.
        int desplazamiento = k % resultado.size();
        // Collections.rotate rota in-place la copia (positivo = a la derecha).
        Collections.rotate(resultado, desplazamiento);
        return resultado;
    }

    public static void main(String[] args) {
        List<Integer> original = List.of(1, 2, 3, 4, 5);
        System.out.println("Original:      " + original);
        System.out.println("Rotar 2:       " + rotarDerecha(original, 2));
        System.out.println("Rotar 7 (=2):  " + rotarDerecha(original, 7));
    }
}
