package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Ficha;

/**
 * Las fichas del reversi: un disco negro de un lado y blanco del otro.
 * Además de lo que pide {@link Ficha}, sabe cuál es su contraria (darla vuelta).
 */
public enum FichaDeReversi implements Ficha {
    NEGRA("Negras"),
    BLANCA("Blancas");

    private final String nombre;

    FichaDeReversi(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @return la ficha del otro color
     */
    public FichaDeReversi getContraria() {
        return (this == NEGRA) ? BLANCA : NEGRA;
    }
}
