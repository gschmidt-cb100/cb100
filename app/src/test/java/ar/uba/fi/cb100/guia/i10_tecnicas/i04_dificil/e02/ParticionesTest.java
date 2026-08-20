package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticionesTest {

    private final Particiones calculadora = new Particiones();

    @Test
    @DisplayName("p(5) = 7: las siete particiones de 5")
    void particionesDeCinco() {
        // 5, 4+1, 3+2, 3+1+1, 2+2+1, 2+1+1+1, 1+1+1+1+1
        assertEquals(7, calculadora.particiones(5));
    }

    @Test
    @DisplayName("p(10) = 42")
    void particionesDeDiez() {
        assertEquals(42, calculadora.particiones(10));
    }

    @Test
    @DisplayName("p(0) = 1: la particion vacia")
    void particionesDeCero() {
        assertEquals(1, calculadora.particiones(0));
    }

    @Test
    @DisplayName("Primeros valores de la sucesion: 1, 1, 2, 3, 5, 7, 11, 15, 22, 30")
    void primerosValores() {
        int[] esperados = {1, 1, 2, 3, 5, 7, 11, 15, 22, 30};
        for (int n = 0; n < esperados.length; n++) {
            assertEquals(esperados[n], calculadora.particiones(n), "p(" + n + ")");
        }
    }

    @Test
    @DisplayName("Un valor grande conocido: p(50) = 204226")
    void valorGrande() {
        assertEquals(204226, calculadora.particiones(50));
    }

    @Test
    @DisplayName("Un n negativo lanza IllegalArgumentException")
    void negativoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.particiones(-1));
    }
}
