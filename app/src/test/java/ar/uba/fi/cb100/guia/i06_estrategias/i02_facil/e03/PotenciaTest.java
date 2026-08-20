package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class PotenciaTest {

    @Test
    @DisplayName("caso base: cualquier base elevada a 0 es 1")
    void exponenteCero() {
        assertEquals(1L, Potencia.potencia(5, 0));
        assertEquals(1L, Potencia.potencia(0, 0));
    }

    @Test
    @DisplayName("potencias conocidas")
    void potenciasConocidas() {
        assertEquals(1024L, Potencia.potencia(2, 10));
        assertEquals(81L, Potencia.potencia(3, 4));
        assertEquals(1L, Potencia.potencia(1, 100));
    }

    @Test
    @DisplayName("base negativa con exponente impar da resultado negativo")
    void baseNegativa() {
        assertEquals(-8L, Potencia.potencia(-2, 3));
        assertEquals(16L, Potencia.potencia(-2, 4));
    }

    @Test
    @DisplayName("cero elevado a exponente positivo es 0")
    void ceroElevado() {
        assertEquals(0L, Potencia.potencia(0, 5));
    }

    @Test
    @DisplayName("exponente negativo lanza IllegalArgumentException")
    void exponenteNegativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Potencia.potencia(2, -1));
    }
}
