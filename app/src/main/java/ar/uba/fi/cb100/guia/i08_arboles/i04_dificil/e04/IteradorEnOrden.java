package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e04;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterador en orden sobre el ABB, SIN recursion: usa una pila explicita.
 *
 * La idea: la pila guarda el camino pendiente. En todo momento, el tope
 * de la pila es el proximo valor a devolver. Para lograrlo:
 *  - Al arrancar, apilamos toda la rama izquierda desde la raiz:
 *    el tope queda en el minimo del arbol.
 *  - En next(), desapilamos el tope y, si tiene hijo derecho, apilamos
 *    la rama izquierda de ese hijo (su sucesor esta ahi abajo).
 *
 * Cada nodo se apila y desapila UNA vez: recorrer todo cuesta O(n) en
 * total, y la pila nunca supera la altura del arbol, O(h) de memoria.
 */
public class IteradorEnOrden<T extends Comparable<T>> implements Iterator<T> {

    /** Pila de nodos pendientes. El tope es el proximo a visitar. */
    private final Deque<AbbIterable.Nodo<T>> pila = new ArrayDeque<>();

    /** Arranca apilando la rama izquierda: el tope queda en el minimo. */
    IteradorEnOrden(AbbIterable.Nodo<T> raiz) {
        apilarRamaIzquierda(raiz);
    }

    /** Apila el nodo y todos sus descendientes por la izquierda. */
    private void apilarRamaIzquierda(AbbIterable.Nodo<T> nodo) {
        while (nodo != null) {
            pila.push(nodo);
            nodo = nodo.izq;
        }
    }

    /** Hay proximo mientras quede algo pendiente en la pila. O(1). */
    @Override
    public boolean hasNext() {
        return !pila.isEmpty();
    }

    /** Devuelve el proximo valor en orden y avanza. */
    @Override
    public T next() {
        if (pila.isEmpty()) {
            throw new NoSuchElementException("El recorrido ya termino");
        }
        AbbIterable.Nodo<T> nodo = pila.pop();
        // El sucesor del nodo esta en su subarbol derecho, bien a la izquierda.
        apilarRamaIzquierda(nodo.der);
        return nodo.valor;
    }
}
