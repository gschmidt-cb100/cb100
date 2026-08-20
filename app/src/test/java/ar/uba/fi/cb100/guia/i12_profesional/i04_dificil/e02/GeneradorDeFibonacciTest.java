package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneradorDeFibonacciTest {

    @Test
    @DisplayName("los primeros 10 son 0,1,1,2,3,5,8,13,21,34")
    public void primerosDiez() {
        assertEquals(List.of(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L),
                GeneradorDeFibonacci.primeros(10));
    }

    @Test
    @DisplayName("F(90) = 2880067194370816120")
    public void fibonacciNoventa() {
        assertEquals(2880067194370816120L, GeneradorDeFibonacci.fibonacci(90));
        assertEquals(2880067194370816120L, GeneradorDeFibonacci.primeros(91).get(90));
    }

    @Test
    @DisplayName("casos borde: n=0 lista vacia, n=1 solo el cero, F(0) y F(1)")
    public void casosBorde() {
        assertTrue(GeneradorDeFibonacci.primeros(0).isEmpty());
        assertEquals(List.of(0L), GeneradorDeFibonacci.primeros(1));
        assertEquals(0L, GeneradorDeFibonacci.fibonacci(0));
        assertEquals(1L, GeneradorDeFibonacci.fibonacci(1));
        assertThrows(IllegalArgumentException.class, () -> GeneradorDeFibonacci.primeros(-1));
    }

    @Test
    @DisplayName("cada termino es la suma de los dos anteriores")
    public void invarianteDeLaSucesion() {
        List<Long> fib = GeneradorDeFibonacci.primeros(50);
        for (int i = 2; i < fib.size(); i++) {
            assertEquals(fib.get(i - 2) + fib.get(i - 1), fib.get(i), "posicion " + i);
        }
    }
}
