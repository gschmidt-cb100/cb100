package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PotenciaLentaTest {

    @Test
    @DisplayName("2^10 = 1024")
    void dosALaDiez() {
        assertEquals(1024L, PotenciaLenta.potenciaLenta(2, 10));
    }

    @Test
    @DisplayName("Exponente 0 devuelve 1")
    void exponenteCero() {
        assertEquals(1L, PotenciaLenta.potenciaLenta(5, 0));
    }

    @Test
    @DisplayName("Exponente 1 devuelve la base")
    void exponenteUno() {
        assertEquals(7L, PotenciaLenta.potenciaLenta(7, 1));
    }

    @Test
    @DisplayName("Base negativa con exponente impar da resultado negativo")
    void baseNegativa() {
        assertEquals(-8L, PotenciaLenta.potenciaLenta(-2, 3));
    }

    @Test
    @DisplayName("Exponente negativo lanza excepcion")
    void exponenteNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> PotenciaLenta.potenciaLenta(2, -1));
    }
}
