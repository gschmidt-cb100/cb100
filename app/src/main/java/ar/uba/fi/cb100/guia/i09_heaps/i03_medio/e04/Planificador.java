package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e04: planificador de CPU por prioridades.
 *
 * <p>Los sistemas operativos eligen "el próximo proceso a ejecutar" miles de
 * veces por segundo: exactamente el trabajo de una cola de prioridad. Acá el
 * criterio es doble y lo expresamos con un {@link Comparator} encadenado:</p>
 * <ol>
 *   <li>prioridad menor primero ({@code comparingInt});</li>
 *   <li>a igual prioridad, el que llegó antes ({@code thenComparingLong}),
 *       para que ningún proceso "sufra inanición" frente a pares iguales.</li>
 * </ol>
 *
 * <p>Ojo: una {@link PriorityQueue} NO es estable por sí sola; sin el
 * desempate por llegada, dos procesos de igual prioridad podrían salir en
 * cualquier orden.</p>
 */
public class Planificador {

    private final PriorityQueue<Proceso> listos = new PriorityQueue<>(
            Comparator.comparingInt(Proceso::prioridad)
                    .thenComparingLong(Proceso::llegada));

    /**
     * Encola un proceso listo para ejecutar, en O(log n).
     *
     * @param p proceso a planificar
     */
    public void agregar(Proceso p) {
        listos.offer(p);
    }

    /**
     * Ejecuta (desencola) todos los procesos y devuelve sus pids en el
     * orden en que la CPU los atendería.
     *
     * @return pids ordenados por prioridad ascendente, empate por llegada
     */
    public List<Integer> ejecutarTodos() {
        List<Integer> orden = new ArrayList<>();
        while (!listos.isEmpty()) {
            orden.add(listos.poll().pid());
        }
        return orden;
    }

    /** @return cuántos procesos esperan en la cola de listos */
    public int pendientes() {
        return listos.size();
    }

    public static void main(String[] args) {
        Planificador plan = new Planificador();
        plan.agregar(new Proceso(100, 3, 0));
        plan.agregar(new Proceso(200, 1, 1));
        plan.agregar(new Proceso(300, 3, 2));
        plan.agregar(new Proceso(400, 2, 3));
        System.out.println("Orden de ejecución: " + plan.ejecutarTodos());
    }
}
