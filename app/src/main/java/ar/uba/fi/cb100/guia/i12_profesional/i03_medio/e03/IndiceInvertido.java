package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e03;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * e03: índice invertido — el corazón de cualquier buscador.
 *
 * <p>En vez de "documento → palabras" guardamos "palabra → documentos".
 * Así, responder "¿dónde aparece <i>java</i>?" es UNA búsqueda en el mapa
 * en O(1) esperado, en lugar de recorrer todos los documentos.</p>
 *
 * <p><b>Elección de estructuras:</b> {@code HashMap} como mapa principal
 * (U7: sólo necesitamos buscar por palabra exacta, no recorrer en orden) y
 * {@code TreeSet} como valor (U8): elimina índices repetidos cuando una
 * palabra aparece dos veces en el mismo documento y los mantiene ordenados,
 * que es como uno espera leer los resultados. Si el orden no importara,
 * un {@code HashSet} alcanzaba.</p>
 */
public final class IndiceInvertido {

    private IndiceInvertido() {
    }

    /**
     * Palabra (en minúsculas, separadas por espacios) → índices de los
     * documentos donde aparece. Costo: O(total de palabras).
     */
    public static Map<String, Set<Integer>> indexar(List<String> documentos) {
        Map<String, Set<Integer>> indice = new HashMap<>();
        for (int i = 0; i < documentos.size(); i++) {
            for (String palabra : documentos.get(i).toLowerCase().split("\\s+")) {
                if (!palabra.isEmpty()) {
                    indice.computeIfAbsent(palabra, clave -> new TreeSet<>()).add(i);
                }
            }
        }
        return indice;
    }

    public static void main(String[] args) {
        Map<String, Set<Integer>> indice = indexar(List.of(
                "java es un lenguaje",
                "python es otro lenguaje",
                "java usa la jvm"));
        System.out.println(indice.get("java"));      // [0, 2]
        System.out.println(indice.get("lenguaje"));  // [0, 1]
        System.out.println(indice.get("jvm"));       // [2]
        System.out.println(indice.get("cobol"));     // null (no aparece)
    }
}
