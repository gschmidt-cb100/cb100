package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Un vagón que no es cargable: suma longitud al tren y nada más. Está para
 * mostrar que el tren no supone que todo vagón lleva carga.
 */
public class VagonComedor extends Vagon {

    /** pre: {@code longitud} es mayor que cero. post: crea el vagón comedor. */
    public VagonComedor(double longitud) {
        super(longitud);
    }
}
