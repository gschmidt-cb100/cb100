package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e07;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio: el camino desde la raíz de un ABB hasta un valor.
 * Gracias al invariante del ABB no hace falta explorar todo el árbol:
 * en cada nodo se sabe hacia qué lado seguir, así que el camino es
 * único y se encuentra en O(altura).
 */
public final class Camino {

    private Camino() {
    }

    /**
     * Devuelve los valores de la rama que va desde la raíz hasta el
     * valor buscado (ambos incluidos), siguiendo el invariante del ABB.
     *
     * @param raiz  raíz de un ABB (puede ser {@code null})
     * @param valor valor buscado
     * @return el camino raíz → valor, o una lista vacía si el valor no está
     */
    public static List<Integer> caminoHasta(Nodo raiz, int valor) {
        List<Integer> camino = new ArrayList<>();
        if (!buscar(raiz, valor, camino)) {
            // Si la rama recorrida no terminó en el valor, no hay camino.
            camino.clear();
        }
        return camino;
    }

    /**
     * Agrega al camino los nodos que visita y avisa si encontró el valor.
     */
    private static boolean buscar(Nodo nodo, int valor, List<Integer> camino) {
        if (nodo == null) {
            return false;
        }
        camino.add(nodo.valor);
        if (valor == nodo.valor) {
            return true;
        }
        return valor < nodo.valor
                ? buscar(nodo.izquierdo, valor, camino)
                : buscar(nodo.derecho, valor, camino);
    }

    /** Demostración con el ABB de ejemplo de la clase. */
    public static void main(String[] args) {
        Nodo raiz = new Nodo(50,
                new Nodo(30, new Nodo(20), new Nodo(40)),
                new Nodo(70, new Nodo(60), new Nodo(80)));

        System.out.println("Camino hasta 40: " + caminoHasta(raiz, 40));
        System.out.println("Camino hasta 50: " + caminoHasta(raiz, 50));
        System.out.println("Camino hasta 65 (no está): " + caminoHasta(raiz, 65));
    }
}
