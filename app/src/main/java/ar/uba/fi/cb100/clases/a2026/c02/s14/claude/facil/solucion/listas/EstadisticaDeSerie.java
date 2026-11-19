package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Acumula las opiniones publicadas de UNA serie y guarda sólo lo necesario
 * para filtrar y ordenar: cuántas son y cuánto suman sus puntajes.
 *
 * El promedio NO es un atributo: se calcula cuando se pide, a partir de la
 * suma y la cantidad, así no puede quedar desactualizado.
 *
 * Invariante:
 *  - serie no es null y no cambia.
 *  - cantidadDeOpiniones es mayor o igual a uno.
 *  - sumaDePuntajes es mayor o igual a cero.
 */
public class EstadisticaDeSerie {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Serie serie;
    private int cantidadDeOpiniones;
    private double sumaDePuntajes;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code opinion} no es null y está publicada.
     * post: crea la estadística de la serie de esa opinión, con esa opinión
     *       como primera. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public EstadisticaDeSerie(Opinion opinion) {
        validarOpinion(opinion);
        this.serie = opinion.getSerie();
        this.cantidadDeOpiniones = 1;
        this.sumaDePuntajes = opinion.getPuntaje();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code opinion} no es null, está publicada y es de la misma serie.
     * post: la opinión queda contada en la estadística. Si no se cumple la
     *       pre, lanza {@link IllegalArgumentException}.
     */
    public void agregar(Opinion opinion) {
        validarOpinion(opinion);
        ValidacionesUtiles.validarFalso(!esDe(opinion.getSerie()), "La opinión no es de " + serie + ".");
        cantidadDeOpiniones++;
        sumaDePuntajes += opinion.getPuntaje();
    }

    /** post: devuelve el promedio de los puntajes de las opiniones acumuladas. */
    public double getPuntajePromedio() {
        return sumaDePuntajes / cantidadDeOpiniones;
    }

    /** post: devuelve true si esta estadística es de la serie recibida. */
    public boolean esDe(Serie serie) {
        return this.serie.equals(serie);
    }

    private static void validarOpinion(Opinion opinion) {
        ValidacionesUtiles.validarDistintoDeNull(opinion, "opinión");
        ValidacionesUtiles.validarFalso(!opinion.estaPublicada(), "Sólo se acumulan opiniones publicadas.");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /** Dos estadísticas son iguales si son de la misma serie. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaDeSerie otra = (EstadisticaDeSerie) o;
        return this.serie.equals(otra.serie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serie);
    }

    @Override
    public String toString() {
        return serie + ": " + cantidadDeOpiniones + " opiniones, promedio " + getPuntajePromedio();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Serie getSerie() {
        return serie;
    }

    public int getCantidadDeOpiniones() {
        return cantidadDeOpiniones;
    }
}
