package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e04;

import java.util.Arrays;

/**
 * Heapsort A MANO, en el lugar (in-place) y sin memoria extra.
 *
 * Dos fases sobre el MISMO arreglo:
 *  1. Construir un MAX-heap con heapify: hundir desde n/2 - 1 hasta 0. O(n).
 *     Usamos max-heap (y no min) porque asi el mayor queda en la posicion 0
 *     y podemos mandarlo al FINAL del arreglo, que es donde va en el orden
 *     ascendente.
 *  2. Repetir n - 1 veces: intercambiar la raiz (el maximo de lo que queda)
 *     con la ultima posicion "viva", achicar el heap en 1 y hundir la nueva
 *     raiz. Cada paso deja un maximo mas en su lugar definitivo. O(n log n).
 *
 * Total: O(n log n) en el peor caso, O(1) de memoria extra. A diferencia de
 * mergesort no necesita arreglo auxiliar, y a diferencia de quicksort no
 * tiene peor caso cuadratico. No es estable, pero con int eso no se nota.
 */
public final class OrdenadorHeapsort {

    private OrdenadorHeapsort() {
        // Clase de utilidad: no se instancia.
    }

    /** Ordena el arreglo de menor a mayor, en el lugar. O(n log n). */
    public static void ordenar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        int n = a.length;

        // Fase 1: heapify a max-heap, hundiendo los nodos internos de atras hacia adelante.
        for (int i = n / 2 - 1; i >= 0; i--) {
            hundir(a, i, n);
        }

        // Fase 2: el maximo (raiz) va al final; el heap se achica de a uno.
        for (int fin = n - 1; fin > 0; fin--) {
            intercambiar(a, 0, fin);
            hundir(a, 0, fin); // Solo las posiciones 0..fin-1 siguen siendo heap.
        }
    }

    /**
     * Hunde a[i] dentro del prefijo a[0..limite-1], comparando con el MAYOR
     * de sus dos hijos (2*i + 1 y 2*i + 2). Version max-heap del hundir.
     */
    private static void hundir(int[] a, int i, int limite) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int mayor = i;
            if (izquierdo < limite && a[izquierdo] > a[mayor]) {
                mayor = izquierdo;
            }
            if (derecho < limite && a[derecho] > a[mayor]) {
                mayor = derecho;
            }
            if (mayor == i) {
                break;
            }
            intercambiar(a, i, mayor);
            i = mayor;
        }
    }

    private static void intercambiar(int[] a, int i, int j) {
        int aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }

    /** Demostracion con un arreglo chico. */
    public static void main(String[] args) {
        int[] valores = {12, 5, 9, 3, 20, 7};
        OrdenadorHeapsort.ordenar(valores);
        System.out.println("Ordenado: " + Arrays.toString(valores));
    }
}
