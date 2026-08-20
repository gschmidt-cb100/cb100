package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e08;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * e08: mesa de entradas donde los trámites urgentes se atienden primero y,
 * dentro de cada categoría, por orden de llegada.
 * <p>
 * <b>Elección de estructura:</b> DOS {@link ArrayDeque}, una para urgentes y
 * otra para normales. Con sólo dos niveles de prioridad no hace falta un
 * heap: cada cola es FIFO puro con encolar y desencolar O(1) en las puntas
 * (U5), y "atender" es mirar primero la cola de urgentes. Una
 * {@code PriorityQueue} (U9) con comparator (urgente, número) también
 * funciona, pero paga O(log n) por operación para resolver un problema que
 * con dos colas es O(1) — y el orden de llegada ya nos lo regala el FIFO,
 * sin necesidad de comparar números de trámite.
 */
public class MesaDeEntradas {

    /** Un trámite con su número de ingreso. */
    public record Tramite(int numero, String descripcion, boolean urgente) {}

    private final Deque<Tramite> urgentes = new ArrayDeque<>();
    private final Deque<Tramite> normales = new ArrayDeque<>();
    private int proximoNumero = 1;   // los números de trámite son crecientes

    /**
     * Ingresa un trámite nuevo y le asigna el próximo número. O(1).
     *
     * @param descripcion qué se tramita
     * @param urgente     si debe atenderse antes que los normales
     * @return el número asignado al trámite
     */
    public int ingresar(String descripcion, boolean urgente) {
        Tramite tramite = new Tramite(proximoNumero++, descripcion, urgente);
        if (urgente) {
            urgentes.addLast(tramite);
        } else {
            normales.addLast(tramite);
        }
        return tramite.numero();
    }

    /**
     * Atiende (y saca) el próximo trámite: el urgente más antiguo si hay
     * alguno, si no el normal más antiguo. O(1).
     *
     * @return el trámite atendido, o {@code null} si no hay nadie esperando
     */
    public Tramite atender() {
        if (!urgentes.isEmpty()) {
            return urgentes.pollFirst();
        }
        return normales.pollFirst();   // null si tampoco hay normales
    }

    /** @return cantidad de trámites esperando ser atendidos */
    public int enEspera() {
        return urgentes.size() + normales.size();
    }

    public static void main(String[] args) {
        MesaDeEntradas mesa = new MesaDeEntradas();
        mesa.ingresar("Cambio de carrera", false);   // nro 1
        mesa.ingresar("Título en trámite", true);    // nro 2, urgente
        mesa.ingresar("Constancia de alumno", false);// nro 3
        mesa.ingresar("Reincorporación", true);      // nro 4, urgente
        while (mesa.enEspera() > 0) {
            System.out.println("Atendiendo: " + mesa.atender());
        }
        // Orden: 2, 4 (urgentes por llegada), 1, 3 (normales por llegada)
    }
}
