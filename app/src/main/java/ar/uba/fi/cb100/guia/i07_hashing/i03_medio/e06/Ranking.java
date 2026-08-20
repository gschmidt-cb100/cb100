package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * e06: armar un ranking a partir de un diccionario de puntajes.
 * Un HashMap no tiene orden, así que la estrategia es: sacar las entradas
 * a una lista y ordenarlas con un {@link Comparator} compuesto:
 * primero por puntaje descendente y, ante empate, por nombre ascendente.
 */
public final class Ranking {

    private Ranking() {
    }

    /**
     * Devuelve los nombres ordenados por puntaje descendente.
     * Si dos jugadores empatan en puntaje, van en orden alfabético ascendente.
     *
     * @param puntajes mapa nombre → puntaje
     * @return lista de nombres, del mejor puntaje al peor
     */
    public static List<String> ranking(Map<String, Integer> puntajes) {
        List<Map.Entry<String, Integer>> entradas = new ArrayList<>(puntajes.entrySet());
        // Comparador compuesto: puntaje DESC, y ante empate nombre ASC.
        entradas.sort(Comparator
                .comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey));

        List<String> nombres = new ArrayList<>(entradas.size());
        for (Map.Entry<String, Integer> entrada : entradas) {
            nombres.add(entrada.getKey());
        }
        return nombres;
    }

    public static void main(String[] args) {
        Map<String, Integer> puntajes = Map.of("ana", 90, "beto", 95, "carla", 90);
        System.out.println("Puntajes: " + puntajes);
        System.out.println("Ranking: " + ranking(puntajes));
    }
}
