package ar.uba.fi.cb100.material.i08_arboles;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <b>Árbol Binario de Búsqueda (ABB / BST)</b>: cada nodo cumple el invariante
 * <i>todo lo menor a la izquierda, todo lo mayor a la derecha</i>. Eso permite
 * buscar, insertar y quitar descartando la mitad del árbol en cada paso:
 * <b>O(h)</b>, con h la altura — O(log n) si el árbol está balanceado,
 * O(n) si degeneró en lista (para eso están los AVL).
 * <p>Sin duplicados (insertar un valor existente no hace nada).</p>
 */
public class ArbolBinarioDeBusqueda<T extends Comparable<T>> {

    protected static class Nodo<T> {
        T valor;
        Nodo<T> izquierdo, derecho;
        Nodo(T valor) { this.valor = valor; }
    }

    protected Nodo<T> raiz;
    protected int cantidad;

    // ------------------------------------------------------------ insertar
    /** Inserta el valor en su lugar según el invariante. O(h). */
    public void insertar(T valor) {
        raiz = insertar(raiz, valor);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {                       // llegué a un hueco: va acá
            cantidad++;
            return new Nodo<>(valor);
        }
        int cmp = valor.compareTo(nodo.valor);
        if (cmp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else if (cmp > 0) {
            nodo.derecho   = insertar(nodo.derecho, valor);
        }
        // cmp == 0: ya estaba, no hacemos nada (sin duplicados)
        return nodo;
    }

    // ------------------------------------------------------------ buscar
    /** ¿Está el valor? Desciende comparando: O(h). */
    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int cmp = valor.compareTo(actual.valor);
            if (cmp == 0) {
                return true;
            }
            actual = (cmp < 0) ? actual.izquierdo : actual.derecho;
        }
        return false;
    }

    /** El menor valor: siempre a la izquierda del todo. O(h). */
    public T minimo() {
        if (raiz == null) {
            throw new IllegalStateException("árbol vacío");
        }
        Nodo<T> actual = raiz;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.valor;
    }

    /** El mayor valor: siempre a la derecha del todo. O(h). */
    public T maximo() {
        if (raiz == null) {
            throw new IllegalStateException("árbol vacío");
        }
        Nodo<T> actual = raiz;
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.valor;
    }

    // ------------------------------------------------------------ quitar
    /** Quita el valor (si está), re-enganchando según el caso. O(h). */
    public void quitar(T valor) {
        raiz = quitar(raiz, valor);
    }

    private Nodo<T> quitar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            return null;   // no estaba
        }
        int cmp = valor.compareTo(nodo.valor);
        if (cmp < 0)      { nodo.izquierdo = quitar(nodo.izquierdo, valor); return nodo; }
        if (cmp > 0)      { nodo.derecho   = quitar(nodo.derecho, valor);   return nodo; }

        // Encontrado: tres casos.
        cantidad--;
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return null;                          // 1) hoja: se elimina y listo
        }
        if (nodo.izquierdo == null) {
            return nodo.derecho;   // 2) un solo hijo:
        }
        if (nodo.derecho == null) {
            return nodo.izquierdo;   //    el hijo lo reemplaza
        }

        // 3) dos hijos: lo reemplaza su SUCESOR (el mínimo del subárbol derecho),
        //    que por ser mínimo tiene a lo sumo un hijo y es fácil de quitar.
        Nodo<T> sucesor = nodo.derecho;
        while (sucesor.izquierdo != null) {
            sucesor = sucesor.izquierdo;
        }
        nodo.valor = sucesor.valor;
        cantidad++;                               // compensa el -- del quitar interno
        nodo.derecho = quitar(nodo.derecho, sucesor.valor);
        return nodo;
    }

    // ------------------------------------------------------------ recorridos
    /** IN-ORDEN (izq, nodo, der): visita los valores ORDENADOS. O(n). */
    public List<T> enOrden() {
        List<T> resultado = new ArrayList<>();
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izquierdo, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.derecho, resultado);
    }

    /** PRE-ORDEN (nodo, izq, der): útil para copiar/serializar el árbol. */
    public List<T> preOrden() {
        List<T> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        resultado.add(nodo.valor);
        preOrden(nodo.izquierdo, resultado);
        preOrden(nodo.derecho, resultado);
    }

    /** POST-ORDEN (izq, der, nodo): útil para liberar/evaluar de abajo arriba. */
    public List<T> postOrden() {
        List<T> resultado = new ArrayList<>();
        postOrden(raiz, resultado);
        return resultado;
    }

    private void postOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        postOrden(nodo.izquierdo, resultado);
        postOrden(nodo.derecho, resultado);
        resultado.add(nodo.valor);
    }

    /** POR NIVELES (BFS): usa una COLA (Unidad 5) en vez de recursión. */
    public List<T> porNiveles() {
        List<T> resultado = new ArrayList<>();
        if (raiz == null) {
            return resultado;
        }
        Deque<Nodo<T>> cola = new ArrayDeque<>();
        cola.addLast(raiz);
        while (!cola.isEmpty()) {
            Nodo<T> nodo = cola.pollFirst();
            resultado.add(nodo.valor);
            if (nodo.izquierdo != null) {
                cola.addLast(nodo.izquierdo);
            }
            if (nodo.derecho != null) {
                cola.addLast(nodo.derecho);
            }
        }
        return resultado;
    }

    // ------------------------------------------------------------ métricas
    public int tamanio() { return cantidad; }

    /** Altura: la rama más larga desde la raíz (árbol vacío: -1). */
    public int altura() { return altura(raiz); }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    public static void main(String[] args) {
        ArbolBinarioDeBusqueda<Integer> abb = new ArbolBinarioDeBusqueda<>();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            abb.insertar(v);
        }
        System.out.println(abb.enOrden());      // [20, 30, 40, 50, 60, 70, 80] ¡ordenado!
        System.out.println(abb.preOrden());     // [50, 30, 20, 40, 70, 60, 80]
        System.out.println(abb.postOrden());    // [20, 40, 30, 60, 80, 70, 50]
        System.out.println(abb.porNiveles());   // [50, 30, 70, 20, 40, 60, 80]
        System.out.println(abb.contiene(40));   // true
        System.out.println(abb.minimo() + " " + abb.maximo());   // 20 80
        System.out.println(abb.altura());       // 2

        abb.quitar(20);                          // caso hoja
        abb.quitar(70);                          // caso dos hijos (sucesor: 80)
        System.out.println(abb.enOrden());      // [30, 40, 50, 60, 80]
        System.out.println(abb.tamanio());      // 5
    }
}
