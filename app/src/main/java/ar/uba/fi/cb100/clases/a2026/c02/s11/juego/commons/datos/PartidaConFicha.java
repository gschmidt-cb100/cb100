package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA PartidaConFicha (dato): una {@link PartidaDeTablero} cuyo tablero tiene fichas y
 * cuyos jugadores juegan cada uno con una ficha.
 *
 * <p>Agrega lo que sólo tiene sentido si hay fichas:
 * <ul>
 *   <li>Invariante: no hay dos jugadores con la misma ficha.</li>
 *   <li>Saber qué jugador juega con cierta ficha ({@link #getJugadorCon}).</li>
 * </ul>
 *
 * @param <F> tipo de ficha del juego
 */
public class PartidaConFicha<F extends Ficha> extends PartidaDeTablero<F, JugadorConFicha<F>> {

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param tablero tablero de fichas, no null
     * @param jugadores al menos 2 jugadores, con fichas distintas
     * @throws IllegalArgumentException si no se cumplen las condiciones
     */
    public PartidaConFicha(Tablero<F> tablero, List<JugadorConFicha<F>> jugadores) {
        super(tablero, jugadores);
        validarFichasDistintas(jugadores);
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param ficha ficha a buscar
     * @return el jugador que juega con esa ficha, o null si nadie
     */
    public JugadorConFicha<F> getJugadorCon(F ficha) {
        for (JugadorConFicha<F> jugador : getJugadores()) {
            if (jugador.getFicha().equals(ficha)) {
                return jugador;
            }
        }
        return null;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private static <F extends Ficha> void validarFichasDistintas(List<JugadorConFicha<F>> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            for (int j = i + 1; j < jugadores.size(); j++) {
                ValidacionesUtiles.validarFalso(jugadores.get(i).getFicha().equals(jugadores.get(j).getFicha()),
                        "Dos jugadores no pueden usar la misma ficha: " + jugadores.get(i).getFicha().getNombre() + ".");
            }
        }
    }
}
