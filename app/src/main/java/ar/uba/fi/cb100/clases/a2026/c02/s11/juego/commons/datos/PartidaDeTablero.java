package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA PartidaDeTablero (dato): una {@link Partida} que se juega sobre un
 * tablero, y en la que cada jugada es <b>una posición</b> del tablero.
 *
 * <p>Agrega el tablero, y exige que el historial tenga {@link JugadaEnTablero}
 * (que sabe la posición). Así se puede preguntar dónde fue la última jugada.
 *
 * @param <T> lo que hay en cada celda del tablero (una ficha, una carta, ...)
 * @param <J> tipo de jugador
 */
public class PartidaDeTablero<T, J extends Jugador> extends Partida<J> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Tablero<T> tablero;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param tablero tablero vacío o preparado, no null
     * @param jugadores al menos 2 jugadores, todos distintos
     */
    public PartidaDeTablero(Tablero<T> tablero, List<J> jugadores) {
        super(jugadores);
        ValidacionesUtiles.validarDistintoDeNull(tablero, "tablero");
        this.tablero = tablero;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Como en Partida, pero la jugada tiene que ser una {@link JugadaEnTablero}.
     * @throws IllegalArgumentException si no es una JugadaEnTablero
     */
    @Override
    public void registrarJugada(Jugada<J> jugada) {
        ValidacionesUtiles.validarFalso(!(jugada instanceof JugadaEnTablero),
                "En una partida de tablero, cada jugada tiene que ser en una posición.");
        super.registrarJugada(jugada);
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return la posición de la última jugada, o null si todavía no se jugó
     */
    public Posicion getPosicionDeLaUltimaJugada() {
        Jugada<J> ultima = getUltimaJugada();
        if (ultima == null) {
            return null;
        }
        // El cast es seguro: registrarJugada sólo acepta JugadaEnTablero.
        return ((JugadaEnTablero<J>) ultima).getPosicion();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Tablero<T> getTablero() {
        return tablero;
    }
}
