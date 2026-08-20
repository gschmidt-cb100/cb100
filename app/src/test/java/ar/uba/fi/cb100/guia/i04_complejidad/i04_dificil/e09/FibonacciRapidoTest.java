package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FibonacciRapidoTest {

    /** Fibonacci iterativo O(n) usado como oráculo en los tests. */
    private static long fibIterativo(int n) {
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            long s = a + b;
            a = b;
            b = s;
        }
        return a;
    }

    @Test
    @DisplayName("Valores puntuales conocidos")
    void valoresConocidos() {
        assertEquals(0, FibonacciRapido.fib(0));
        assertEquals(1, FibonacciRapido.fib(1));
        assertEquals(1, FibonacciRapido.fib(2));
        assertEquals(55, FibonacciRapido.fib(10));
        assertEquals(6765, FibonacciRapido.fib(20));
    }

    @Test
    @DisplayName("Coincide con el fibonacci iterativo hasta 92")
    void coincideConIterativo() {
        for (int n = 0; n <= 92; n++) {
            assertEquals(fibIterativo(n), FibonacciRapido.fib(n), "falló en n=" + n);
        }
    }

    @Test
    @DisplayName("F(92) es el mayor que entra en long")
    void limiteLong() {
        assertEquals(7540113804746346429L, FibonacciRapido.fib(92));
    }

    @Test
    @DisplayName("n negativo o mayor a 92 lanzan excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> FibonacciRapido.fib(-1));
        assertThrows(IllegalArgumentException.class, () -> FibonacciRapido.fib(93));
    }
}
