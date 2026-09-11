package ar.uba.fi.cb100.librerias.imagen;

import java.awt.event.KeyEvent;

/**
 * Las teclas que entiende {@link JuegoVisual}. Las flechas y las letras
 * W, A, S, D son lo mismo: un juego de grilla se maneja con cualquiera de las
 * dos. Todo lo que no esté acá llega como {@link #OTRA}.
 *
 * <p>{@link #CERRAR} no es una tecla: es lo que devuelve
 * {@link JuegoVisual#esperarTecla()} cuando el usuario cerró la ventana, para
 * que el ciclo del juego pueda terminar sin quedarse esperando para siempre.
 */
public enum Tecla {

    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1),
    ESPACIO(0, 0),
    ENTER(0, 0),
    ESCAPE(0, 0),
    RETROCESO(0, 0),
    OTRA(0, 0),
    CERRAR(0, 0);

    private final int deltaFila;
    private final int deltaColumna;

    Tecla(int deltaFila, int deltaColumna) {
        this.deltaFila = deltaFila;
        this.deltaColumna = deltaColumna;
    }

    /** true para las cuatro flechas. */
    public boolean esDireccion() {
        return deltaFila != 0 || deltaColumna != 0;
    }

    /** Cuánto cambia la fila al moverse en esta dirección: -1, 0 o 1. */
    public int deltaFila() {
        return deltaFila;
    }

    /** Cuánto cambia la columna al moverse en esta dirección: -1, 0 o 1. */
    public int deltaColumna() {
        return deltaColumna;
    }

    /**
     * Traduce el código de tecla de Swing ({@code KeyEvent.VK_...}) a una
     * {@code Tecla}. Es lo único de esta clase que sabe de Swing.
     */
    public static Tecla desdeCodigo(int codigoDeTecla) {
        return switch (codigoDeTecla) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> ARRIBA;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> ABAJO;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> IZQUIERDA;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> DERECHA;
            case KeyEvent.VK_SPACE -> ESPACIO;
            case KeyEvent.VK_ENTER -> ENTER;
            case KeyEvent.VK_ESCAPE -> ESCAPE;
            case KeyEvent.VK_BACK_SPACE, KeyEvent.VK_Z -> RETROCESO;
            default -> OTRA;
        };
    }
}
