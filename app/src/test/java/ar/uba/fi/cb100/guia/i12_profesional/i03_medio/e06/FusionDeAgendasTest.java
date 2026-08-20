package ar.uba.fi.cb100.guia.i12_profesional.i03_medio.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FusionDeAgendasTest {

    @Test
    @DisplayName("La agenda global sale ordenada por hora")
    void agendaOrdenada() {
        FusionDeAgendas fusion = new FusionDeAgendas(Map.of(
                "Ana", List.of(new FusionDeAgendas.Evento("14:00", "Demo"),
                               new FusionDeAgendas.Evento("09:00", "Daily")),
                "Beto", List.of(new FusionDeAgendas.Evento("11:30", "Entrevista"))));

        Map<String, List<String>> global = fusion.agendaGlobal();
        // El TreeMap garantiza el orden cronológico de las claves.
        assertEquals(List.of("09:00", "11:30", "14:00"),
                List.copyOf(global.keySet()));
        assertEquals(List.of("Daily (Ana)"), global.get("09:00"));
        assertEquals(List.of("Entrevista (Beto)"), global.get("11:30"));
    }

    @Test
    @DisplayName("Dos eventos a la misma hora son un conflicto")
    void conConflictos() {
        FusionDeAgendas fusion = new FusionDeAgendas(Map.of(
                "Ana", List.of(new FusionDeAgendas.Evento("09:00", "Daily"),
                               new FusionDeAgendas.Evento("14:00", "Demo")),
                "Beto", List.of(new FusionDeAgendas.Evento("09:00", "Daily"),
                                new FusionDeAgendas.Evento("14:00", "Review")),
                "Carla", List.of(new FusionDeAgendas.Evento("16:00", "Retro"))));

        assertEquals(List.of("09:00", "14:00"), fusion.conflictos());
        assertEquals(2, fusion.agendaGlobal().get("09:00").size());
    }

    @Test
    @DisplayName("Sin superposiciones no hay conflictos")
    void sinConflictos() {
        FusionDeAgendas fusion = new FusionDeAgendas(Map.of(
                "Ana", List.of(new FusionDeAgendas.Evento("09:00", "Daily")),
                "Beto", List.of(new FusionDeAgendas.Evento("10:00", "Entrevista"))));

        assertTrue(fusion.conflictos().isEmpty());
        assertEquals(2, fusion.agendaGlobal().size());
    }

    @Test
    @DisplayName("Una misma persona puede generar conflicto consigo misma")
    void conflictoDeUnaSolaPersona() {
        FusionDeAgendas fusion = new FusionDeAgendas(Map.of(
                "Ana", List.of(new FusionDeAgendas.Evento("09:00", "Daily"),
                               new FusionDeAgendas.Evento("09:00", "Médico"))));

        assertEquals(List.of("09:00"), fusion.conflictos());
    }
}
