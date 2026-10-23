package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Vagón: lo que todo vagón tiene en común, la <b>longitud</b> en metros.
 * <p>
 * Es abstracta porque un vagón "a secas" no existe: es de pasajeros o es de
 * carga. Las capacidades viven en las subclases ({@link VagonDePasajeros},
 * {@link VagonDeCarga}); acá se definen las operaciones que el {@link Tren}
 * necesita hacerle a <b>cualquier</b> vagón sin preguntarle de qué tipo es
 * (polimorfismo). Por defecto un vagón no lleva pasajeros ni carga: cada
 * subclase redefine lo que le corresponde.
 */
public abstract class Vagon {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final double longitud;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud} (en metros) es mayor que cero.
     * post: crea el vagón con esa longitud. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    protected Vagon(double longitud) {
        ValidacionesUtiles.esMayorQueCero(longitud, "longitud del vagón");
        this.longitud = longitud;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /** post: indica si el vagón está vacío (sin pasajeros ni carga). */
    public abstract boolean estaVacio();

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
