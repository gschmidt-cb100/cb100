package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * e01: ranking de jugadores. Un {@link TreeMap} mantiene las claves
 * (los puntajes) siempre ordenadas, así que obtener los mejores es
 * simplemente recorrer la vista descendente.
 */
public final class Ranking {

    private Ranking() {
    }

    /**
     * Devuelve los nombres de los 3 jugadores con mayor puntaje,
     * en orden descendente de puntaje. Si hay menos de 3, devuelve todos.
     *
     * @param puntajeAJugador mapa puntaje → nombre del jugador
     * @return lista con los nombres del podio
     */
    public static List<String> topTres(TreeMap<Integer, String> puntajeAJugador) {
        List<String> podio = new ArrayList<>();
        // descendingMap da la misma información pero de mayor a menor clave.
        for (Map.Entry<Integer, String> entrada : puntajeAJugador.descendingMap().entrySet()) {
            if (podio.size() == 3) {
                break;
            }
            podio.add(entrada.getValue());
        }
        return podio;
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> torneo = new TreeMap<>();
        torneo.put(870, "Mora");
        torneo.put(1200, "Julián");
        torneo.put(990, "Sofía");
        torneo.put(450, "Pedro");
        System.out.println("Puntajes: " + torneo);
        System.out.println("Top 3: " + topTres(torneo));
    }
}
