package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA JugadaEnTablero (dato inmutable): una {@link Jugada} hecha en una
 * posición del tablero. Es la jugada del memotest (elegir la carta de una
 * posición), y la base de {@link JugadaConFicha}.
 *
 * @param <J> tipo de jugador del juego
 */
public class JugadaEnTablero<J extends Jugador> extends Jugada<J> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Posicion posicion;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugador quién jugó, no null
     * @param posicion dónde jugó, no null
     * @param numero número de jugada dentro de la partida, mayor que cero
     * @throws IllegalArgumentException si algún parámetro no cumple
     */
    public JugadaEnTablero(J jugador, Posicion posicion, int numero) {
        super(jugador, numero);
        ValidacionesUtiles.validarDistintoDeNull(posicion, "posicion");
        this.posicion = posicion;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Posicion getPosicion() {
        return posicion;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + " en " + posicion;
    }
}
