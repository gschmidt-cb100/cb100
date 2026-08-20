package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e01;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Pila (LIFO) genérica implementada sobre un {@link ArrayList}.
 * El tope de la pila es el último elemento del arreglo dinámico,
 * de modo que apilar y desapilar son operaciones al final de la lista.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Pila<T> {

    // Estructura interna: el final de la lista representa el tope.
    private final List<T> datos = new ArrayList<>();

    /** Agrega un elemento en el tope de la pila. */
    public void apilar(T elemento) {
        datos.add(elemento);
    }

    /**
     * Quita y devuelve el elemento del tope.
     *
     * @throws NoSuchElementException si la pila está vacía
     */
    public T desapilar() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede desapilar de una pila vacía");
        }
        return datos.remove(datos.size() - 1);
    }

    /**
     * Devuelve (sin quitar) el elemento del tope.
     *
     * @throws NoSuchElementException si la pila está vacía
     */
    public T tope() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede consultar el tope de una pila vacía");
        }
        return datos.get(datos.size() - 1);
    }

    /** Indica si la pila no tiene elementos. */
    public boolean estaVacia() {
        return datos.isEmpty();
    }

    /** Cantidad de elementos en la pila. */
    public int tamanio() {
        return datos.size();
    }
}
