package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e06;

import java.util.Arrays;

/**
 * Validador de min-heap: decide si un arreglo, leido como arbol por niveles,
 * cumple el invariante "cada padre es menor o igual que sus hijos".
 *
 * Alcanza con recorrer los PADRES (indices 0 a n/2 - 1, los unicos nodos
 * con al menos un hijo) y comparar cada uno con AMBOS hijos existentes:
 *  - hijo izquierdo: 2*i + 1 (existe siempre que i < n/2).
 *  - hijo derecho:   2*i + 2 (puede no existir en el ultimo padre).
 *
 * Si todos los pares padre-hijo estan bien, el invariante vale globalmente
 * por transitividad. Complejidad: O(n), memoria O(1).
 *
 * Error tipico que este validador evita: chequear solo el hijo izquierdo.
 * Un arreglo como {1, 2, 0} pasa ese chequeo trucho (1 <= 2) pero no es
 * heap, porque el hijo derecho 0 es menor que la raiz.
 */
public final class ValidadorDeHeap {

    private ValidadorDeHeap() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Indica si el arreglo es un min-heap valido. Los arreglos vacios o de
     * un solo elemento son heaps trivialmente (no hay par padre-hijo que
     * pueda fallar).
     */
    public static boolean esMinHeap(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        int n = a.length;
        for (int i = 0; i < n / 2; i++) { // Solo los padres: i en 0..n/2-1.
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            if (a[i] > a[izquierdo]) { // El izquierdo existe seguro si i es padre.
                return false;
            }
            if (derecho < n && a[i] > a[derecho]) { // El derecho puede no existir.
                return false;
            }
        }
        return true;
    }

    /** Demostracion: un heap valido y el mismo con una posicion rota. */
    public static void main(String[] args) {
        int[] valido = {3, 5, 7, 12, 20, 9};
        int[] roto = {3, 5, 7, 12, 4, 9}; // El 4 es menor que su padre 5.
        System.out.println(Arrays.toString(valido) + " -> " + esMinHeap(valido));
        System.out.println(Arrays.toString(roto) + " -> " + esMinHeap(roto));
    }
}
