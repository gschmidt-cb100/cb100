package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaRegresivaTest {

    @Test
    @DisplayName("cuenta regresiva de un numero positivo")
    void positivo() {
        assertEquals(List.of(5, 4, 3, 2, 1, 0), CuentaRegresiva.cuentaRegresiva(5));
    }

    @Test
    @DisplayName("caso base: cuenta regresiva de 0 es [0]")
    void desdeCero() {
        assertEquals(List.of(0), CuentaRegresiva.cuentaRegresiva(0));
    }

    @Test
    @DisplayName("cuenta regresiva de 1")
    void desdeUno() {
        assertEquals(List.of(1, 0), CuentaRegresiva.cuentaRegresiva(1));
    }

    @Test
    @DisplayName("n negativo devuelve lista vacia")
    void negativo() {
        assertTrue(CuentaRegresiva.cuentaRegresiva(-3).isEmpty());
    }
}
