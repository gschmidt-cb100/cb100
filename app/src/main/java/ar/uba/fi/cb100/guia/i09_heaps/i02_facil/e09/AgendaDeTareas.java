package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e09;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * e09: una agenda que siempre tiene a mano la tarea que vence primero.
 * La prioridad no es un número: es una fecha ({@link LocalDate}), que ya
 * es {@link Comparable}. Con {@code Comparator.comparing(Tarea::fechaLimite)}
 * el heap ordena por vencimiento sin escribir ni una comparación a mano.
 */
public class AgendaDeTareas {

    /** Una tarea pendiente: qué hay que hacer y para cuándo. */
    public record Tarea(String descripcion, LocalDate fechaLimite) {
    }

    /** Estado interno: min-heap por fecha límite (vence antes → sale antes). */
    private final PriorityQueue<Tarea> pendientes =
            new PriorityQueue<>(Comparator.comparing(Tarea::fechaLimite));

    /**
     * Agrega una tarea a la agenda.
     *
     * @param descripcion qué hay que hacer
     * @param fechaLimite fecha de vencimiento
     */
    public void agregar(String descripcion, LocalDate fechaLimite) {
        pendientes.offer(new Tarea(descripcion, fechaLimite));
    }

    /**
     * Devuelve la tarea de vencimiento más próximo SIN sacarla de la agenda.
     *
     * @return la tarea que vence primero
     * @throws NoSuchElementException si la agenda está vacía
     */
    public Tarea proxima() {
        if (pendientes.isEmpty()) {
            throw new NoSuchElementException("La agenda esta vacia");
        }
        return pendientes.peek();
    }

    /**
     * Marca como completada la tarea de vencimiento más próximo: la saca
     * de la agenda y la devuelve.
     *
     * @return la tarea completada
     * @throws NoSuchElementException si la agenda está vacía
     */
    public Tarea completarProxima() {
        if (pendientes.isEmpty()) {
            throw new NoSuchElementException("La agenda esta vacia");
        }
        return pendientes.poll();
    }

    /**
     * @return cantidad de tareas pendientes
     */
    public int pendientes() {
        return pendientes.size();
    }

    public static void main(String[] args) {
        AgendaDeTareas agenda = new AgendaDeTareas();
        agenda.agregar("Entregar TP de heaps", LocalDate.of(2026, 8, 20));
        agenda.agregar("Estudiar para el parcial", LocalDate.of(2026, 8, 15));
        agenda.agregar("Renovar la SUBE", LocalDate.of(2026, 9, 1));
        System.out.println("Proxima: " + agenda.proxima());
        System.out.println("Completo: " + agenda.completarProxima());
        System.out.println("Ahora la proxima es: " + agenda.proxima());
    }
}
