package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContarVocalesTest {

    @Test
    @DisplayName("Cuenta vocales mezclando mayúsculas y minúsculas")
    void cuentaMayusMinus() {
        assertEquals(5, ContarVocales.contarVocales("Murcielago"));
        assertEquals(5, ContarVocales.contarVocales("AEIOU"));
        assertEquals(5, ContarVocales.contarVocales("aeiou"));
    }

    @Test
    @DisplayName("Sin vocales devuelve 0")
    void sinVocales() {
        assertEquals(0, ContarVocales.contarVocales("xyz"));
        assertEquals(0, ContarVocales.contarVocales(""));
    }

    @Test
    @DisplayName("Cadena nula devuelve 0")
    void cadenaNula() {
        assertEquals(0, ContarVocales.contarVocales(null));
    }
}
