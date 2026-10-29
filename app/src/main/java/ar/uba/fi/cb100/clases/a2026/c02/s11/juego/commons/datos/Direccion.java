package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

/**
 * Las 8 direcciones en las que se puede avanzar sobre un tablero.
 *
 * <p>Cada dirección sabe cuánto cambia la fila y la columna (-1, 0 o 1).
 * Así, "avanzar" es una suma y no ocho {@code if}. La usan los dos juegos:
 * el tateti para buscar líneas y el reversi para buscar fichas a dar vuelta.
 * También la usa el cursor para moverse con las flechas.
 */
public enum Direccion {

    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1),
    ARRIBA_IZQUIERDA(-1, -1),
    ARRIBA_DERECHA(-1, 1),
    ABAJO_IZQUIERDA(1, -1),
    ABAJO_DERECHA(1, 1);

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int deltaFila;
    private final int deltaColumna;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    Direccion(int deltaFila, int deltaColumna) {
        this.deltaFila = deltaFila;
        this.deltaColumna = deltaColumna;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @return la dirección contraria (ARRIBA -> ABAJO, ARRIBA_DERECHA -> ABAJO_IZQUIERDA, ...)
     */
    public Direccion getOpuesta() {
        for (Direccion direccion : values()) {
            if (direccion.deltaFila == -this.deltaFila && direccion.deltaColumna == -this.deltaColumna) {
                return direccion;
            }
        }
        throw new IllegalStateException("No hay opuesta para " + this);   // no pasa nunca: las 8 tienen opuesta
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getDeltaFila() {
        return deltaFila;
    }

    public int getDeltaColumna() {
        return deltaColumna;
    }
}
