package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Ficha;

/**
 * Las fichas del tateti. Implementa {@link Ficha}: así puede ir en un
 * Tablero, un Jugador y una Partida de commons.
 */
public enum FichaDeTateti implements Ficha {
    X,
    O;

    @Override
    public String getNombre() {
        return name();
    }
}
