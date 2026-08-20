package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ConteoIteracionesTest {

    @Test
    @DisplayName("n = 1 -> 0 vueltas")
    void nUno() {
        assertEquals(0, ConteoIteraciones.iteraciones(1));
    }

    @Test
    @DisplayName("n = 8 -> 3 vueltas (i = 1, 2, 4)")
    void nOcho() {
        assertEquals(3, ConteoIteraciones.iteraciones(8));
    }

    @Test
    @DisplayName("n = 16 -> 4 vueltas (i = 1, 2, 4, 8)")
    void nDieciseis() {
        assertEquals(4, ConteoIteraciones.iteraciones(16));
    }

    @Test
    @DisplayName("n = 0 y negativos -> 0 vueltas")
    void nCeroONegativo() {
        assertEquals(0, ConteoIteraciones.iteraciones(0));
        assertEquals(0, ConteoIteraciones.iteraciones(-5));
    }

    @Test
    @DisplayName("Potencia exacta: n = 10 -> 4 vueltas (i = 1, 2, 4, 8)")
    void nDiez() {
        assertEquals(4, ConteoIteraciones.iteraciones(10));
    }
}
