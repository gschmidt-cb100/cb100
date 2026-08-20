package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    @DisplayName("Factorial de casos base y general")
    void factoriales() {
        assertEquals(1L, Factorial.factorial(0));
        assertEquals(1L, Factorial.factorial(1));
        assertEquals(120L, Factorial.factorial(5));
        assertEquals(3628800L, Factorial.factorial(10));
    }

    @Test
    @DisplayName("Factorial de negativo lanza IllegalArgumentException")
    void factorialNegativo() {
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-5));
    }
}
