package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e02;

import java.util.ArrayList;
import java.util.List;

/**
 * ABB con la operacion quitar, que es la mas delicada del arbol.
 *
 * Al quitar un nodo hay tres casos:
 *  1. HOJA (sin hijos): se desengancha y listo.
 *  2. UN HIJO: el hijo "sube" a ocupar el lugar del nodo quitado.
 *  3. DOS HIJOS: no podemos subir a ninguno de los dos sin romper el
 *     invariante. El truco: copiamos en el nodo el valor de su SUCESOR
 *     (el minimo del subarbol derecho, que es el que le sigue en orden)
 *     y despues quitamos ese sucesor del subarbol derecho. Como el
 *     sucesor es un minimo, no tiene hijo izquierdo, asi que quitarlo
 *     cae en el caso 1 o 2.
 *
 * Complejidad: quitar es O(h), igual que insertar y contiene.
 */
public class ArbolBusquedaConQuitar<T extends Comparable<T>> {

    /** Nodo del arbol. El valor no es final: el caso de dos hijos lo pisa. */
    private static class Nodo<T> {
        T valor;
        Nodo<T> izq;
        Nodo<T> der;

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

    /** Inserta el valor sin duplicados, igual que en el ejercicio 1. */
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
        }
        return nodo;
    }

    /** Indica si el valor esta en el arbol. O(h). */
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

    /**
     * Quita el valor del arbol si esta. Si no esta, no pasa nada.
     * Devuelve true si efectivamente se quito algo.
     */
    public boolean quitar(T valor) {
        int tamanioAntes = tamanio;
        raiz = quitar(raiz, valor);
        return tamanio < tamanioAntes;
    }

    /** Devuelve la raiz del subarbol ya sin el valor. */
    private Nodo<T> quitar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            return null; // El valor no estaba: no hay nada que quitar.
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = quitar(nodo.izq, valor);
            return nodo;
        }
        if (comparacion > 0) {
            nodo.der = quitar(nodo.der, valor);
            return nodo;
        }
        // Encontramos el nodo a quitar: separamos los tres casos.
        if (nodo.izq == null && nodo.der == null) {
            // Caso 1: hoja. El padre pasa a apuntar a null.
            tamanio--;
            return null;
        }
        if (nodo.izq == null || nodo.der == null) {
            // Caso 2: un solo hijo. Ese hijo sube al lugar del nodo.
            tamanio--;
            return (nodo.izq != null) ? nodo.izq : nodo.der;
        }
        // Caso 3: dos hijos. Copiamos el sucesor y lo quitamos de la derecha.
        T sucesor = minimo(nodo.der);
        nodo.valor = sucesor;
        nodo.der = quitar(nodo.der, sucesor); // Este quitar decrementa tamanio.
        return nodo;
    }

    /** Minimo de un subarbol: bajar siempre por la izquierda. */
    private T minimo(Nodo<T> nodo) {
        while (nodo.izq != null) {
            nodo = nodo.izq;
        }
        return nodo.valor;
    }

    /** Devuelve los valores ordenados de menor a mayor. */
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

    /** Demostracion de los tres casos de quitar. */
    public static void main(String[] args) {
        ArbolBusquedaConQuitar<Integer> arbol = new ArbolBusquedaConQuitar<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        System.out.println("inicial          = " + arbol.enOrden());
        arbol.quitar(20); // Hoja.
        System.out.println("sin 20 (hoja)    = " + arbol.enOrden());
        arbol.quitar(30); // Le quedo un solo hijo: el 40.
        System.out.println("sin 30 (un hijo) = " + arbol.enOrden());
        arbol.quitar(50); // Dos hijos: lo reemplaza su sucesor, el 60.
        System.out.println("sin 50 (2 hijos) = " + arbol.enOrden());
    }
}
