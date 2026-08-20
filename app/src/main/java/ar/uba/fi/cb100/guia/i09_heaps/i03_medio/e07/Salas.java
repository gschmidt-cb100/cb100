package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e07: ¿cuántas salas hacen falta para que ninguna reunión espere?
 *
 * <p>Algoritmo goloso clásico:</p>
 * <ol>
 *   <li>ordenamos las reuniones por hora de inicio;</li>
 *   <li>mantenemos un min-heap con la hora de FIN de cada sala ocupada
 *       (la raíz es la sala que se libera primero);</li>
 *   <li>para cada reunión: si la sala que primero se libera ya terminó
 *       ({@code peek() <= inicio}), la reutilizamos (poll); si no, hace
 *       falta una sala nueva. En ambos casos encolamos el fin.</li>
 * </ol>
 *
 * <p>Al final el heap tiene una entrada por sala usada: su tamaño es la
 * respuesta. Costo O(n log n) por el ordenamiento y las operaciones de heap.</p>
 */
public final class Salas {

    private Salas() {
    }

    /**
     * Calcula la cantidad mínima de salas para celebrar todas las
     * reuniones sin superposición dentro de una misma sala.
     *
     * @param reuniones reuniones a acomodar (la lista no se modifica)
     * @return cantidad mínima de salas; 0 si no hay reuniones
     */
    public static int salasNecesarias(List<Reunion> reuniones) {
        List<Reunion> porInicio = new ArrayList<>(reuniones);
        porInicio.sort(Comparator.comparingInt(Reunion::inicio));

        // Min-heap de horas de fin: la raíz es la sala que se libera primero.
        PriorityQueue<Integer> fines = new PriorityQueue<>();
        for (Reunion r : porInicio) {
            if (!fines.isEmpty() && fines.peek() <= r.inicio()) {
                // La sala que primero se libera ya terminó: la reutilizamos.
                fines.poll();
            }
            fines.offer(r.fin());
        }
        return fines.size();
    }

    public static void main(String[] args) {
        List<Reunion> reuniones = List.of(
                new Reunion(0, 30), new Reunion(5, 10), new Reunion(15, 20));
        System.out.println("Reuniones: " + reuniones);
        System.out.println("Salas necesarias: " + salasNecesarias(reuniones));
    }
}
