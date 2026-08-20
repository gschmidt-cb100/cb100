package ar.uba.fi.cb100.material.i05_lineales;

/**
 * TDA <b>Conjunto</b> (Set): una colección <b>sin duplicados</b> y sin orden por
 * posición. Su operación distintiva es que {@code agregar} ignora los elementos
 * que ya están (comparando por {@code equals}). Ofrece además las operaciones
 * clásicas de teoría de conjuntos: <b>unión</b>, <b>intersección</b> y
 * <b>diferencia</b>.
 */
public interface Conjunto<T> {

    /** Agrega {@code x} si no estaba. Devuelve {@code true} si lo agregó. */
    boolean agregar(T x);

    /** ¿Pertenece {@code x} al conjunto? */
    boolean contiene(T x);

    /** Quita {@code x}. Devuelve {@code true} si estaba. */
    boolean eliminar(T x);

    /** Agrega (in place) todos los elementos de {@code otro} que no estén. */
    void agregarTodos(Conjunto<T> otro);

    /** Unión: un conjunto NUEVO con los elementos de ambos. */
    Conjunto<T> union(Conjunto<T> otro);

    /** Intersección: un conjunto NUEVO con los elementos que están en los dos. */
    Conjunto<T> interseccion(Conjunto<T> otro);

    /** Diferencia (this − otro): un conjunto NUEVO con los que están acá pero no en otro. */
    Conjunto<T> diferencia(Conjunto<T> otro);

    /** Cantidad de elementos. */
    int tamanio();

    /** Los elementos, para poder recorrerlos. */
    Object[] aArreglo();
}
