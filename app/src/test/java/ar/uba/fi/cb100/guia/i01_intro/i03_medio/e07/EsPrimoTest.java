package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EsPrimoTest {

    @Test
    @DisplayName("Números no primos")
    void noPrimos() {
        assertFalse(EsPrimo.esPrimo(0));
        assertFalse(EsPrimo.esPrimo(1));
        assertFalse(EsPrimo.esPrimo(9));
    }

    @Test
    @DisplayName("Números primos")
    void primos() {
        assertTrue(EsPrimo.esPrimo(2));
        assertTrue(EsPrimo.esPrimo(7));
        assertTrue(EsPrimo.esPrimo(13));
    }

    @Test
    @DisplayName("Casos borde negativos no son primos")
    void negativos() {
        assertFalse(EsPrimo.esPrimo(-7));
    }
}
