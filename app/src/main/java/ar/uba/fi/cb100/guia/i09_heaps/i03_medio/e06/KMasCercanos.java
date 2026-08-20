package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e06: los k puntos más cercanos al origen.
 *
 * <p>Es el espejo de "top-k mayores": como ahora queremos los k MENORES
 * (por distancia), el heap acotado es un <b>max</b>-heap. Su raíz es
 * "el peor de los cercanos": si aparece un punto más cercano que la raíz,
 * la raíz sobra y la reemplazamos.</p>
 *
 * <p>Costo O(n log k) con memoria O(k), contra O(n log n) de ordenar
 * todos los puntos.</p>
 */
public final class KMasCercanos {

    private KMasCercanos() {
    }

    /**
     * Devuelve los {@code k} puntos más cercanos al origen, ordenados de
     * más cercano a más lejano. Si hay menos de k puntos, devuelve todos.
     *
     * @param puntos puntos candidatos (no se modifica la lista)
     * @param k      cantidad de puntos a devolver, debe ser &gt;= 1
     * @return los k más cercanos, ordenados por distancia ascendente
     * @throws IllegalArgumentException si k &lt; 1
     */
    public static List<Punto> kMasCercanos(List<Punto> puntos, int k) {
        if (k < 1) {
            throw new IllegalArgumentException("k debe ser >= 1, vino " + k);
        }
        // MAX-heap por distancia: la raíz es el más LEJANO de los candidatos.
        PriorityQueue<Punto> heap = new PriorityQueue<>(
                Comparator.comparingDouble(Punto::distanciaAlOrigen).reversed());
        for (Punto p : puntos) {
            if (heap.size() < k) {
                heap.offer(p);
            } else if (p.distanciaAlOrigen() < heap.peek().distanciaAlOrigen()) {
                // El nuevo está más cerca que el peor candidato: lo desplaza.
                heap.poll();
                heap.offer(p);
            }
        }
        // El max-heap entrega de más lejano a más cercano; invertimos.
        List<Punto> resultado = new ArrayList<>(heap.size());
        while (!heap.isEmpty()) {
            resultado.add(0, heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<Punto> puntos = List.of(
                new Punto(3, 4), new Punto(1, 1),
                new Punto(0, 5), new Punto(-2, 0));
        System.out.println("Puntos:        " + puntos);
        System.out.println("2 más cercanos: " + kMasCercanos(puntos, 2));
    }
}
