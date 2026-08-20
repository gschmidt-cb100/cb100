package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 06 - Fibonacci iterativo. */
class FibonacciTest {

    @Test
    @DisplayName("Casos base fib(0) y fib(1)")
    void casosBase() {
        assertEquals(0L, Fibonacci.fibonacci(0));
        assertEquals(1L, Fibonacci.fibonacci(1));
    }

    @Test
    @DisplayName("Valores intermedios y grandes")
    void valoresIntermedios() {
        assertEquals(55L, Fibonacci.fibonacci(10));
        assertEquals(6765L, Fibonacci.fibonacci(20));
    }

    @Test
    @DisplayName("Indice negativo lanza excepcion")
    void indiceNegativo() {
        assertThrows(IllegalArgumentException.class, () -> Fibonacci.fibonacci(-1));
    }
}
