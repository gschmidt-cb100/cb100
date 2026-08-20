package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PotenciaDeDosTest {

    @Test
    @DisplayName("El 1 es potencia de 2 (2^0)")
    void unoEsPotencia() {
        assertTrue(PotenciaDeDos.esPotenciaDe2(1));
    }

    @Test
    @DisplayName("Potencias de 2 validas")
    void potenciasValidas() {
        assertTrue(PotenciaDeDos.esPotenciaDe2(2));
        assertTrue(PotenciaDeDos.esPotenciaDe2(16));
        assertTrue(PotenciaDeDos.esPotenciaDe2(1024));
    }

    @Test
    @DisplayName("Numeros que no son potencia de 2")
    void noPotencias() {
        assertFalse(PotenciaDeDos.esPotenciaDe2(3));
        assertFalse(PotenciaDeDos.esPotenciaDe2(12));
    }

    @Test
    @DisplayName("El 0 no es potencia de 2")
    void cero() {
        assertFalse(PotenciaDeDos.esPotenciaDe2(0));
    }

    @Test
    @DisplayName("Los negativos no son potencia de 2")
    void negativos() {
        assertFalse(PotenciaDeDos.esPotenciaDe2(-8));
    }
}
