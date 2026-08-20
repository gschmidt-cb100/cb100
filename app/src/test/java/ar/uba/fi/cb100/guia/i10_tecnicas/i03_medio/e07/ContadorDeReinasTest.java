package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContadorDeReinasTest {

    @Test
    @DisplayName("Con n=4 hay exactamente 2 soluciones")
    void cuatroReinas() {
        assertEquals(2, ContadorDeReinas.contar(4));
    }

    @Test
    @DisplayName("Con n=6 hay exactamente 4 soluciones")
    void seisReinas() {
        assertEquals(4, ContadorDeReinas.contar(6));
    }

    @Test
    @DisplayName("El clásico: con n=8 hay 92 soluciones")
    void ochoReinas() {
        assertEquals(92, ContadorDeReinas.contar(8));
    }

    @Test
    @DisplayName("Casos sin solución: n=2 y n=3 dan 0")
    void tablerosChicosSinSolucion() {
        assertEquals(0, ContadorDeReinas.contar(2));
        assertEquals(0, ContadorDeReinas.contar(3));
    }

    @Test
    @DisplayName("Con n=1 la única solución es la reina sola")
    void unaReina() {
        assertEquals(1, ContadorDeReinas.contar(1));
    }

    @Test
    @DisplayName("n=0 no es un tablero válido y lanza IllegalArgumentException")
    void nInvalido() {
        assertThrows(IllegalArgumentException.class, () -> ContadorDeReinas.contar(0));
    }
}
