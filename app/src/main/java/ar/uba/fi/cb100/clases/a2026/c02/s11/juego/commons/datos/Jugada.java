package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Jugada (dato inmutable): quién jugó y en qué número de jugada.
 * La partida guarda la lista de jugadas: es el historial.
 *
 * <p>Qué se jugó depende del juego, y lo agrega cada subclase:
 * <pre>
 *   Jugada&lt;J&gt;
 *   ├── JugadaDeCasitaRobada       + la carta, adónde fue y qué pasó
 *   └── JugadaEnTablero&lt;J&gt;         + la posición
 *       └── JugadaConFicha&lt;F&gt;      + la ficha que se puso
 * </pre>
 *
 * @param <J> tipo de jugador del juego
 */
public class Jugada<J extends Jugador> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final J jugador;
    private final int numero;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugador quién jugó, no null
     * @param numero número de jugada dentro de la partida, mayor que cero
     * @throws IllegalArgumentException si algún parámetro no cumple
     */
    public Jugada(J jugador, int numero) {
        ValidacionesUtiles.validarDistintoDeNull(jugador, "jugador");
        ValidacionesUtiles.esMayorQueCero(numero, "numero");
        this.jugador = jugador;
        this.numero = numero;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public J getJugador() {
        return jugador;
    }

    public int getNumero() {
        return numero;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return numero + ". " + jugador;
    }
}
