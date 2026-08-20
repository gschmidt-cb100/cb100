package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TurnosConPrioridadTest {

    @Test
    @DisplayName("un jubilado que llega tarde pasa antes que un no-jubilado que llego temprano")
    void jubiladoTardioPasaPrimero() {
        TurnosConPrioridad turnos = new TurnosConPrioridad();
        turnos.sacarTurno("Ana", false);
        turnos.sacarTurno("Clara", true);
        assertEquals("Clara", turnos.atender());
        assertEquals("Ana", turnos.atender());
    }

    @Test
    @DisplayName("entre jubilados se respeta el orden de llegada")
    void entreJubiladosEsFifo() {
        TurnosConPrioridad turnos = new TurnosConPrioridad();
        turnos.sacarTurno("Delia", true);
        turnos.sacarTurno("Elsa", true);
        turnos.sacarTurno("Fermin", true);
        assertEquals("Delia", turnos.atender());
        assertEquals("Elsa", turnos.atender());
        assertEquals("Fermin", turnos.atender());
    }

    @Test
    @DisplayName("entre no-jubilados tambien se respeta el orden de llegada")
    void entreNoJubiladosEsFifo() {
        TurnosConPrioridad turnos = new TurnosConPrioridad();
        turnos.sacarTurno("Gaston", false);
        turnos.sacarTurno("Hilda", false);
        assertEquals("Gaston", turnos.atender());
        assertEquals("Hilda", turnos.atender());
    }

    @Test
    @DisplayName("atender sin nadie en espera lanza NoSuchElementException")
    void atenderVacioFalla() {
        assertThrows(NoSuchElementException.class,
                () -> new TurnosConPrioridad().atender());
    }

    @Test
    @DisplayName("enEspera cuenta a los que faltan atender")
    void enEsperaCuentaBien() {
        TurnosConPrioridad turnos = new TurnosConPrioridad();
        turnos.sacarTurno("Ivan", false);
        turnos.sacarTurno("Julia", true);
        assertEquals(2, turnos.enEspera());
        turnos.atender();
        assertEquals(1, turnos.enEspera());
    }
}
