package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

/**
 * Interfaz común a las fichas de cualquier juego de tablero.
 *
 * <p>Cada juego define sus fichas como un enum que implementa esta interfaz
 * (FichaDeTateti: X y O; FichaDeReversi: NEGRA y BLANCA). Así todo lo que está
 * en commons (Tablero, Jugador, Partida, AdministradorDePartida) trabaja con
 * "una ficha" sin saber de qué juego es.
 *
 * <p>Notar que la ficha <b>no sabe cómo se dibuja</b>: eso lo decide la capa de
 * presentación. La ficha es un dato del juego, no de la pantalla.
 */
public interface Ficha {

    /**
     * @return un nombre legible para mostrar en mensajes ("X", "Negras")
     */
    String getNombre();
}
