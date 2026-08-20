package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e06;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * e06: fusión de agendas — juntar los calendarios de todo el equipo.
 *
 * <p>Cada persona tiene su lista de eventos; queremos UNA vista global
 * ordenada por hora, y detectar las horas conflictivas (más de un evento).</p>
 *
 * <p><b>Elección de estructura:</b> {@code TreeMap<String, List<String>>}
 * (U8): la clave es la hora en formato "HH:MM", cuyo orden alfabético
 * coincide con el cronológico, así que el árbol nos regala la agenda ya
 * ordenada — sin ningún sort explícito. Un {@code HashMap} obligaría a
 * ordenar las claves aparte cada vez que mostramos la agenda (U7).
 * Detectar conflictos es entonces trivial: las horas cuya lista tiene
 * más de una entrada.</p>
 */
public final class FusionDeAgendas {

    /** Un evento de agenda: hora "HH:MM" y título. Record inmutable (U3). */
    public record Evento(String hora, String titulo) {}

    private final Map<String, List<Evento>> agendasPorPersona;

    public FusionDeAgendas(Map<String, List<Evento>> agendasPorPersona) {
        this.agendasPorPersona = Map.copyOf(agendasPorPersona);
    }

    /**
     * La agenda global: hora → lista de "titulo (persona)", con las horas
     * en orden cronológico gracias al TreeMap.
     */
    public Map<String, List<String>> agendaGlobal() {
        Map<String, List<String>> global = new TreeMap<>();
        // TreeMap también para recorrer personas en orden estable.
        new TreeMap<>(agendasPorPersona).forEach((persona, eventos) -> {
            for (Evento evento : eventos) {
                global.computeIfAbsent(evento.hora(), hora -> new ArrayList<>())
                        .add(evento.titulo() + " (" + persona + ")");
            }
        });
        return global;
    }

    /** Las horas con más de un evento, en orden cronológico. */
    public List<String> conflictos() {
        return agendaGlobal().entrySet().stream()
                .filter(entrada -> entrada.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static void main(String[] args) {
        FusionDeAgendas fusion = new FusionDeAgendas(Map.of(
                "Ana", List.of(new Evento("09:00", "Daily"),
                               new Evento("14:00", "Demo")),
                "Beto", List.of(new Evento("09:00", "Daily"),
                                new Evento("11:30", "Entrevista"))));

        fusion.agendaGlobal().forEach((hora, eventos) ->
                System.out.println(hora + " -> " + eventos));
        // 09:00 -> [Daily (Ana), Daily (Beto)]
        // 11:30 -> [Entrevista (Beto)]
        // 14:00 -> [Demo (Ana)]
        System.out.println("Conflictos: " + fusion.conflictos());  // [09:00]
    }
}
