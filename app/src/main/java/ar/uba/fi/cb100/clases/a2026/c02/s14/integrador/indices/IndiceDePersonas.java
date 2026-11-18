package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Contrato de un índice sobre un campo: dado el comienzo de una clave,
 * devuelve las personas cuya clave empieza así. Las tres implementaciones
 * (vector, lista, árbol) cumplen el mismo contrato con costos distintos, que
 * es justamente lo que el sistema mide y muestra.
 */
public interface IndiceDePersonas {

    /**
     * pre: {@code prefijo} no es null.
     * post: devuelve las personas cuya clave empieza con el prefijo (sin
     *       distinguir mayúsculas), ordenadas por clave y, a igual clave, por
     *       DNI. Un prefijo vacío devuelve todas.
     */
    List<Persona> buscarPorPrefijo(String prefijo);

    /** post: devuelve el campo sobre el que está armado el índice. */
    CampoDeBusqueda getCampo();

    /** post: devuelve la estructura que usa por dentro. */
    TipoDeIndice getTipo();

    /** post: devuelve cuántas personas tiene indexadas. */
    int getCantidad();
}
