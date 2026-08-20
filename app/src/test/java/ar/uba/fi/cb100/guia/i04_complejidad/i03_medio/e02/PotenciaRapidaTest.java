package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PotenciaRapidaTest {

    @Test
    @DisplayName("Calcula potencias tipicas correctamente")
    void potenciasTipicas() {
        assertEquals(1024L, PotenciaRapida.potencia(2, 10));
        assertEquals(16807L, PotenciaRapida.potencia(7, 5));
        assertEquals(1000L, PotenciaRapida.potencia(10, 3));
    }

    @Test
    @DisplayName("Exponente 0 siempre devuelve 1")
    void exponenteCero() {
        assertEquals(1L, PotenciaRapida.potencia(3, 0));
        assertEquals(1L, PotenciaRapida.potencia(0, 0));
    }

    @Test
    @DisplayName("Exponente 1 devuelve la base")
    void exponenteUno() {
        assertEquals(5L, PotenciaRapida.potencia(5, 1));
    }

    @Test
    @DisplayName("Base negativa respeta el signo segun la paridad")
    void baseNegativa() {
        assertEquals(-8L, PotenciaRapida.potencia(-2, 3));
        assertEquals(16L, PotenciaRapida.potencia(-2, 4));
    }

    @Test
    @DisplayName("Exponente negativo lanza IllegalArgumentException")
    void exponenteNegativo() {
        assertThrows(IllegalArgumentException.class, () -> PotenciaRapida.potencia(2, -1));
    }
}
