package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e05;

/**
 * Nodo simplemente enlazado usado por el conjunto.
 */
class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
