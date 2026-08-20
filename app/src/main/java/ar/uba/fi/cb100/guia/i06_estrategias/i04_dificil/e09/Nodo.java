package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e09;

/**
 * Nodo de una lista simplemente enlazada de enteros: guarda un dato y la
 * referencia al siguiente nodo. Autocontenido en el paquete del ejercicio.
 */
class Nodo {

    int dato;
    Nodo siguiente;

    Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
