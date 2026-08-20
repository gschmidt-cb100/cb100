package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e03;

/**
 * Nodo de una lista doblemente enlazada: guarda un dato y referencias al
 * nodo anterior y al siguiente.
 */
class Nodo<T> {

    T dato;
    Nodo<T> anterior;
    Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
