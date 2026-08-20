package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {

    @Test
    @DisplayName("casos base fib(0)=0 y fib(1)=1")
    void casosBase() {
        assertEquals(0L, Fibonacci.fib(0));
        assertEquals(1L, Fibonacci.fib(1));
    }

    @Test
    @DisplayName("primeros valores de la sucesion")
    void primerosValores() {
        assertEquals(1L, Fibonacci.fib(2));
        assertEquals(2L, Fibonacci.fib(3));
        assertEquals(5L, Fibonacci.fib(5));
        assertEquals(55L, Fibonacci.fib(10));
    }

    @Test
    @DisplayName("valor grande fib(50) = 12586269025")
    void valorGrande() {
        assertEquals(12586269025L, Fibonacci.fib(50));
    }

    @Test
    @DisplayName("cumple la recurrencia fib(n) = fib(n-1) + fib(n-2)")
    void cumpleRecurrencia() {
        for (int n = 2; n <= 60; n++) {
            assertEquals(Fibonacci.fib(n - 1) + Fibonacci.fib(n - 2), Fibonacci.fib(n),
                    "fallo la recurrencia en n=" + n);
        }
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    void negativoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Fibonacci.fib(-1));
    }
}
