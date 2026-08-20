package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e02;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio: los tres recorridos en profundidad de un árbol binario.
 * Los tres tienen la misma estructura recursiva; lo único que cambia
 * es en qué momento se "visita" el nodo actual (acá, agregarlo a la lista).
 */
public final class Recorridos {

    private Recorridos() {
    }

    /**
     * Recorrido en orden (izquierdo, nodo, derecho). En un ABB devuelve
     * los valores ordenados de menor a mayor.
     */
    public static List<Integer> enOrden(Nodo raiz) {
        List<Integer> resultado = new ArrayList<>();
        enOrden(raiz, resultado);
        return resultado;
    }

    private static void enOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izquierdo, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.derecho, resultado);
    }

    /** Recorrido en preorden (nodo, izquierdo, derecho). */
    public static List<Integer> preOrden(Nodo raiz) {
        List<Integer> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private static void preOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        resultado.add(nodo.valor);
        preOrden(nodo.izquierdo, resultado);
        preOrden(nodo.derecho, resultado);
    }

    /** Recorrido en postorden (izquierdo, derecho, nodo). */
    public static List<Integer> postOrden(Nodo raiz) {
        List<Integer> resultado = new ArrayList<>();
        postOrden(raiz, resultado);
        return resultado;
    }

    private static void postOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        postOrden(nodo.izquierdo, resultado);
        postOrden(nodo.derecho, resultado);
        resultado.add(nodo.valor);
    }

    /** Demostración con el árbol de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("En orden:   " + enOrden(raiz));
        System.out.println("Preorden:   " + preOrden(raiz));
        System.out.println("Postorden:  " + postOrden(raiz));
    }
}
