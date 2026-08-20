package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

/**
 * Nodo simplemente enlazado usado por {@link ListaEnlazada}.
 */
class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
