package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e02;

/**
 * Nodo de una lista simplemente enlazada: guarda un dato y una referencia
 * al siguiente nodo.
 */
class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
