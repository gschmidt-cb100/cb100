package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AgendaDeTareasTest {

    @Test
    @DisplayName("proxima devuelve la tarea de fecha mas cercana sin sacarla")
    void proximaNoSaca() {
        AgendaDeTareas agenda = new AgendaDeTareas();
        agenda.agregar("TP", LocalDate.of(2026, 8, 20));
        agenda.agregar("Parcial", LocalDate.of(2026, 8, 15));
        assertEquals("Parcial", agenda.proxima().descripcion());
        // Sigue estando: proxima() usa peek, no poll.
        assertEquals(2, agenda.pendientes());
    }

    @Test
    @DisplayName("completarProxima saca las tareas en orden de vencimiento")
    void completarSacaEnOrden() {
        AgendaDeTareas agenda = new AgendaDeTareas();
        agenda.agregar("SUBE", LocalDate.of(2026, 9, 1));
        agenda.agregar("Parcial", LocalDate.of(2026, 8, 15));
        agenda.agregar("TP", LocalDate.of(2026, 8, 20));
        assertEquals("Parcial", agenda.completarProxima().descripcion());
        assertEquals("TP", agenda.completarProxima().descripcion());
        assertEquals("SUBE", agenda.completarProxima().descripcion());
    }

    @Test
    @DisplayName("completar una tarea reduce las pendientes")
    void completarReducePendientes() {
        AgendaDeTareas agenda = new AgendaDeTareas();
        agenda.agregar("A", LocalDate.of(2026, 1, 1));
        agenda.agregar("B", LocalDate.of(2026, 2, 1));
        agenda.completarProxima();
        assertEquals(1, agenda.pendientes());
    }

    @Test
    @DisplayName("proxima y completarProxima con agenda vacia lanzan NoSuchElementException")
    void agendaVaciaFalla() {
        AgendaDeTareas agenda = new AgendaDeTareas();
        assertThrows(NoSuchElementException.class, agenda::proxima);
        assertThrows(NoSuchElementException.class, agenda::completarProxima);
    }
}
