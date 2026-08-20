package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e01;

/**
 * Nodo de una lista enlazada simple de enteros.
 * Guarda un valor y una referencia al siguiente nodo.
 */
public class Nodo {
    // Campos package-private para que la lista los manipule directamente.
    int valor;
    Nodo siguiente;

    public Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
