package ar.uba.fi.cb100.material.i09_heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * La cola de prioridad <b>de la API de Java</b>: {@link PriorityQueue}, un
 * min-heap binario. Es la que se usa en parciales, TPs y finales.
 * <p>OJO: al RECORRERLA (iterator/toString) los elementos NO salen ordenados —
 * el heap sólo garantiza la raíz. Para consumir en orden hay que hacer
 * {@code poll()} repetidamente.</p>
 */
public class DemoApiNativa {

    public static void main(String[] args) {
        // --- min-heap por defecto (orden natural) ------------------------------
        PriorityQueue<Integer> cola = new PriorityQueue<>();
        cola.offer(7); cola.offer(3); cola.offer(9); cola.offer(1);
        System.out.println(cola.peek());     // 1  (el mínimo, sin sacarlo)
        System.out.println(cola.poll());     // 1  (lo saca)
        System.out.println(cola.poll());     // 3
        System.out.println(cola.size());     // 2

        // ¡el iterador NO está ordenado!
        PriorityQueue<Integer> otra = new PriorityQueue<>();
        for (int v : new int[]{5, 1, 4, 2, 3}) {
            otra.offer(v);
        }
        System.out.println(otra);            // p. ej. [1, 2, 4, 5, 3] — NO es el orden

        // --- max-heap: se invierte el comparador -------------------------------
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer(7); maxHeap.offer(3); maxHeap.offer(9);
        System.out.println(maxHeap.poll());  // 9  (ahora sale el MAYOR)

        // --- prioridad por un campo, con desempate -----------------------------
        record Tarea(String nombre, int prioridad, long llegada) {}
        PriorityQueue<Tarea> tareas = new PriorityQueue<>(
                Comparator.comparingInt(Tarea::prioridad)          // menor número = más urgente
                          .thenComparingLong(Tarea::llegada));     // empate: orden de llegada
        tareas.offer(new Tarea("backup", 3, 1));
        tareas.offer(new Tarea("incendio", 1, 2));
        tareas.offer(new Tarea("mail", 3, 0));
        System.out.println(tareas.poll().nombre());   // incendio
        System.out.println(tareas.poll().nombre());   // mail  (empate 3: llegó antes)
        System.out.println(tareas.poll().nombre());   // backup
    }
}
