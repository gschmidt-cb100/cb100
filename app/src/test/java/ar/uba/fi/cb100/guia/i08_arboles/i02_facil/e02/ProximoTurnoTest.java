package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ProximoTurnoTest {

    private TreeMap<LocalTime, String> turnosDeEjemplo() {
        TreeMap<LocalTime, String> turnos = new TreeMap<>();
        turnos.put(LocalTime.of(9, 0), "García");
        turnos.put(LocalTime.of(10, 30), "López");
        turnos.put(LocalTime.of(14, 0), "Suárez");
        return turnos;
    }

    @Test
    @DisplayName("desde las 10:00 el proximo turno es el de las 10:30")
    void proximoIntermedio() {
        assertEquals("López", ProximoTurno.proximoDesde(turnosDeEjemplo(), LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("si la hora coincide exactamente con un turno, devuelve ese turno")
    void horaExacta() {
        assertEquals("Suárez", ProximoTurno.proximoDesde(turnosDeEjemplo(), LocalTime.of(14, 0)));
    }

    @Test
    @DisplayName("si ya pasaron todos los turnos devuelve null")
    void sinTurnosPosteriores() {
        assertNull(ProximoTurno.proximoDesde(turnosDeEjemplo(), LocalTime.of(18, 0)));
    }

    @Test
    @DisplayName("bien temprano devuelve el primer turno del dia")
    void primerTurno() {
        assertEquals("García", ProximoTurno.proximoDesde(turnosDeEjemplo(), LocalTime.of(7, 0)));
    }
}
