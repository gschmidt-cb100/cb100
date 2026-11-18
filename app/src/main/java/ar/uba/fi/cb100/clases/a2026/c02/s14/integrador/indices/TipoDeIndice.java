package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Las tres estructuras con las que se arma un índice, y la fábrica que crea
 * cada una. Quien usa los índices nunca nombra las clases concretas: pide un
 * tipo y recibe un {@link IndiceDePersonas}.
 */
public enum TipoDeIndice {
    VECTOR("Vector", "ArrayList ordenado + búsqueda binaria"),
    LISTA("Lista", "LinkedList ordenada + recorrido secuencial"),
    ARBOL("Árbol", "TreeMap (árbol binario rojo-negro) + consulta por rango");

    private final String titulo;
    private final String descripcion;

    TipoDeIndice(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    /**
     * pre: {@code campo} y {@code personas} no son null.
     * post: devuelve un índice nuevo de este tipo sobre ese campo.
     */
    public IndiceDePersonas crear(CampoDeBusqueda campo, List<Persona> personas) {
        return switch (this) {
            case VECTOR -> new IndicePorVector(campo, personas);
            case LISTA -> new IndicePorLista(campo, personas);
            case ARBOL -> new IndicePorArbol(campo, personas);
        };
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
