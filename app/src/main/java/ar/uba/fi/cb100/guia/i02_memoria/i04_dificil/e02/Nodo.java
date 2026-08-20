package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e02;

/**
 * Nodo simple usado para armar listas que pueden tener ciclos.
 * Exponemos los campos para poder enlazar nodos a mano en las pruebas.
 */
public class Nodo {
    int valor;
    Nodo siguiente;

    public Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
