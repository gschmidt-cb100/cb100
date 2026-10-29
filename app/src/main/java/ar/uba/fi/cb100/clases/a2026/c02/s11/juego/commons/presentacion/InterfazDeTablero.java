package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;

/**
 * {@link InterfazDeJuego} de un juego de tablero: además sabe dibujar el tablero.
 *
 * <p>Es genérica en <b>lo que hay en cada celda</b> (una ficha, una carta...),
 * no en "fichas": sirve igual para juegos con y sin fichas.
 *
 * @param <T> lo que hay en cada celda del tablero
 */
public interface InterfazDeTablero<T> extends InterfazDeJuego {

    /**
     * Dibuja el tablero de la partida.
     * @param partida partida a mostrar, no null
     * @param cursor posición del cursor, no null
     * @param posicionesMarcadas posiciones a resaltar (por ejemplo, las jugadas válidas); puede estar vacía
     */
    void mostrar(PartidaDeTablero<T, ?> partida, Posicion cursor, List<Posicion> posicionesMarcadas);
}
