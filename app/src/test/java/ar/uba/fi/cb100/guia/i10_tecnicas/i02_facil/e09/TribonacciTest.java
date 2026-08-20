package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TribonacciTest {

    @Test
    @DisplayName("los casos base son t(0)=0, t(1)=1, t(2)=1")
    void casosBase() {
        assertEquals(0, Tribonacci.tribonacci(0));
        assertEquals(1, Tribonacci.tribonacci(1));
        assertEquals(1, Tribonacci.tribonacci(2));
    }

    @Test
    @DisplayName("t(4) = 4")
    void terminoCuatro() {
        assertEquals(4, Tribonacci.tribonacci(4));
    }

    @Test
    @DisplayName("t(25) = 1389537")
    void terminoVeinticinco() {
        assertEquals(1_389_537L, Tribonacci.tribonacci(25));
    }

    @Test
    @DisplayName("cada término es la suma de los tres anteriores")
    void cumpleLaRecurrencia() {
        for (int n = 3; n <= 20; n++) {
            assertEquals(Tribonacci.tribonacci(n - 1)
                            + Tribonacci.tribonacci(n - 2)
                            + Tribonacci.tribonacci(n - 3),
                    Tribonacci.tribonacci(n),
                    "falla la recurrencia en n = " + n);
        }
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Tribonacci.tribonacci(-1));
    }
}
