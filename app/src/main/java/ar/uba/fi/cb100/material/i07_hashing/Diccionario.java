package ar.uba.fi.cb100.material.i07_hashing;

/**
 * El TDA <b>Diccionario</b> (también llamado <i>mapa</i> o <i>tabla asociativa</i>):
 * asocia cada <b>clave</b> única con un <b>valor</b>. Su contrato no dice nada de
 * cómo se implementa; en esta unidad lo implementamos con <b>tablas de hash</b>
 * (encadenamiento y direccionamiento abierto), que logran O(1) <b>promedio</b> en
 * todas sus operaciones.
 *
 * @param <K> tipo de las claves (debe tener {@code equals} y {@code hashCode} consistentes)
 * @param <V> tipo de los valores
 */
public interface Diccionario<K, V> {

    /** Asocia la clave con el valor; si la clave ya existía, reemplaza su valor. */
    void poner(K clave, V valor);

    /** Devuelve el valor asociado a la clave, o {@code null} si no está. */
    V obtener(K clave);

    /** ¿Existe la clave en el diccionario? */
    boolean contiene(K clave);

    /** Quita la clave (y su valor). Devuelve el valor que tenía, o {@code null}. */
    V quitar(K clave);

    /** Cantidad de pares clave→valor guardados. */
    int tamanio();

    default boolean estaVacio() { return tamanio() == 0; }
}
