package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    private Agenda agenda;

    @BeforeEach
    void preparar() {
        agenda = new Agenda();
        agenda.agregar("2026-09-15", "Parcial");
        agenda.agregar("2026-08-11", "Clase de streams");
        agenda.agregar("2026-08-25", "Entrega TP");
    }

    @Test
    @DisplayName("el listado sale en orden cronologico aunque se cargue desordenado")
    void listadoCronologico() {
        assertEquals(List.of(
                "2026-08-11: Clase de streams",
                "2026-08-25: Entrega TP",
                "2026-09-15: Parcial"), agenda.listado());
    }

    @Test
    @DisplayName("proximos devuelve solo los eventos del rango, extremos inclusive")
    void rangoInclusive() {
        assertEquals(List.of(
                "2026-08-11: Clase de streams",
                "2026-08-25: Entrega TP"),
                agenda.proximos("2026-08-11", "2026-08-25"));
    }

    @Test
    @DisplayName("un rango sin eventos devuelve lista vacia")
    void rangoVacio() {
        assertTrue(agenda.proximos("2026-10-01", "2026-12-31").isEmpty());
    }

    @Test
    @DisplayName("dos eventos el mismo dia se conservan ambos")
    void mismoDia() {
        agenda.agregar("2026-08-11", "Consultas");
        assertEquals(List.of(
                "2026-08-11: Clase de streams",
                "2026-08-11: Consultas"),
                agenda.proximos("2026-08-11", "2026-08-11"));
    }

    @Test
    @DisplayName("agenda vacia lista vacio")
    void agendaVacia() {
        assertTrue(new Agenda().listado().isEmpty());
    }
}
