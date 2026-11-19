package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una opinión de un usuario sobre una serie: la serie, el puntaje y el estado.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarla.
 *
 * Hipótesis:
 *  H1. El puntaje no puede ser negativo. El enunciado no fija un máximo, así
 *      que no se valida un tope.
 */
public class Opinion {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Serie serie;
    private final double puntaje;
    private final EstadoOpinion estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code serie} y {@code estado} no son null; {@code puntaje} es
     *      mayor o igual a cero.
     * post: crea la opinión. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Opinion(Serie serie, double puntaje, EstadoOpinion estado) {
        ValidacionesUtiles.validarDistintoDeNull(serie, "serie");
        ValidacionesUtiles.validarDistintoDeNull(estado, "estado");
        ValidacionesUtiles.validarFalso(puntaje < 0, "El puntaje no puede ser negativo.");
        this.serie = serie;
        this.puntaje = puntaje;
        this.estado = estado;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /** post: devuelve true si la opinión está en estado PUBLICADA. */
    public boolean estaPublicada() {
        return estado == EstadoOpinion.PUBLICADA;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return serie + ": " + puntaje + " [" + estado + "]";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Serie getSerie() {
        return serie;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public EstadoOpinion getEstado() {
        return estado;
    }
}
