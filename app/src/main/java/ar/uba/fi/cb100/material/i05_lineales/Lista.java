package ar.uba.fi.cb100.material.i05_lineales;

/**
 * TDA <b>Lista</b>: una secuencia de elementos con posición (índice 0..n−1).
 * Es el <b>contrato</b>; puede implementarse con un arreglo (vector dinámico) o
 * con nodos enlazados, y quien la usa no debería notar la diferencia.
 *
 * @param <T> tipo de los elementos
 */
public interface Lista<T> {

    /** Agrega {@code x} al final. */
    void agregar(T x);

    /** Inserta {@code x} en la posición {@code i} (0..tamaño), corriendo el resto. */
    void insertar(int i, T x);

    /** Devuelve el elemento en la posición {@code i}. */
    T obtener(int i);

    /** Elimina y devuelve el elemento en la posición {@code i}. */
    T eliminar(int i);

    /** Primera posición donde aparece {@code x} (por {@code equals}), o −1. */
    int indiceDe(T x);

    /** ¿Contiene {@code x}? */
    boolean contiene(T x);

    /** Agrega al final todos los elementos de {@code otra} (en orden). */
    void agregarTodos(Lista<T> otra);

    /** Cantidad de elementos. */
    int tamanio();

    /** ¿Está vacía? */
    boolean estaVacia();
}
