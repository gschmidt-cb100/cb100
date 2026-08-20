package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * e01: agrupar palabras según su letra inicial (en minúscula).
 * El punto del ejercicio es {@code computeIfAbsent}: si la clave todavía
 * no existe, crea la lista vacía; si ya existe, devuelve la que estaba.
 * Así evitamos el clásico "if (!mapa.containsKey(...)) mapa.put(...)".
 */
public final class AgruparPorInicial {

    private AgruparPorInicial() {
    }

    /**
     * Agrupa las palabras por su letra inicial, normalizada a minúscula.
     * Las palabras se conservan en el orden en que aparecen en la lista.
     *
     * @param palabras lista de palabras no vacías
     * @return mapa inicial (minúscula) → lista de palabras que empiezan con ella
     */
    public static Map<Character, List<String>> agrupar(List<String> palabras) {
        Map<Character, List<String>> grupos = new HashMap<>();
        for (String palabra : palabras) {
            char inicial = Character.toLowerCase(palabra.charAt(0));
            // computeIfAbsent: crea la lista sólo la primera vez que aparece la inicial.
            grupos.computeIfAbsent(inicial, k -> new ArrayList<>()).add(palabra);
        }
        return grupos;
    }

    public static void main(String[] args) {
        List<String> palabras = List.of("Mate", "mesa", "bondi", "Bife", "asado");
        System.out.println("Palabras: " + palabras);
        System.out.println("Agrupadas: " + agrupar(palabras));
    }
}
