package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e01;

import java.util.HashMap;
import java.util.Map;

/**
 * e01: contar cuántas veces aparece cada carácter de un texto.
 * Es el "hola mundo" de los diccionarios: la clave es el carácter
 * y el valor es la cantidad de apariciones.
 */
public final class Frecuencias {

    private Frecuencias() {
    }

    /**
     * Devuelve un mapa con la cantidad de apariciones de cada carácter de {@code s}.
     *
     * @param s texto a analizar
     * @return mapa carácter → cantidad de apariciones
     */
    public static Map<Character, Integer> frecuencias(String s) {
        Map<Character, Integer> conteo = new HashMap<>();
        for (char c : s.toCharArray()) {
            // merge: si la clave no está arranca en 1, si está le suma 1.
            conteo.merge(c, 1, Integer::sum);
        }
        return conteo;
    }

    public static void main(String[] args) {
        String texto = "banana";
        System.out.println("Texto: " + texto);
        System.out.println("Frecuencias: " + frecuencias(texto));
    }
}
