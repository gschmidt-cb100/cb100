package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.CartaEspanola;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.PartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.InterfazDeJuego;

/**
 * {@link InterfazDeJuego} de la casita robada: además sabe mostrar la mesa,
 * las casitas, el mazo y la mano del jugador de turno.
 */
public interface InterfazDeCasitaRobada extends InterfazDeJuego {

    /**
     * @param partida partida a mostrar, no null
     * @param cursor celda del cursor (según {@link DisposicionDeCasitaRobada}), no null
     * @param cartaElegida carta de la mano que el jugador eligió, o null si todavía ninguna
     */
    void mostrar(PartidaDeCasitaRobada partida, Posicion cursor, CartaEspanola cartaElegida);
}
