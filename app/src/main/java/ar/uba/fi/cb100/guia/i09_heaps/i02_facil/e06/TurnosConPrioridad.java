package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e06;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * e06: turnos de atención donde los jubilados tienen prioridad y, dentro
 * de cada grupo, se respeta el orden de llegada (FIFO).
 *
 * <p>La {@link PriorityQueue} de Java <em>no es estable</em>: entre elementos
 * "empatados" según el comparador no garantiza orden de llegada. Por eso el
 * truco clásico: guardar un número de llegada incremental y usarlo como
 * criterio de desempate.</p>
 */
public class TurnosConPrioridad {

    /** Un cliente en espera: nombre, si es jubilado y su número de llegada. */
    public record Cliente(String nombre, boolean esJubilado, long llegada) {
    }

    /**
     * Primero por "no es jubilado" (false &lt; true, así que los jubilados
     * quedan adelante) y después por número de llegada.
     */
    private static final Comparator<Cliente> POR_PRIORIDAD =
            Comparator.comparing((Cliente c) -> !c.esJubilado())
                    .thenComparingLong(Cliente::llegada);

    private final PriorityQueue<Cliente> espera = new PriorityQueue<>(POR_PRIORIDAD);

    /** Contador incremental: a cada turno nuevo le asigna su número de llegada. */
    private long proximaLlegada = 0;

    /**
     * Registra la llegada de un cliente y le asigna su número de orden.
     *
     * @param nombre     nombre del cliente
     * @param esJubilado {@code true} si tiene prioridad de jubilado
     */
    public void sacarTurno(String nombre, boolean esJubilado) {
        espera.offer(new Cliente(nombre, esJubilado, proximaLlegada++));
    }

    /**
     * Atiende (y saca de la espera) al cliente que corresponde: el jubilado
     * que llegó primero o, si no hay jubilados, el no-jubilado que llegó primero.
     *
     * @return nombre del cliente atendido
     * @throws NoSuchElementException si no hay nadie esperando
     */
    public String atender() {
        if (espera.isEmpty()) {
            throw new NoSuchElementException("No hay clientes esperando");
        }
        return espera.poll().nombre();
    }

    /**
     * @return cantidad de clientes que siguen esperando
     */
    public int enEspera() {
        return espera.size();
    }

    public static void main(String[] args) {
        TurnosConPrioridad turnos = new TurnosConPrioridad();
        turnos.sacarTurno("Ana", false);
        turnos.sacarTurno("Beto", false);
        turnos.sacarTurno("Clara", true);   // llega última pero es jubilada
        System.out.println("1ro: " + turnos.atender()); // Clara
        System.out.println("2do: " + turnos.atender()); // Ana
        System.out.println("3ro: " + turnos.atender()); // Beto
    }
}
