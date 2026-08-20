package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e06;

/**
 * Nodo enlazado usado por la pila.
 */
class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
