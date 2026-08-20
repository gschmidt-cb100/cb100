package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e08;

import java.util.ArrayList;
import java.util.List;

/**
 * ABB de enteros (replica del ejercicio 1, especializado en Integer),
 * con el recorrido en PRE-orden ademas del en orden.
 *
 * El pre-orden (nodo, izquierda, derecha) es el recorrido clave para
 * serializar: visita cada nodo ANTES que a sus hijos, o sea, en el
 * mismo orden en que habria que insertarlos para reconstruir el arbol
 * con la misma forma.
 */
public class ArbolBusqueda {

    /** Nodo del arbol. */
    private static class Nodo {
        final int valor;
        Nodo izq;
        Nodo der;

        Nodo(int valor) {
            this.valor = valor;
        }
    }

    private Nodo raiz;
    private int tamanio;

    /** Cantidad de valores almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Inserta el valor sin duplicados. O(h). */
    public void insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    private Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            tamanio++;
            return new Nodo(valor);
        }
        if (valor < nodo.valor) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (valor > nodo.valor) {
            nodo.der = insertar(nodo.der, valor);
        }
        return nodo;
    }

    /** Devuelve los valores ordenados de menor a mayor. O(n). */
    public List<Integer> enOrden() {
        List<Integer> resultado = new ArrayList<>(tamanio);
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izq, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.der, resultado);
    }

    /** Devuelve los valores en pre-orden: nodo, izquierda, derecha. O(n). */
    public List<Integer> preOrden() {
        List<Integer> resultado = new ArrayList<>(tamanio);
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        resultado.add(nodo.valor); // Primero el nodo: por eso sirve para serializar.
        preOrden(nodo.izq, resultado);
        preOrden(nodo.der, resultado);
    }
}
