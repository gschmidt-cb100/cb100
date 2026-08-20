package ar.uba.fi.cb100.guia.i08_arboles.i03_medio.e09;

import java.util.Arrays;

/**
 * Ejercicio: construir un ABB balanceado a partir de un arreglo ordenado.
 * La estrategia es división y conquista: el elemento del medio es la
 * raíz (deja mitad de los valores a cada lado) y cada mitad se resuelve
 * recursivamente. El árbol resultante tiene altura O(log n).
 */
public final class ConstructorBalanceado {

    private ConstructorBalanceado() {
    }

    /**
     * Construye un ABB balanceado con los valores del arreglo,
     * que debe venir ordenado de menor a mayor y sin repetidos.
     *
     * @param ordenado arreglo ordenado ascendente
     * @return la raíz del ABB balanceado, o {@code null} si el arreglo es vacío
     */
    public static Nodo desdeOrdenado(int[] ordenado) {
        return construir(ordenado, 0, ordenado.length - 1);
    }

    /**
     * Construye el subárbol con los valores de las posiciones
     * [desde, hasta]: el del medio como raíz y una mitad para cada hijo.
     */
    private static Nodo construir(int[] ordenado, int desde, int hasta) {
        if (desde > hasta) {
            return null;
        }
        int medio = desde + (hasta - desde) / 2;
        return new Nodo(ordenado[medio],
                construir(ordenado, desde, medio - 1),
                construir(ordenado, medio + 1, hasta));
    }

    /** Demostración: 15 valores ordenados generan un árbol de altura 3. */
    public static void main(String[] args) {
        int[] valores = new int[15];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = i + 1;
        }
        Nodo raiz = desdeOrdenado(valores);
        System.out.println("Entrada: " + Arrays.toString(valores));
        System.out.println("Raíz del ABB balanceado: " + raiz.valor);
        System.out.println("Hijos de la raíz: " + raiz.izquierdo.valor + " y " + raiz.derecho.valor);
    }
}
