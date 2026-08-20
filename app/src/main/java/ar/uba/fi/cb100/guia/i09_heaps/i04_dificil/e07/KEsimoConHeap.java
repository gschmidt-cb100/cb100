package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e07;

import java.util.Arrays;

/**
 * K-esimo menor con heap PROPIO: heapify O(n) + k extracciones.
 *
 * Estrategia:
 *  1. Copiamos el arreglo y lo convertimos en min-heap con heapify
 *     (hundir desde n/2 - 1 hasta 0): O(n), mejor que encolar de a uno.
 *  2. Desencolamos el minimo k - 1 veces y el siguiente es la respuesta.
 *
 * Complejidad total: O(n + k log n). Para k chico es casi lineal y le gana
 * a ordenar todo (O(n log n)). Los duplicados cuentan por posicion: en
 * {7, 7, 1} el 2do menor es 7.
 *
 * El mini-heap de int esta replicado adentro (metodos estaticos sobre el
 * arreglo copia) para que el ejercicio sea autocontenido.
 */
public final class KEsimoConHeap {

    private KEsimoConHeap() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Devuelve el k-esimo menor del arreglo (k = 1 es el minimo).
     *
     * @throws IllegalArgumentException si a es null o k esta fuera de 1..a.length.
     */
    public static int kEsimoMenor(int[] a, int k) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException(
                    "k debe estar entre 1 y " + a.length + ", vino " + k);
        }

        // Paso 1: heapify O(n) sobre una copia (no rompemos el arreglo del llamador).
        int[] heap = a.clone();
        int tamanio = heap.length;
        for (int i = tamanio / 2 - 1; i >= 0; i--) {
            hundir(heap, i, tamanio);
        }

        // Paso 2: k - 1 extracciones descartadas y la k-esima es la respuesta.
        for (int extraccion = 1; extraccion < k; extraccion++) {
            tamanio--;
            heap[0] = heap[tamanio]; // Ultima hoja a la raiz.
            hundir(heap, 0, tamanio);
        }
        return heap[0];
    }

    /** Hundir de min-heap sobre el prefijo heap[0..limite-1], mirando ambos hijos. */
    private static void hundir(int[] heap, int i, int limite) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < limite && heap[izquierdo] < heap[menor]) {
                menor = izquierdo;
            }
            if (derecho < limite && heap[derecho] < heap[menor]) {
                menor = derecho;
            }
            if (menor == i) {
                break;
            }
            int aux = heap[i];
            heap[i] = heap[menor];
            heap[menor] = aux;
            i = menor;
        }
    }

    /** Demostracion: los k-esimos de un arreglo desordenado. */
    public static void main(String[] args) {
        int[] valores = {12, 5, 9, 3, 20, 7};
        System.out.println("Arreglo: " + Arrays.toString(valores));
        for (int k = 1; k <= valores.length; k++) {
            System.out.println("k = " + k + " -> " + kEsimoMenor(valores, k));
        }
    }
}
