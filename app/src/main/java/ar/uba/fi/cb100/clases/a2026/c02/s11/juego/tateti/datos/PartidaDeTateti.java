package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.datos;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.TableroMatriz;

/**
 * Partida de tateti (dato): una {@link PartidaConFicha} que además sabe
 * cuántas fichas en línea hacen falta para ganar.
 *
 * <p>Herencia: todo lo de Partida (tablero, turnos, historial, ganador) y lo
 * de PartidaConFicha (fichas distintas, jugador con cierta ficha) se
 * reutiliza; sólo se agrega lo propio del tateti. Con 3x3 y 3 en línea es el
 * tateti clásico; con 15x15 y 5 en línea es el gomoku.
 *
 * Invariante: 2 <= fichasEnLineaParaGanar <= max(filas, columnas).
 */
public class PartidaDeTateti extends PartidaConFicha<FichaDeTateti> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int fichasEnLineaParaGanar;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una partida con el tablero vacío.
     * @param filas filas del tablero, mayor que cero
     * @param columnas columnas del tablero, mayor que cero
     * @param fichasEnLineaParaGanar cuántas en línea ganan, entre 2 y el lado mayor del tablero
     * @param jugadores los 2 jugadores, con X y O
     */
    public PartidaDeTateti(int filas, int columnas, int fichasEnLineaParaGanar, List<JugadorConFicha<FichaDeTateti>> jugadores) {
        super(new TableroMatriz<>(filas, columnas), jugadores);
        ValidacionesUtiles.validarRango(fichasEnLineaParaGanar, 2, Math.max(filas, columnas), "fichasEnLineaParaGanar");
        this.fichasEnLineaParaGanar = fichasEnLineaParaGanar;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getFichasEnLineaParaGanar() {
        return fichasEnLineaParaGanar;
    }
}
