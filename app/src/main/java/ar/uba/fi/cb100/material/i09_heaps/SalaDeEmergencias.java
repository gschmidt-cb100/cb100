package ar.uba.fi.cb100.material.i09_heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <b>Ejemplo integrador de la Unidad 9</b>: el triage de una guardia médica.
 * Los pacientes NO se atienden por orden de llegada (eso sería una Cola, U5)
 * sino por <b>gravedad</b> — y a igual gravedad, por llegada (desempate FIFO).
 * Exactamente lo que modela una cola de prioridad.
 */
public class SalaDeEmergencias {

    /** Gravedad 1 = crítico … 5 = leve (como el triage real). */
    public record Paciente(String nombre, int gravedad, long numeroDeLlegada) {}

    private final PriorityQueue<Paciente> espera = new PriorityQueue<>(
            Comparator.comparingInt(Paciente::gravedad)            // más grave primero
                      .thenComparingLong(Paciente::numeroDeLlegada)); // empate: FIFO
    private long proximoNumero = 0;

    public void ingresar(String nombre, int gravedad) {
        if (gravedad < 1 || gravedad > 5) {
            throw new IllegalArgumentException("gravedad 1..5");
        }
        espera.offer(new Paciente(nombre, gravedad, proximoNumero++));
    }

    /** El próximo a atender (el más grave; a igual gravedad, el que llegó antes). */
    public String atender() {
        Paciente p = espera.poll();
        if (p == null) {
            throw new IllegalStateException("no hay pacientes");
        }
        return p.nombre();
    }

    public int enEspera() { return espera.size(); }

    public static void main(String[] args) {
        SalaDeEmergencias guardia = new SalaDeEmergencias();
        guardia.ingresar("ana", 3);       // llega primera, gravedad media
        guardia.ingresar("juan", 5);      // leve
        guardia.ingresar("mia", 1);       // ¡crítica!
        guardia.ingresar("leo", 3);       // misma gravedad que ana, llegó después

        System.out.println(guardia.atender());   // mia   (gravedad 1)
        System.out.println(guardia.atender());   // ana   (gravedad 3, llegó antes que leo)
        System.out.println(guardia.atender());   // leo
        System.out.println(guardia.atender());   // juan  (el leve espera)
    }
}
