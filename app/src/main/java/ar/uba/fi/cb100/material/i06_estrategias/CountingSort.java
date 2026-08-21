package ar.uba.fi.cb100.material.i06_estrategias;

import java.util.Arrays;

/**
 * <b>Counting sort</b>: ordena SIN comparar elementos entre sí. Cuenta cuántas
 * veces aparece cada valor y reconstruye el arreglo ordenado. Sirve para enteros
 * en un rango acotado [0, k]. Complejidad $O(n + k)$: puede ser <b>lineal</b>,
 * más rápido que el límite $O(n \log n)$ de los ordenamientos por comparación.
 */
public class CountingSort {

    public static int[] ordenar(int[] a, int maximo) {
        int[] conteo = new int[maximo + 1];
        for (int x : a) {
            conteo[x]++;   // contar apariciones
        }

        int[] resultado = new int[a.length];
        int i = 0;
        for (int valor = 0; valor <= maximo; valor++) {
            while (conteo[valor]-- > 0) {
                resultado[i++] = valor;   // reconstruir
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] a = {4, 2, 2, 8, 3, 3, 1};
        System.out.println(Arrays.toString(ordenar(a, 8)));   // [1, 2, 2, 3, 3, 4, 8]
    }
}
