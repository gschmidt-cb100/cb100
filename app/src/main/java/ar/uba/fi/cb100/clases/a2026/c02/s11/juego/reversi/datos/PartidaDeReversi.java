package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.datos;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.TableroMatriz;

/**
 * Partida de reversi (dato): una {@link PartidaConFicha} sobre un tablero de lado par
 * que además recuerda si en el último turno alguien tuvo que pasar por no
 * tener jugadas.
 *
 * Invariante: el lado es par y al menos 4 (para las 4 fichas iniciales del centro).
 */
public class PartidaDeReversi extends PartidaConFicha<FichaDeReversi> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private JugadorConFicha<FichaDeReversi> jugadorQuePaso;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una partida con el tablero vacío (el administrador pone las fichas iniciales).
     * @param lado filas y columnas del tablero: par y al menos 4
     * @param jugadores los 2 jugadores, con NEGRA y BLANCA
     */
    public PartidaDeReversi(int lado, List<JugadorConFicha<FichaDeReversi>> jugadores) {
        super(new TableroMatriz<>(lado, lado), jugadores);
        ValidacionesUtiles.validarFalso(lado < 4 || lado % 2 != 0, "El lado del tablero de reversi tiene que ser par y al menos 4.");
        this.jugadorQuePaso = null;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Registra que un jugador no tenía jugadas y pasó el turno.
     * @param jugador quien pasó, no null
     */
    public void registrarPase(JugadorConFicha<FichaDeReversi> jugador) {
        ValidacionesUtiles.validarDistintoDeNull(jugador, "jugador");
        this.jugadorQuePaso = jugador;
    }

    /**
     * Olvida el último pase (se llama al hacer una jugada).
     */
    public void limpiarPase() {
        this.jugadorQuePaso = null;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * @return el jugador que pasó en el último turno, o null si nadie pasó
     */
    public JugadorConFicha<FichaDeReversi> getJugadorQuePaso() {
        return jugadorQuePaso;
    }
}
