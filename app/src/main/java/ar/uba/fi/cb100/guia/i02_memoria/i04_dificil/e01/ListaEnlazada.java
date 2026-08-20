package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e01;

import java.util.Arrays;

/**
 * Lista enlazada simple de enteros.
 * Mantiene referencia al primero y al ultimo para agregar en O(1).
 */
public class ListaEnlazada {
    private Nodo primero;
    private Nodo ultimo;
    private int tamanio;

    /** Agrega un valor al final de la lista. */
    public void agregar(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (primero == null) {
            // La lista estaba vacia: el nuevo nodo es primero y ultimo.
            primero = nuevo;
            ultimo = nuevo;
        } else {
            // Enganchamos el nuevo nodo despues del ultimo.
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        tamanio++;
    }

    /** Cantidad de elementos almacenados. */
    public int tamanio() {
        return tamanio;
    }

    /** Copia los valores de la lista a un arreglo nuevo, en orden. */
    public int[] aArreglo() {
        int[] resultado = new int[tamanio];
        Nodo actual = primero;
        int i = 0;
        while (actual != null) {
            resultado[i] = actual.valor;
            i++;
            actual = actual.siguiente;
        }
        return resultado;
    }

    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);
        System.out.println("Tamanio: " + lista.tamanio());
        System.out.println("Contenido: " + Arrays.toString(lista.aArreglo()));
    }
}
