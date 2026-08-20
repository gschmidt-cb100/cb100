package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ParImparTest {

    @Test
    @DisplayName("caso base: 0 es par y no es impar")
    void cero() {
        assertTrue(ParImpar.esPar(0));
        assertFalse(ParImpar.esImpar(0));
    }

    @Test
    @DisplayName("numeros pares positivos")
    void paresPositivos() {
        assertTrue(ParImpar.esPar(2));
        assertTrue(ParImpar.esPar(10));
        assertFalse(ParImpar.esImpar(10));
    }

    @Test
    @DisplayName("numeros impares positivos")
    void imparesPositivos() {
        assertTrue(ParImpar.esImpar(3));
        assertTrue(ParImpar.esImpar(7));
        assertFalse(ParImpar.esPar(7));
    }

    @Test
    @DisplayName("funciona con numeros negativos")
    void negativos() {
        assertTrue(ParImpar.esPar(-4));
        assertTrue(ParImpar.esImpar(-5));
        assertFalse(ParImpar.esPar(-5));
    }

    @Test
    @DisplayName("par e impar son siempre opuestos")
    void sonOpuestos() {
        for (int n = -10; n <= 10; n++) {
            assertNotEquals(ParImpar.esPar(n), ParImpar.esImpar(n));
        }
    }
}
