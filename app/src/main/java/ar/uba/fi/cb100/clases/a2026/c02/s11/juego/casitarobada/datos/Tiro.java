package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;

/**
 * TDA Tiro (dato inmutable): el <b>movimiento</b> de la casita robada. Es lo
 * que el AdministradorDePartida recibe en {@code jugar(movimiento)}, como en
 * los juegos de tablero recibe una Posicion.
 *
 * <p>Un tiro es una carta de la mano y adónde va:
 * <ul>
 *   <li>a la mesa: levanta las que coinciden, o si no hay, queda en la mesa;</li>
 *   <li>a la casita de otro jugador: se la roba (si la de arriba coincide).</li>
 * </ul>
 * Se crea con {@link #aLaMesa} o {@link #aLaCasitaDe}.
 */
public class Tiro {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final CartaEspanola carta;
    private final Jugador duenioDeLaCasita;      // null: va a la mesa

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    private Tiro(CartaEspanola carta, Jugador duenioDeLaCasita) {
        ValidacionesUtiles.validarDistintoDeNull(carta, "carta");
        this.carta = carta;
        this.duenioDeLaCasita = duenioDeLaCasita;
    }

    /**
     * @param carta carta de la mano, no null
     * @return el tiro de esa carta a la mesa
     */
    public static Tiro aLaMesa(CartaEspanola carta) {
        return new Tiro(carta, null);
    }

    /**
     * @param carta carta de la mano, no null
     * @param duenio jugador al que se le quiere robar la casita, no null
     * @return el tiro de esa carta a la casita de ese jugador
     */
    public static Tiro aLaCasitaDe(CartaEspanola carta, Jugador duenio) {
        ValidacionesUtiles.validarDistintoDeNull(duenio, "duenio");
        return new Tiro(carta, duenio);
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    public boolean esALaMesa() {
        return this.duenioDeLaCasita == null;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public CartaEspanola getCarta() {
        return carta;
    }

    /**
     * @return el dueño de la casita a la que va, o null si va a la mesa
     */
    public Jugador getDuenioDeLaCasita() {
        return duenioDeLaCasita;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return carta + (esALaMesa() ? " a la mesa" : " a la casita de " + duenioDeLaCasita.getNombre());
    }
}
