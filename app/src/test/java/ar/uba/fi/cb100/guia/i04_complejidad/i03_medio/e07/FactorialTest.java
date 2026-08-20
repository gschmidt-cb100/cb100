package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    @DisplayName("Casos base: 0! y 1! valen 1")
    void casosBase() {
        assertEquals(1L, Factorial.factorial(0));
        assertEquals(1L, Factorial.factorial(1));
    }

    @Test
    @DisplayName("Factoriales tipicos")
    void factorialesTipicos() {
        assertEquals(120L, Factorial.factorial(5));
        assertEquals(3628800L, Factorial.factorial(10));
    }

    @Test
    @DisplayName("Factorial de valor mayor (long)")
    void valorMayor() {
        assertEquals(479001600L, Factorial.factorial(12));
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-100));
    }
}
