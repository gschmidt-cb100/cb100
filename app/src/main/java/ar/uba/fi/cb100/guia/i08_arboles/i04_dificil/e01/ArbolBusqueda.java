package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e01;

import java.util.ArrayList;
import java.util.List;

/**
 * Arbol binario de busqueda (ABB) implementado A MANO, sin duplicados.
 *
 * Invariante del ABB: para cada nodo, todo lo que esta en su subarbol
 * izquierdo es MENOR que su valor, y todo lo que esta en su subarbol
 * derecho es MAYOR. Gracias a eso, el recorrido en orden (izquierda,
 * nodo, derecha) devuelve los valores ordenados de menor a mayor.
 *
 * Complejidad (con n elementos y h la altura del arbol):
 *  - insertar / contiene: O(h). Si el arbol esta balanceado h es O(log n),
 *    pero si insertamos en orden creciente degenera en una lista y h es O(n).
 *  - enOrden: O(n).
 *  - tamanio: O(1) porque lo llevamos contado.
 */
public class ArbolBusqueda<T extends Comparable<T>> {

    /** Nodo del arbol: un valor y sus dos hijos (pueden ser null). */
    private static class Nodo<T> {
        final T valor;
        Nodo<T> izq;
        Nodo<T> der;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    /** Raiz del arbol. Si es null, el arbol esta vacio. */
    private Nodo<T> raiz;

    /** Cantidad de valores almacenados. */
    private int tamanio;

    /** Cantidad de valores almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /**
     * Inserta el valor respetando el invariante del ABB.
     * Si el valor ya estaba, NO lo agrega de nuevo (sin duplicados).
     */
    public void insertar(T valor) {
        raiz = insertar(raiz, valor);
    }

    /**
     * Version recursiva: devuelve la raiz del subarbol ya con el valor
     * insertado. Devolver el nodo permite "re-colgar" el subarbol en el padre.
     */
    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            // Llegamos a un lugar libre: aca va el valor nuevo.
            tamanio++;
            return new Nodo<>(valor);
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (comparacion > 0) {
            nodo.der = insertar(nodo.der, valor);
        }
        // comparacion == 0: el valor ya estaba, no hacemos nada.
        return nodo;
    }

    /** Indica si el valor esta en el arbol. Busqueda iterativa, O(h). */
    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = valor.compareTo(actual.valor);
            if (comparacion == 0) {
                return true;
            }
            // El invariante nos dice para que lado seguir: ese es el truco del ABB.
            actual = (comparacion < 0) ? actual.izq : actual.der;
        }
        return false;
    }

    /** Devuelve los valores ordenados de menor a mayor (recorrido en orden). */
    public List<T> enOrden() {
        List<T> resultado = new ArrayList<>(tamanio);
        enOrden(raiz, resultado);
        return resultado;
    }

    /** En orden: primero la izquierda, despues el nodo, despues la derecha. */
    private void enOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izq, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.der, resultado);
    }

    /** Demostracion: insertamos desordenado y enOrden devuelve ordenado. */
    public static void main(String[] args) {
        ArbolBusqueda<Integer> arbol = new ArbolBusqueda<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80, 50}) {
            arbol.insertar(valor); // El 50 repetido no se agrega.
        }
        System.out.println("tamanio = " + arbol.tamanio());
        System.out.println("enOrden = " + arbol.enOrden());
        System.out.println("contiene(60) = " + arbol.contiene(60));
        System.out.println("contiene(65) = " + arbol.contiene(65));
    }
}
