package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * e10: para cada palabra, el índice de su última aparición en el arreglo.
 * Aprovechamos que {@code put} sobre una clave existente <em>pisa</em> el
 * valor anterior: recorriendo de izquierda a derecha queda el último índice.
 */
public final class UltimaAparicion {

    private UltimaAparicion() {
    }

    /**
     * Devuelve un mapa palabra → índice de su última aparición en {@code palabras}.
     *
     * @param palabras arreglo de palabras
     * @return mapa con el último índice de cada palabra
     */
    public static Map<String, Integer> ultimaPosicion(String[] palabras) {
        Map<String, Integer> ultima = new HashMap<>();
        for (int i = 0; i < palabras.length; i++) {
            // put pisa el valor anterior: al final queda el mayor indice.
            ultima.put(palabras[i], i);
        }
        return ultima;
    }

    public static void main(String[] args) {
        String[] palabras = {"sol", "luna", "sol", "mar"};
        System.out.println("Palabras: " + Arrays.toString(palabras));
        System.out.println("Ultima posicion: " + ultimaPosicion(palabras));
    }
}
