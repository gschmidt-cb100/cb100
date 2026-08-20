package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e03;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Ejercicio: recorrido por niveles (BFS) de un árbol binario.
 * A diferencia de los recorridos en profundidad, acá no usamos recursión:
 * una cola guarda los nodos pendientes y garantiza que se visiten
 * nivel por nivel, de izquierda a derecha.
 */
public final class PorNiveles {

    private PorNiveles() {
    }

    /**
     * Devuelve los valores del árbol nivel por nivel, de izquierda a derecha.
     *
     * @param raiz raíz del árbol (puede ser {@code null})
     * @return lista con los valores en orden de niveles (vacía si el árbol es vacío)
     */
    public static List<Integer> porNiveles(Nodo raiz) {
        List<Integer> resultado = new ArrayList<>();
        if (raiz == null) {
            return resultado;
        }
        Queue<Nodo> pendientes = new ArrayDeque<>();
        pendientes.add(raiz);
        while (!pendientes.isEmpty()) {
            Nodo actual = pendientes.remove();
            resultado.add(actual.valor);
            // Encolamos los hijos: quedan detrás de todos los nodos
            // del nivel actual, por eso salen en el nivel siguiente.
            if (actual.izquierdo != null) {
                pendientes.add(actual.izquierdo);
            }
            if (actual.derecho != null) {
                pendientes.add(actual.derecho);
            }
        }
        return resultado;
    }

    /** Demostración con el árbol de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("Por niveles: " + porNiveles(raiz));
    }
}
