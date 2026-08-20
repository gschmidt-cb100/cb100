package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e02;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Cola (FIFO) genérica implementada sobre un {@link LinkedList}.
 * Se encola al final y se desencola por el frente.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Cola<T> {

    // LinkedList implementa Queue: add al final, poll por el frente.
    private final Queue<T> datos = new LinkedList<>();

    /** Agrega un elemento al final de la cola. */
    public void encolar(T elemento) {
        datos.add(elemento);
    }

    /**
     * Quita y devuelve el elemento del frente.
     *
     * @throws NoSuchElementException si la cola está vacía
     */
    public T desencolar() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede desencolar de una cola vacía");
        }
        return datos.poll();
    }

    /**
     * Devuelve (sin quitar) el elemento del frente.
     *
     * @throws NoSuchElementException si la cola está vacía
     */
    public T frente() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede consultar el frente de una cola vacía");
        }
        return datos.peek();
    }

    /** Indica si la cola no tiene elementos. */
    public boolean estaVacia() {
        return datos.isEmpty();
    }

    /** Cantidad de elementos en la cola. */
    public int tamanio() {
        return datos.size();
    }
}
