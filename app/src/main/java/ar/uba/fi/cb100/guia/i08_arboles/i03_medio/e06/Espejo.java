package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e06;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio: el espejo de un árbol binario. Se construye un árbol NUEVO
 * en el que, en todos los niveles, el subárbol izquierdo y el derecho
 * están intercambiados. El árbol original no se modifica.
 */
public final class Espejo {

    private Espejo() {
    }

    /**
     * Devuelve un árbol nuevo, espejo del recibido: en cada nodo,
     * el espejo del subárbol derecho queda a la izquierda y viceversa.
     *
     * @param raiz raíz del árbol original (puede ser {@code null})
     * @return la raíz del árbol espejado, o {@code null} si el original es vacío
     */
    public static Nodo espejo(Nodo raiz) {
        if (raiz == null) {
            return null;
        }
        // Nodo nuevo: el original queda intacto.
        return new Nodo(raiz.valor, espejo(raiz.derecho), espejo(raiz.izquierdo));
    }

    /**
     * Recorrido en orden, usado para observar el efecto del espejo:
     * el en-orden del espejo es el en-orden del original invertido.
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

    /** Demostración con el árbol de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("En orden del original: " + enOrden(raiz));
        System.out.println("En orden del espejo:   " + enOrden(espejo(raiz)));
    }
}
