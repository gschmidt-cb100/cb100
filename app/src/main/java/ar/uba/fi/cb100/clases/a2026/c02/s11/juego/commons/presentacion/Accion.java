package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;

/**
 * Lo que el usuario quiere hacer, sin importar con qué tecla (o botón, o
 * comando) lo pidió.
 *
 * <p>El controlador del juego trabaja con acciones, no con teclas: la
 * traducción tecla → acción la hace cada {@link InterfazDeJuego}. Así, si
 * mañana el juego se maneja con el mouse o por consola, el controlador no cambia.
 */
public enum Accion {

    MOVER_ARRIBA(Direccion.ARRIBA),
    MOVER_ABAJO(Direccion.ABAJO),
    MOVER_IZQUIERDA(Direccion.IZQUIERDA),
    MOVER_DERECHA(Direccion.DERECHA),
    JUGAR(null),
    NUEVA_PARTIDA(null),
    SALIR(null),
    NINGUNA(null);

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Direccion direccion;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    Accion(Direccion direccion) {
        this.direccion = direccion;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return true si la acción es mover el cursor
     */
    public boolean esMovimiento() {
        return this.direccion != null;
    }

    /**
     * @return la dirección del movimiento, o null si la acción no es un movimiento
     */
    public Direccion getDireccion() {
        return direccion;
    }
}
