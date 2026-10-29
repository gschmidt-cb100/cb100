package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadaEnTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;

/**
 * La lógica común a los juegos <b>de tablero</b>: el movimiento es una
 * {@link Posicion}. Agrega a {@link AdministradorDePartida}:
 * <ul>
 *   <li>validar que la posición esté dentro del tablero;</li>
 *   <li>que el historial guarde {@link JugadaEnTablero};</li>
 *   <li>listar las jugadas válidas recorriendo el tablero, y el gancho para marcarlas en pantalla;</li>
 *   <li>el gancho {@link #prepararTablero}.</li>
 * </ul>
 *
 * @param <T> lo que hay en cada celda del tablero
 * @param <J> tipo de jugador
 * @param <P> tipo de partida del juego
 */
public abstract class AdministradorDePartidaDeTablero<T, J extends Jugador, P extends PartidaDeTablero<T, J>>
        extends AdministradorDePartida<J, Posicion, P> {

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugadores al menos 2 jugadores
     */
    protected AdministradorDePartidaDeTablero(List<J> jugadores) {
        super(jugadores);
    }

//GANCHOS PROPIOS DE LOS JUEGOS DE TABLERO ----------------------------------------------------------------

    /**
     * Prepara el tablero de una partida nueva. Por defecto, lo deja vacío.
     * @param partida partida recién creada
     */
    protected void prepararTablero(P partida) {
    }

    /**
     * @return true si conviene que la pantalla marque dónde se puede jugar. Por defecto, no.
     */
    public boolean debeMarcarJugadasValidas() {
        return false;
    }

//METODOS DE AdministradorDePartida -----------------------------------------------------------------------

    /**
     * En un juego de tablero, preparar la partida es preparar el tablero.
     */
    @Override
    protected final void prepararPartida(P partida) {
        prepararTablero(partida);
    }

    @Override
    protected void validarMovimiento(Posicion posicion) {
        ValidacionesUtiles.validarFalso(!getPartida().getTablero().estaDentro(posicion), posicion + " está fuera del tablero.");
    }

    @Override
    protected Jugada<J> crearJugada(J jugador, Posicion posicion, int numero) {
        return new JugadaEnTablero<>(jugador, posicion, numero);
    }

    @Override
    protected String explicarJugadaInvalida(Posicion posicion) {
        return "No se puede jugar en " + posicion + ".";
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return las posiciones donde el jugador de turno puede jugar (vacía si la partida terminó)
     */
    public List<Posicion> getJugadasValidas() {
        List<Posicion> validas = new ArrayList<>();
        if (getPartida().estaEnCurso()) {
            for (Posicion posicion : getPartida().getTablero().getPosiciones()) {
                if (esJugadaValida(posicion)) {
                    validas.add(posicion);
                }
            }
        }
        return validas;
    }
}
