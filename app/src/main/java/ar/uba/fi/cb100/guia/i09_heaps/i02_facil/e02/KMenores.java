package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e02;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e02: obtener los k menores elementos de un arreglo. La gracia de la
 * cola de prioridad es que no hace falta ordenar todo: encolamos los n
 * elementos y desencolamos solo k veces.
 */
public final class KMenores {

    private KMenores() {
    }

    /**
     * Devuelve los {@code k} menores elementos de {@code a}, de menor a mayor.
     * Si {@code k} es mayor que la cantidad de elementos, devuelve todos.
     *
     * <p>Costo: O(n log n) por las inserciones más O(k log n) por las
     * extracciones.</p>
     *
     * @param a arreglo de entrada (no se modifica)
     * @param k cantidad de mínimos pedidos (no puede ser negativa)
     * @return lista con los k menores, ordenados de menor a mayor
     * @throws IllegalArgumentException si {@code k} es negativo
     */
    public static List<Integer> kMenores(int[] a, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k no puede ser negativo: " + k);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : a) {
            heap.offer(x);
        }
        List<Integer> resultado = new ArrayList<>();
        // Si piden más de los que hay, devolvemos todos.
        int cuantos = Math.min(k, a.length);
        for (int i = 0; i < cuantos; i++) {
            resultado.add(heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {40, 10, 30, 50, 20};
        System.out.println("Los 3 menores de [40, 10, 30, 50, 20]: " + kMenores(datos, 3));
        System.out.println("Pidiendo 10 (mas que el tamano): " + kMenores(datos, 10));
    }
}
