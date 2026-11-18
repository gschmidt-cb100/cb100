package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una melodía: un nombre y una lista de notas en el orden en que suenan.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarla.
 */
public class Melodia {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private List<Nota> notas;
    private String nombre;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code nombre} no es null ni está vacío.
     * post: crea la melodía sin notas. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Melodia(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre de la melodía no puede estar vacío.");
        this.nombre = nombre;
        this.notas = new ArrayList<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code nota} no es null.
     * post: la nota queda al final de la melodía.
     */
    public void agregarNota(Nota nota) {
        ValidacionesUtiles.validarDistintoDeNull(nota, "nota");
        notas.add(nota);
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve la lista de notas de la melodía (la misma, no una copia). */
    public List<Nota> getNotas() {
        return notas;
    }

    public String getNombre() {
        return nombre;
    }
}
