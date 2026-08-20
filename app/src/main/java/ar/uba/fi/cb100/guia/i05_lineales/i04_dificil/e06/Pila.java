package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e06;

import java.util.NoSuchElementException;

/**
 * Pila (LIFO) implementada A MANO sobre nodos enlazados. El tope es la cabeza
 * de la lista, por lo que todas las operaciones son O(1).
 *
 * Complejidad:
 *  - apilar / desapilar / tope / estaVacia / tamanio: O(1)
 */
public class Pila<T> {

    private Nodo<T> tope;
    private int tamanio;

    public Pila() {
        this.tope = null;
        this.tamanio = 0;
    }

    /** true si la pila no tiene elementos. O(1). */
    public boolean estaVacia() {
        return tope == null;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Apila un elemento en el tope. O(1). */
    public void apilar(T x) {
        Nodo<T> nuevo = new Nodo<>(x);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamanio++;
    }

    /** Desapila y devuelve el elemento del tope. O(1). */
    public T desapilar() {
        if (estaVacia()) {
            throw new NoSuchElementException("La pila esta vacia");
        }
        Nodo<T> desapilado = tope;
        tope = tope.siguiente;
        desapilado.siguiente = null;
        tamanio--;
        return desapilado.dato;
    }

    /** Devuelve (sin quitar) el elemento del tope. O(1). */
    public T tope() {
        if (estaVacia()) {
            throw new NoSuchElementException("La pila esta vacia");
        }
        return tope.dato;
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        Pila<String> p = new Pila<>();
        p.apilar("a");
        p.apilar("b");
        p.apilar("c");
        System.out.println("tope: " + p.tope());        // c
        System.out.println("desapilar: " + p.desapilar()); // c
        System.out.println("desapilar: " + p.desapilar()); // b
        System.out.println("tamanio: " + p.tamanio());    // 1
    }
}
