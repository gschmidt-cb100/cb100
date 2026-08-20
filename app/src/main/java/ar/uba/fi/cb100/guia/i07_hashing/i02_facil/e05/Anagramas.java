package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e05;

import java.util.HashMap;
import java.util.Map;

/**
 * e05: decidir si dos textos son anagramas comparando mapas de frecuencias.
 * Dos textos son anagramas si usan exactamente las mismas letras la misma
 * cantidad de veces (ignorando mayúsculas y espacios).
 */
public final class Anagramas {

    private Anagramas() {
    }

    /**
     * Indica si {@code a} y {@code b} son anagramas, ignorando
     * mayúsculas/minúsculas y espacios.
     *
     * @param a primer texto
     * @param b segundo texto
     * @return {@code true} si son anagramas
     */
    public static boolean sonAnagramas(String a, String b) {
        // Dos mapas de frecuencias iguales <=> mismas letras, mismas cantidades.
        return frecuencias(a).equals(frecuencias(b));
    }

    /** Cuenta las apariciones de cada carácter, normalizando a minúsculas y sin espacios. */
    private static Map<Character, Integer> frecuencias(String s) {
        Map<Character, Integer> conteo = new HashMap<>();
        for (char c : s.toLowerCase().toCharArray()) {
            if (c != ' ') {
                conteo.merge(c, 1, Integer::sum);
            }
        }
        return conteo;
    }

    public static void main(String[] args) {
        System.out.println("'Roma' y 'amor': " + sonAnagramas("Roma", "amor"));
        System.out.println("'gato' y 'perro': " + sonAnagramas("gato", "perro"));
    }
}
