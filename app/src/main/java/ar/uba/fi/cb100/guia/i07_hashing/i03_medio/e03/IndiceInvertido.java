package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * e03: índice invertido, la estructura detrás de cualquier buscador.
 * En vez de "documento → texto", armamos "palabra → documentos que la
 * contienen". Usamos un {@code Set} de nombres para que un documento no
 * figure dos veces aunque repita la palabra.
 */
public final class IndiceInvertido {

    private IndiceInvertido() {
    }

    /**
     * Construye el índice invertido de un conjunto de documentos.
     * Las palabras se normalizan a minúsculas y se separan por cualquier
     * secuencia de caracteres que no sean letras.
     *
     * @param docs mapa nombre de documento → contenido
     * @return mapa palabra (minúscula) → conjunto de nombres de documentos que la contienen
     */
    public static Map<String, Set<String>> indexar(Map<String, String> docs) {
        Map<String, Set<String>> indice = new HashMap<>();
        for (Map.Entry<String, String> doc : docs.entrySet()) {
            String nombre = doc.getKey();
            // Separamos por todo lo que NO sea letra (incluye acentuadas por \p{L}).
            String[] palabras = doc.getValue().toLowerCase().split("[^\\p{L}]+");
            for (String palabra : palabras) {
                if (palabra.isEmpty()) {
                    continue; // split puede dejar un primer token vacío.
                }
                indice.computeIfAbsent(palabra, k -> new HashSet<>()).add(nombre);
            }
        }
        return indice;
    }

    public static void main(String[] args) {
        Map<String, String> docs = Map.of(
                "a.txt", "el mate está listo",
                "b.txt", "el bondi no llega, el bondi tarda");
        Map<String, Set<String>> indice = indexar(docs);
        System.out.println("¿Dónde aparece 'el'? " + indice.get("el"));
        System.out.println("¿Dónde aparece 'bondi'? " + indice.get("bondi"));
    }
}
