package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e09;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * e09: contar votos y encontrar al ganador de una elección.
 * Conteo con {@code merge} (el patrón clásico de acumular en un mapa)
 * y búsqueda del máximo recorriendo las entradas.
 */
public final class Escrutinio {

    private Escrutinio() {
    }

    /**
     * Cuenta cuántos votos recibió cada candidato.
     *
     * @param votos lista de votos (cada elemento es el nombre del candidato votado)
     * @return mapa candidato → cantidad de votos
     */
    public static Map<String, Integer> escrutinio(List<String> votos) {
        Map<String, Integer> conteo = new HashMap<>();
        for (String voto : votos) {
            conteo.merge(voto, 1, Integer::sum);
        }
        return conteo;
    }

    /**
     * Devuelve el candidato con más votos, o {@code null} si el conteo está vacío.
     * Si hay empate, devuelve alguno de los empatados.
     *
     * @param conteo mapa candidato → cantidad de votos
     * @return nombre del ganador, o {@code null}
     */
    public static String ganador(Map<String, Integer> conteo) {
        String ganador = null;
        int maximo = 0;
        for (Map.Entry<String, Integer> entrada : conteo.entrySet()) {
            if (ganador == null || entrada.getValue() > maximo) {
                ganador = entrada.getKey();
                maximo = entrada.getValue();
            }
        }
        return ganador;
    }

    public static void main(String[] args) {
        List<String> votos = List.of("Ana", "Bruno", "Ana", "Carla", "Ana", "Bruno");
        Map<String, Integer> conteo = escrutinio(votos);
        System.out.println("Conteo: " + conteo);
        System.out.println("Ganador: " + ganador(conteo));
    }
}
