package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class SumaHastaNTest {

    @Test
    @DisplayName("caso base: suma(0) = 0")
    void sumaDeCero() {
        assertEquals(0L, SumaHastaN.suma(0));
    }

    @Test
    @DisplayName("suma(1) = 1")
    void sumaDeUno() {
        assertEquals(1L, SumaHastaN.suma(1));
    }

    @Test
    @DisplayName("sumas conocidas usando la formula n(n+1)/2")
    void sumasConocidas() {
        assertEquals(15L, SumaHastaN.suma(5));
        assertEquals(55L, SumaHastaN.suma(10));
        assertEquals(5050L, SumaHastaN.suma(100));
    }

    @Test
    @DisplayName("n negativo devuelve 0")
    void negativoDaCero() {
        assertEquals(0L, SumaHastaN.suma(-7));
    }
}
