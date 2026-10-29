package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Ficha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;

/**
 * La lógica común a los juegos de tablero en los que <b>cada jugada pone la
 * ficha del jugador en una celda vacía</b> (tateti, reversi, conecta 4, gomoku...).
 *
 * <p>Sigue siendo abstracta, pero ya resuelve tres pasos de la plantilla:
 * <ul>
 *   <li>{@link #esJugadaValida}: la celda está vacía.</li>
 *   <li>{@link #aplicarJugada}: poner la ficha del jugador.</li>
 *   <li>{@link #crearJugada}: el historial guarda una {@link JugadaConFicha}.</li>
 * </ul>
 * El tateti los usa tal cual. El reversi los <b>extiende</b> con
 * {@code super}: una jugada válida es una celda vacía <i>que además</i>
 * encierra fichas, y aplicar es poner la ficha <i>y además</i> dar vuelta las
 * encerradas.
 *
 * @param <F> tipo de ficha del juego
 * @param <P> tipo de partida del juego
 */
public abstract class AdministradorDePartidaConFicha<F extends Ficha, P extends PartidaConFicha<F>>
        extends AdministradorDePartidaDeTablero<F, JugadorConFicha<F>, P> {

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugadores al menos 2 jugadores con fichas distintas
     */
    protected AdministradorDePartidaConFicha(List<JugadorConFicha<F>> jugadores) {
        super(jugadores);
    }

//PASOS DE LA PLANTILLA YA RESUELTOS ----------------------------------------------------------------------

    /**
     * @return true si la celda está vacía
     */
    @Override
    public boolean esJugadaValida(Posicion posicion) {
        return getPartida().getTablero().estaVacio(posicion);
    }

    @Override
    protected String explicarJugadaInvalida(Posicion posicion) {
        return "La celda " + posicion + " está ocupada.";
    }

    /**
     * Pone la ficha del jugador en la posición.
     */
    @Override
    protected void aplicarJugada(Posicion posicion, JugadorConFicha<F> jugador) {
        getPartida().getTablero().colocar(posicion, jugador.getFicha());
    }

    @Override
    protected Jugada<JugadorConFicha<F>> crearJugada(JugadorConFicha<F> jugador, Posicion posicion, int numero) {
        return new JugadaConFicha<>(jugador, posicion, numero);
    }
}
