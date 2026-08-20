package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e07;

import java.util.NoSuchElementException;

/**
 * Cola (FIFO) implementada A MANO sobre nodos enlazados, con punteros al
 * frente (donde se desencola) y al fondo (donde se encola). Mantener ambos
 * punteros permite encolar y desencolar en O(1).
 *
 * Complejidad:
 *  - encolar / desencolar / frente / estaVacia / tamanio: O(1)
 */
public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fondo;
    private int tamanio;

    public Cola() {
        this.frente = null;
        this.fondo = null;
        this.tamanio = 0;
    }

    /** true si la cola no tiene elementos. O(1). */
    public boolean estaVacia() {
        return frente == null;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Encola un elemento en el fondo. O(1). */
    public void encolar(T x) {
        Nodo<T> nuevo = new Nodo<>(x);
        if (estaVacia()) {
            frente = nuevo;
            fondo = nuevo;
        } else {
            fondo.siguiente = nuevo;
            fondo = nuevo;
        }
        tamanio++;
    }

    /** Desencola y devuelve el elemento del frente. O(1). */
    public T desencolar() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola esta vacia");
        }
        Nodo<T> desencolado = frente;
        frente = frente.siguiente;
        if (frente == null) {
            fondo = null; // la cola quedo vacia
        }
        desencolado.siguiente = null;
        tamanio--;
        return desencolado.dato;
    }

    /** Devuelve (sin quitar) el elemento del frente. O(1). */
    public T frente() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola esta vacia");
        }
        return frente.dato;
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        Cola<String> c = new Cola<>();
        c.encolar("a");
        c.encolar("b");
        c.encolar("c");
        System.out.println("frente: " + c.frente());        // a
        System.out.println("desencolar: " + c.desencolar()); // a
        System.out.println("desencolar: " + c.desencolar()); // b
        System.out.println("tamanio: " + c.tamanio());       // 1
    }
}
