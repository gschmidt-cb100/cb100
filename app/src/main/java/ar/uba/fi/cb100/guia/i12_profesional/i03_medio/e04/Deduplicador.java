package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * e04: deduplicar preservando el orden de primera aparición.
 *
 * <p><b>La alternativa ingenua</b> es, por cada elemento, preguntar
 * {@code resultado.contains(elemento)}: ese contains recorre la lista
 * entera (U5), así que el total es O(n²) — con un millón de elementos,
 * del orden del billón de comparaciones.</p>
 *
 * <p><b>La versión profesional</b> lleva un {@code HashSet} auxiliar de
 * "ya vistos": {@code add} devuelve {@code false} si el elemento ya estaba,
 * en O(1) esperado (U7). Total: O(n) en tiempo, O(n) extra en espacio —
 * cambiamos memoria por velocidad, el trade-off clásico. Un
 * {@code LinkedHashSet} lograría lo mismo en una línea; acá lo hacemos
 * explícito para que se vea el mecanismo.</p>
 */
public final class Deduplicador {

    private Deduplicador() {
    }

    /**
     * La lista sin duplicados, conservando la primera aparición de cada
     * elemento. O(n) esperado gracias al Set auxiliar.
     */
    public static <T> List<T> sinDuplicados(List<T> elementos) {
        Set<T> vistos = new HashSet<>();
        List<T> resultado = new ArrayList<>();
        for (T elemento : elementos) {
            if (vistos.add(elemento)) {             // false = ya estaba (U7)
                resultado.add(elemento);
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println(sinDuplicados(List.of(3, 1, 3, 2, 1, 3)));
        // [3, 1, 2]
        System.out.println(sinDuplicados(List.of("ana", "beto", "ana", "carla")));
        // [ana, beto, carla]
    }
}
