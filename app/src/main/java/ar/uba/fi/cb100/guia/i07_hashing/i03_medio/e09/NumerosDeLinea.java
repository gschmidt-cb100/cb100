package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * e09: para cada palabra buscada, listar en qué líneas de un texto aparece
 * (índices desde 0). Es el "grep -n" casero: recorremos las líneas una vez
 * y, por cada palabra de la línea que esté entre las buscadas, agregamos
 * el número de línea a su lista con computeIfAbsent.
 */
public final class NumerosDeLinea {

    private NumerosDeLinea() {
    }

    /**
     * Busca las palabras indicadas en cada línea y devuelve, por palabra,
     * la lista de índices de línea (desde 0) donde aparece. Una palabra
     * repetida en la misma línea registra el índice una sola vez.
     * Las palabras buscadas que no aparecen en ninguna línea no figuran
     * en el resultado.
     *
     * @param lineas   líneas de texto, indexadas desde 0
     * @param buscadas conjunto de palabras a rastrear
     * @return mapa palabra → lista ordenada de índices de línea donde aparece
     */
    public static Map<String, List<Integer>> ocurrencias(List<String> lineas, Set<String> buscadas) {
        Map<String, List<Integer>> resultado = new HashMap<>();
        for (int i = 0; i < lineas.size(); i++) {
            for (String palabra : lineas.get(i).split("\\s+")) {
                if (!buscadas.contains(palabra)) {
                    continue;
                }
                List<Integer> indices = resultado.computeIfAbsent(palabra, k -> new ArrayList<>());
                // Evitamos duplicar el índice si la palabra se repite en la línea.
                if (indices.isEmpty() || indices.get(indices.size() - 1) != i) {
                    indices.add(i);
                }
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<String> lineas = List.of(
                "el mate está listo",
                "no hay agua",
                "el agua está fría");
        Set<String> buscadas = Set.of("el", "agua");
        System.out.println("Ocurrencias: " + ocurrencias(lineas, buscadas));
    }
}
