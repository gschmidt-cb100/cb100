package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una nota de una melodía: frecuencia (Hz), duración (ms) y amplitud.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarla.
 */
public class Nota {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private double frecuencia;
    private int duracion;
    private double amplitud;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code frecuencia} y {@code duracion} son mayores que cero;
     *      {@code amplitud} es mayor o igual a cero.
     * post: crea la nota. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Nota(double frecuencia, int duracion, double amplitud) {
        ValidacionesUtiles.esMayorQueCero(frecuencia, "frecuencia");
        ValidacionesUtiles.esMayorQueCero(duracion, "duración");
        ValidacionesUtiles.validarFalso(amplitud < 0, "La amplitud no puede ser negativa.");
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.amplitud = amplitud;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return frecuencia + " Hz, " + duracion + " ms";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public double getFrecuencia() {
        return frecuencia;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getAmplitud() {
        return amplitud;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * pre: {@code frecuencia} es mayor que cero.
     * post: la nota queda con esa frecuencia. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public void setFrecuencia(double frecuencia) {
        ValidacionesUtiles.esMayorQueCero(frecuencia, "frecuencia");
        this.frecuencia = frecuencia;
    }
}
