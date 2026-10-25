package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Lo que todo vagón tiene en común: la longitud en metros. Un vagón puede ser
 * cargable ({@link VagonConCargas}) o no (un {@link VagonComedor}): el tren
 * los engancha a todos por igual y, al cargar, sólo mira los que implementan
 * {@link VagonCargable}.
 */
public abstract class Vagon {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final double longitud;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud} (en metros) es mayor que cero.
     * post: crea el vagón con esa longitud.
     */
    protected Vagon(double longitud) {
        ValidacionesUtiles.esMayorQueCero(longitud, "longitud del vagón");
        this.longitud = longitud;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getSimpleName() + " de " + longitud + " m";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve la longitud [m] del vagón. */
    public double getLongitud() {
        return longitud;
    }
}
