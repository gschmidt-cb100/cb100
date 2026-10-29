package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;

/**
 * TDA JugadaDeCasitaRobada (dato inmutable): una {@link Jugada} que recuerda
 * el tiro, qué pasó y cuántas cartas ganó el jugador.
 *
 * <p>Hereda de Jugada directamente: no es una JugadaEnTablero, porque la
 * casita robada no tiene tablero.
 */
public class JugadaDeCasitaRobada extends Jugada<Jugador> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Tiro tiro;
    private final ResultadoDeTiro resultado;
    private final int cartasGanadas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugador quién tiró, no null
     * @param tiro qué tiró y adónde, no null
     * @param resultado qué pasó, no null
     * @param cartasGanadas cuántas cartas pasaron a su casita (contando la tirada), mayor o igual a cero
     * @param numero número de jugada dentro de la partida, mayor que cero
     */
    public JugadaDeCasitaRobada(Jugador jugador, Tiro tiro, ResultadoDeTiro resultado, int cartasGanadas, int numero) {
        super(jugador, numero);
        ValidacionesUtiles.validarDistintoDeNull(tiro, "tiro");
        ValidacionesUtiles.validarDistintoDeNull(resultado, "resultado");
        ValidacionesUtiles.validarFalso(cartasGanadas < 0, "Las cartas ganadas no pueden ser negativas.");
        this.tiro = tiro;
        this.resultado = resultado;
        this.cartasGanadas = cartasGanadas;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Tiro getTiro() {
        return tiro;
    }

    public ResultadoDeTiro getResultado() {
        return resultado;
    }

    public int getCartasGanadas() {
        return cartasGanadas;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String nombre = getJugador().getNombre();
        switch (this.resultado) {
            case ROBO:
                return nombre + " le robó la casita a " + this.tiro.getDuenioDeLaCasita().getNombre()
                        + " con el " + this.tiro.getCarta() + " (+" + this.cartasGanadas + ")";
            case LEVANTO:
                return nombre + " levantó con el " + this.tiro.getCarta() + " (+" + this.cartasGanadas + ")";
            default:
                return nombre + " dejó el " + this.tiro.getCarta() + " en la mesa";
        }
    }
}
