package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    @DisplayName("caso base: 0! = 1")
    void factorialDeCero() {
        assertEquals(1L, Factorial.factorial(0));
    }

    @Test
    @DisplayName("factorial de 1 es 1")
    void factorialDeUno() {
        assertEquals(1L, Factorial.factorial(1));
    }

    @Test
    @DisplayName("factoriales conocidos")
    void factorialesConocidos() {
        assertEquals(120L, Factorial.factorial(5));
        assertEquals(720L, Factorial.factorial(6));
        assertEquals(3628800L, Factorial.factorial(10));
    }

    @Test
    @DisplayName("factorial grande dentro del rango de long")
    void factorialGrande() {
        assertEquals(2432902008176640000L, Factorial.factorial(20));
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
    }
}
