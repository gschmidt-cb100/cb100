package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e10: simulación por eventos discretos.
 *
 * <p>Los simuladores (de redes, de colas de banco, de tráfico) no avanzan
 * el reloj de a un tick: saltan directamente al próximo evento. La agenda
 * de eventos futuros es una cola de prioridad ordenada por tiempo, y lo
 * interesante es que procesar un evento puede <b>agendar nuevos eventos</b>
 * futuros: el heap crece y se achica mientras corre la simulación.</p>
 *
 * <p>Dos detalles de diseño:</p>
 * <ul>
 *   <li>la acción de un evento es una interfaz funcional ({@link Accion}),
 *       así cada evento agenda lo que quiera con una lambda que recibe el
 *       propio simulador;</li>
 *   <li>como {@link PriorityQueue} no es estable, cada evento lleva un
 *       número de secuencia para que los empates de tiempo respeten el
 *       orden en que se agendaron.</li>
 * </ul>
 */
public class Simulador {

    /** Lo que hace un evento al dispararse: puede agendar más eventos. */
    @FunctionalInterface
    public interface Accion {
        /**
         * Se ejecuta cuando el evento se procesa.
         *
         * @param simulador el simulador, para poder agendar nuevos eventos
         */
        void ejecutar(Simulador simulador);
    }

    /** Un evento agendado; {@code secuencia} desempata tiempos iguales. */
    private record Evento(long tiempo, String nombre, Accion accion, long secuencia) {
    }

    private final PriorityQueue<Evento> agenda = new PriorityQueue<>(
            Comparator.comparingLong(Evento::tiempo)
                    .thenComparingLong(Evento::secuencia));

    private long proximaSecuencia = 0;

    /**
     * Agenda un evento sin acción asociada.
     *
     * @param tiempo instante de disparo
     * @param nombre etiqueta del evento (aparece en el log)
     */
    public void agendar(long tiempo, String nombre) {
        agendar(tiempo, nombre, null);
    }

    /**
     * Agenda un evento con una acción que se ejecuta al dispararse
     * (por ejemplo, para agendar eventos futuros).
     *
     * @param tiempo instante de disparo
     * @param nombre etiqueta del evento (aparece en el log)
     * @param accion qué hacer al disparar el evento; puede ser null
     */
    public void agendar(long tiempo, String nombre, Accion accion) {
        agenda.offer(new Evento(tiempo, nombre, accion, proximaSecuencia++));
    }

    /**
     * Corre la simulación hasta vaciar la agenda: procesa los eventos en
     * orden de tiempo (empates por orden de agenda) y ejecuta sus acciones.
     *
     * @return los nombres de los eventos, en el orden en que se procesaron
     */
    public List<String> correr() {
        List<String> log = new ArrayList<>();
        while (!agenda.isEmpty()) {
            Evento evento = agenda.poll();
            log.add(evento.nombre());
            if (evento.accion() != null) {
                // La acción puede agendar eventos nuevos: el heap sigue vivo.
                evento.accion().ejecutar(this);
            }
        }
        return log;
    }

    public static void main(String[] args) {
        Simulador sim = new Simulador();
        sim.agendar(10, "llega cliente", s -> {
            s.agendar(15, "empieza atención");
            s.agendar(25, "termina atención");
        });
        sim.agendar(20, "llega otro cliente");
        System.out.println("Orden de eventos: " + sim.correr());
    }
}
