package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e06;

import java.util.ArrayList;
import java.util.List;

/**
 * Arbol AVL: un ABB que se mantiene balanceado solo.
 *
 * Invariante AVL: en cada nodo, las alturas de sus dos subarboles
 * difieren a lo sumo en 1. Llamamos factor de equilibrio (FE) a
 * altura(izq) - altura(der): tiene que quedar siempre en {-1, 0, 1}.
 *
 * Al insertar, un nodo puede quedar con FE 2 o -2. Segun de que lado
 * vino el desbalance hay CUATRO casos:
 *  - LL (izquierda-izquierda): una rotacion a la derecha.
 *  - RR (derecha-derecha): una rotacion a la izquierda.
 *  - LR (izquierda-derecha): rotar el hijo a la izquierda y despues
 *    el nodo a la derecha (rotacion doble).
 *  - RL (derecha-izquierda): el espejo de LR.
 *
 * Con esto la altura queda en O(log n) SIEMPRE, y por lo tanto
 * insertar y contiene son O(log n) garantizado, sin casos degenerados.
 */
public class ArbolAvl<T extends Comparable<T>> {

    /** Nodo con la altura cacheada para calcular el FE en O(1). */
    private static class Nodo<T> {
        T valor;
        Nodo<T> izq;
        Nodo<T> der;
        int altura; // Una hoja mide 0.

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> raiz;
    private int tamanio;

    /** Cantidad de valores almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Altura del arbol: -1 si esta vacio, 0 si es una hoja sola. O(1). */
    public int altura() {
        return altura(raiz);
    }

    /** Valor de la raiz, o null si el arbol esta vacio. */
    public T raiz() {
        return (raiz == null) ? null : raiz.valor;
    }

    /** Altura de un subarbol, tolerando null. */
    private static int altura(Nodo<?> nodo) {
        return (nodo == null) ? -1 : nodo.altura;
    }

    /** Factor de equilibrio: positivo = cargado a la izquierda. */
    private static int factorEquilibrio(Nodo<?> nodo) {
        return altura(nodo.izq) - altura(nodo.der);
    }

    private static <T> void actualizarAltura(Nodo<T> nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
    }

    /** Inserta el valor sin duplicados, re-balanceando al volver. O(log n). */
    public void insertar(T valor) {
        raiz = insertar(raiz, valor);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            tamanio++;
            return new Nodo<>(valor);
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (comparacion > 0) {
            nodo.der = insertar(nodo.der, valor);
        } else {
            return nodo; // Duplicado: no se agrega y no hay nada que balancear.
        }
        // Al VOLVER de la recursion, cada nodo del camino se revisa y
        // se rota si hace falta. Ese es el corazon del AVL.
        return balancear(nodo);
    }

    /** Revisa el FE del nodo y aplica el caso de rotacion que corresponda. */
    private Nodo<T> balancear(Nodo<T> nodo) {
        actualizarAltura(nodo);
        int fe = factorEquilibrio(nodo);
        if (fe > 1) {
            // Desbalance a la izquierda.
            if (factorEquilibrio(nodo.izq) < 0) {
                // Caso LR: el peso esta en izq.der, hay que enderezarlo primero.
                nodo.izq = rotarIzquierda(nodo.izq);
            }
            // Caso LL (o LR ya enderezado): rotacion simple a la derecha.
            return rotarDerecha(nodo);
        }
        if (fe < -1) {
            // Desbalance a la derecha.
            if (factorEquilibrio(nodo.der) > 0) {
                // Caso RL: espejo del LR.
                nodo.der = rotarDerecha(nodo.der);
            }
            // Caso RR (o RL ya enderezado): rotacion simple a la izquierda.
            return rotarIzquierda(nodo);
        }
        return nodo; // FE en {-1, 0, 1}: no hay nada que hacer.
    }

    /** Rotacion a la derecha: sube el hijo izquierdo. O(1). */
    private Nodo<T> rotarDerecha(Nodo<T> p) {
        Nodo<T> q = p.izq;
        p.izq = q.der;
        q.der = p;
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    /** Rotacion a la izquierda: sube el hijo derecho. O(1). */
    private Nodo<T> rotarIzquierda(Nodo<T> p) {
        Nodo<T> q = p.der;
        p.der = q.izq;
        q.izq = p;
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    /** Indica si el valor esta en el arbol. O(log n) garantizado. */
    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = valor.compareTo(actual.valor);
            if (comparacion == 0) {
                return true;
            }
            actual = (comparacion < 0) ? actual.izq : actual.der;
        }
        return false;
    }

    /** Devuelve los valores ordenados de menor a mayor. O(n). */
    public List<T> enOrden() {
        List<T> resultado = new ArrayList<>(tamanio);
        enOrden(raiz, resultado);
        return resultado;
    }

    private void enOrden(Nodo<T> nodo, List<T> resultado) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izq, resultado);
        resultado.add(nodo.valor);
        enOrden(nodo.der, resultado);
    }

    /**
     * Demostracion: insertar 1..15 EN ORDEN, que en un ABB comun arma
     * una lista de altura 14, aca deja un arbol perfecto de altura 3.
     */
    public static void main(String[] args) {
        ArbolAvl<Integer> avl = new ArbolAvl<>();
        for (int valor = 1; valor <= 15; valor++) {
            avl.insertar(valor);
        }
        System.out.println("tamanio = " + avl.tamanio());
        System.out.println("altura  = " + avl.altura() + " (un ABB comun mediria 14)");
        System.out.println("raiz    = " + avl.raiz());
        System.out.println("enOrden = " + avl.enOrden());
    }
}
