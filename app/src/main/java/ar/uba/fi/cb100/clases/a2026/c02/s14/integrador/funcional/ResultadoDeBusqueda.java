package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Lo que devuelve una búsqueda: con qué se hizo, las coincidencias y cuánto
 * tardó. Es lo que la pantalla muestra debajo de la grilla.
 */
public record ResultadoDeBusqueda(String metodo, List<Persona> personas, long nanosegundos) {

    public ResultadoDeBusqueda {
        personas = List.copyOf(personas);      // copia defensiva: el resultado no cambia después
    }

    /** post: devuelve el tiempo en milisegundos, con decimales. */
    public double getMilisegundos() {
        return nanosegundos / 1_000_000.0;
    }

    /** post: devuelve la cantidad de coincidencias. */
    public int getCantidad() {
        return personas.size();
    }
}
