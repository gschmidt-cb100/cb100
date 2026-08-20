package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e08;

import java.util.PriorityQueue;

/**
 * e08: repartir tareas entre m trabajadores para suavizar la carga.
 *
 * <p>Estrategia golosa: cada tarea (en el orden en que llega) se asigna al
 * trabajador MENOS cargado hasta el momento. Un min-heap con la carga
 * acumulada de cada trabajador responde "¿quién está más libre?" en
 * O(log m) por tarea.</p>
 *
 * <p>Así funcionan, en esencia, los balanceadores de carga "least
 * connections" y el scheduling de pools de threads. Con n tareas el costo
 * es O(n log m).</p>
 */
public final class BalanceDeCarga {

    private BalanceDeCarga() {
    }

    /**
     * Asigna cada tarea, en el orden dado, al trabajador menos cargado
     * y devuelve la carga del trabajador más cargado al final.
     *
     * @param duraciones duración de cada tarea, en orden de llegada
     * @param m          cantidad de trabajadores, debe ser &gt;= 1
     * @return máxima carga acumulada por un trabajador al terminar
     * @throws IllegalArgumentException si m &lt; 1
     */
    public static long cargaMaxima(int[] duraciones, int m) {
        if (m < 1) {
            throw new IllegalArgumentException("m debe ser >= 1, vino " + m);
        }
        // Min-heap de cargas: la raíz es el trabajador más libre.
        PriorityQueue<Long> cargas = new PriorityQueue<>();
        for (int i = 0; i < m; i++) {
            cargas.offer(0L);
        }
        for (int duracion : duraciones) {
            // El más libre recibe la tarea y vuelve al heap con su nueva carga.
            long carga = cargas.poll();
            cargas.offer(carga + duracion);
        }
        // La respuesta es el máximo del heap: lo buscamos vaciándolo.
        long maxima = 0;
        while (!cargas.isEmpty()) {
            maxima = cargas.poll();   // el último poll es el máximo
        }
        return maxima;
    }

    public static void main(String[] args) {
        int[] tareas = {2, 3, 7, 1, 4, 6};
        System.out.println("Tareas: [2, 3, 7, 1, 4, 6] con 3 trabajadores");
        System.out.println("Carga máxima final: " + cargaMaxima(tareas, 3));
    }
}
