package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class PotenciaRapidaTest {

    @Test
    @DisplayName("exponente 0 siempre da 1")
    void exponenteCero() {
        assertEquals(1L, PotenciaRapida.potencia(2, 0));
        assertEquals(1L, PotenciaRapida.potencia(7, 0));
    }

    @Test
    @DisplayName("potencias chicas conocidas")
    void potenciasChicas() {
        assertEquals(2L, PotenciaRapida.potencia(2, 1));
        assertEquals(1024L, PotenciaRapida.potencia(2, 10));
        assertEquals(243L, PotenciaRapida.potencia(3, 5));
        assertEquals(1L, PotenciaRapida.potencia(1, 1000));
    }

    @Test
    @DisplayName("potencias grandes dentro del rango de long")
    void potenciasGrandes() {
        assertEquals(4611686018427387904L, PotenciaRapida.potencia(2, 62));
        assertEquals(1000000000L, PotenciaRapida.potencia(10, 9));
    }

    @Test
    @DisplayName("coincide con multiplicacion iterativa")
    void coincideConIterativo() {
        for (int e = 0; e <= 20; e++) {
            long esperado = 1L;
            for (int i = 0; i < e; i++) {
                esperado *= 3L;
            }
            assertEquals(esperado, PotenciaRapida.potencia(3, e), "fallo para 3^" + e);
        }
    }

    @Test
    @DisplayName("exponente negativo lanza IllegalArgumentException")
    void exponenteNegativo() {
        assertThrows(IllegalArgumentException.class, () -> PotenciaRapida.potencia(2, -1));
    }
}
