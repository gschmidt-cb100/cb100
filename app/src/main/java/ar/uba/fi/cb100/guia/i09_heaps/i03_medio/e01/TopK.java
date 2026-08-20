package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * e01: los k mayores de un stream que no entra en memoria.
 *
 * <p>La idea contraintuitiva: para quedarnos con los k <b>mayores</b> usamos
 * un <b>min</b>-heap de tamaño k. La raíz del heap es "el peor de los
 * mejores": si llega un valor más grande que la raíz, la raíz sobra
 * (hay k valores mejores que ella) y la reemplazamos. Si llega algo menor
 * o igual que la raíz, lo descartamos sin tocar el heap.</p>
 *
 * <p>Costo: O(n log k) en tiempo y O(k) en memoria, mucho mejor que
 * ordenar todo (O(n log n) y O(n)) cuando k es chico frente a n.</p>
 */
public final class TopK {

    private TopK() {
    }

    /**
     * Devuelve los {@code k} valores más grandes del stream, ordenados
     * de mayor a menor. Si el stream tiene menos de k elementos,
     * devuelve todos los que haya.
     *
     * @param stream iterador sobre los valores (se consume una sola vez)
     * @param k      cantidad de máximos a retener, debe ser &gt;= 1
     * @return lista con los k mayores, en orden descendente
     * @throws IllegalArgumentException si k &lt; 1
     */
    public static List<Integer> topK(Iterator<Integer> stream, int k) {
        if (k < 1) {
            throw new IllegalArgumentException("k debe ser >= 1, vino " + k);
        }
        // Min-heap: la raíz es el MENOR de los k candidatos actuales.
        PriorityQueue<Integer> heap = new PriorityQueue<>(k);
        while (stream.hasNext()) {
            int valor = stream.next();
            if (heap.size() < k) {
                heap.offer(valor);
            } else if (valor > heap.peek()) {
                // El nuevo supera al peor candidato: lo desplaza.
                heap.poll();
                heap.offer(valor);
            }
        }
        // El heap sale de menor a mayor; damos vuelta para orden descendente.
        List<Integer> resultado = new ArrayList<>(heap.size());
        while (!heap.isEmpty()) {
            resultado.add(0, heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        Random azar = new Random(42);
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datos.add(azar.nextInt(100));
        }
        System.out.println("Stream:   " + datos);
        System.out.println("Top 5:    " + topK(datos.iterator(), 5));
    }
}
