package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import java.util.List;
import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una serie del servicio de streaming: título y género.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarla.
 *
 * Hipótesis:
 *  H1. Una serie se identifica por su título y su género (puede haber dos
 *      series con el mismo título en géneros distintos).
 */
public class Serie {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String titulo;
    private final String genero;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code titulo} y {@code genero} no son null ni están vacíos.
     * post: crea la serie. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Serie(String titulo, String genero) {
        ValidacionesUtiles.validarDistintoDeNull(titulo, "título");
        ValidacionesUtiles.validarDistintoDeNull(genero, "género");
        ValidacionesUtiles.validarFalso(titulo.isBlank(), "El título de la serie no puede estar vacío.");
        ValidacionesUtiles.validarFalso(genero.isBlank(), "El género de la serie no puede estar vacío.");
        this.titulo = titulo;
        this.genero = genero;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code generos} no es null.
     * post: devuelve true si el género de la serie está entre los recibidos.
     */
    public boolean esDeAlgunoDe(List<String> generos) {
        ValidacionesUtiles.validarDistintoDeNull(generos, "géneros");
        return generos.contains(genero);
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /** Dos series son iguales si tienen el mismo título y el mismo género (H1). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie otra = (Serie) o;
        return this.titulo.equals(otra.titulo) && this.genero.equals(otra.genero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, genero);
    }

    @Override
    public String toString() {
        return titulo + " (" + genero + ")";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }
}
