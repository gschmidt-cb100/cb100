package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

/**
 * TDA JugadaConFicha (dato inmutable): una {@link JugadaEnTablero} que además
 * recuerda qué ficha se puso.
 *
 * <p>La ficha es la del jugador, pero se guarda igual: el historial queda
 * completo por sí mismo (por ejemplo, para mostrarlo o para deshacer).
 *
 * @param <F> tipo de ficha del juego
 */
public class JugadaConFicha<F extends Ficha> extends JugadaEnTablero<JugadorConFicha<F>> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final F ficha;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugador quién jugó, no null
     * @param posicion dónde puso la ficha, no null
     * @param numero número de jugada dentro de la partida, mayor que cero
     * @throws IllegalArgumentException si algún parámetro no cumple
     */
    public JugadaConFicha(JugadorConFicha<F> jugador, Posicion posicion, int numero) {
        super(jugador, posicion, numero);
        this.ficha = jugador.getFicha();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public F getFicha() {
        return ficha;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return getNumero() + ". " + getJugador().getNombre() + " pone " + ficha.getNombre() + " en " + getPosicion();
    }
}
