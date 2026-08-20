package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorTest {

    @Test
    @DisplayName("Los eventos se procesan por tiempo, no por orden de agenda")
    void ordenPorTiempo() {
        Simulador sim = new Simulador();
        sim.agendar(30, "tercero");
        sim.agendar(10, "primero");
        sim.agendar(20, "segundo");
        assertEquals(List.of("primero", "segundo", "tercero"), sim.correr());
    }

    @Test
    @DisplayName("Un evento inicial agenda dos futuros que se intercalan bien")
    void eventoQueAgendaOtros() {
        Simulador sim = new Simulador();
        sim.agendar(10, "llega cliente", s -> {
            s.agendar(15, "empieza atención");
            s.agendar(25, "termina atención");
        });
        sim.agendar(20, "llega otro cliente");
        assertEquals(
                List.of("llega cliente", "empieza atención",
                        "llega otro cliente", "termina atención"),
                sim.correr());
    }

    @Test
    @DisplayName("Empates de tiempo se resuelven por orden de agendado")
    void empatesEstables() {
        Simulador sim = new Simulador();
        sim.agendar(5, "A");
        sim.agendar(5, "B");
        sim.agendar(5, "C");
        assertEquals(List.of("A", "B", "C"), sim.correr());
    }

    @Test
    @DisplayName("Las acciones pueden encadenarse: un evento agenda otro que agenda otro")
    void cadenaDeAcciones() {
        Simulador sim = new Simulador();
        sim.agendar(1, "uno", s1 ->
                s1.agendar(2, "dos", s2 ->
                        s2.agendar(3, "tres")));
        assertEquals(List.of("uno", "dos", "tres"), sim.correr());
    }

    @Test
    @DisplayName("Con la agenda vacía, correr devuelve un log vacío")
    void agendaVacia() {
        assertEquals(List.of(), new Simulador().correr());
    }
}
