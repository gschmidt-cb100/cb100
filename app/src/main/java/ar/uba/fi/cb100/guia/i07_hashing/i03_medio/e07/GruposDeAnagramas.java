package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * e07: agrupar palabras que son anagramas entre sí.
 * El truco es elegir bien la clave del diccionario: dos palabras son
 * anagramas si y sólo si, al ordenar sus letras, dan la misma cadena.
 * Esa "forma canónica" es la clave del HashMap; el valor, la lista de
 * palabras del grupo.
 */
public final class GruposDeAnagramas {

    private GruposDeAnagramas() {
    }

    /**
     * Agrupa las palabras que son anagramas entre sí.
     *
     * @param palabras lista de palabras
     * @return colección de grupos; cada grupo es la lista de palabras
     *         que comparten las mismas letras
     */
    public static Collection<List<String>> grupos(List<String> palabras) {
        Map<String, List<String>> grupos = new HashMap<>();
        for (String palabra : palabras) {
            grupos.computeIfAbsent(clave(palabra), k -> new ArrayList<>()).add(palabra);
        }
        return grupos.values();
    }

    /** Forma canónica de una palabra: sus letras ordenadas alfabéticamente. */
    private static String clave(String palabra) {
        char[] letras = palabra.toCharArray();
        Arrays.sort(letras);
        return new String(letras);
    }

    public static void main(String[] args) {
        List<String> palabras = List.of("roma", "amor", "ramo", "mora", "perro", "sol");
        System.out.println("Palabras: " + palabras);
        System.out.println("Grupos: " + grupos(palabras));
    }
}
