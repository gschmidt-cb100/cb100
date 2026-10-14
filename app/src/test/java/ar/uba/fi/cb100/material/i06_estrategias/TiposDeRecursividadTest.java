package ar.uba.fi.cb100.material.i06_estrategias;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TiposDeRecursividadTest {

    @Test
    @DisplayName("Múltiple: en un pasillo hay un solo camino")
    void caminosEnPasillo() {
        assertEquals(1, TiposDeRecursividad.caminosEnGrilla(1, 5));
        assertEquals(1, TiposDeRecursividad.caminosEnGrilla(7, 1));
    }

    @Test
    @DisplayName("Múltiple: grillas chicas conocidas (2x2=2, 3x3=6)")
    void caminosConocidos() {
        assertEquals(2, TiposDeRecursividad.caminosEnGrilla(2, 2));
        assertEquals(6, TiposDeRecursividad.caminosEnGrilla(3, 3));
        assertEquals(10, TiposDeRecursividad.caminosEnGrilla(3, 4));
    }

    @Test
    @DisplayName("Múltiple: grilla inválida lanza excepción")
    void grillaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> TiposDeRecursividad.caminosEnGrilla(0, 3));
    }

    @Test
    @DisplayName("Indirecta: esPar y esImpar se llaman mutuamente y aciertan")
    void paridad() {
        assertTrue(TiposDeRecursividad.esPar(0));
        assertFalse(TiposDeRecursividad.esImpar(0));
        assertTrue(TiposDeRecursividad.esPar(10));
        assertFalse(TiposDeRecursividad.esPar(7));
        assertTrue(TiposDeRecursividad.esImpar(7));
    }

    @Test
    @DisplayName("Indirecta: negativo lanza excepción")
    void paridadNegativa() {
        assertThrows(IllegalArgumentException.class, () -> TiposDeRecursividad.esPar(-1));
    }

    @Test
    @DisplayName("Anidada: la función 91 devuelve 91 para todo n <= 100")
    void funcion91Abajo() {
        for (int n = -20; n <= 100; n += 10) {
            assertEquals(91, TiposDeRecursividad.mcCarthy91(n));
        }
    }

    @Test
    @DisplayName("Anidada: para n > 100 devuelve n - 10")
    void funcion91Arriba() {
        assertEquals(91, TiposDeRecursividad.mcCarthy91(101));
        assertEquals(140, TiposDeRecursividad.mcCarthy91(150));
    }
}
