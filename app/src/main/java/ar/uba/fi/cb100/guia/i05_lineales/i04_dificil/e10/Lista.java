package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

/**
 * Tipo Abstracto de Datos (TAD) Lista: define el contrato de una secuencia
 * indexada, independientemente de como se implemente por dentro.
 *
 * Este ejercicio muestra el valor de la abstraccion: dos implementaciones muy
 * distintas (una sobre arreglo, otra sobre nodos enlazados) cumplen exactamente
 * el mismo comportamiento observable.
 */
public interface Lista<T> {

    /** Agrega x al final de la lista. */
    void agregar(T x);

    /**
     * Inserta x en la posicion i (0-based), corriendo el resto a la derecha.
     * Admite i == tamanio() (insertar al final).
     */
    void insertar(int i, T x);

    /** Elimina y devuelve el elemento en la posicion i. */
    T eliminar(int i);

    /** Devuelve el elemento en la posicion i. */
    T obtener(int i);

    /** Cantidad de elementos. */
    int tamanio();
}
