package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 01 - FizzBuzz. */
class FizzBuzzTest {

    @Test
    @DisplayName("Multiplos de 3 devuelven Fizz")
    void multiploDeTres() {
        assertEquals("Fizz", FizzBuzz.fizzbuzz(3));
        assertEquals("Fizz", FizzBuzz.fizzbuzz(9));
    }

    @Test
    @DisplayName("Multiplos de 5 devuelven Buzz")
    void multiploDeCinco() {
        assertEquals("Buzz", FizzBuzz.fizzbuzz(5));
        assertEquals("Buzz", FizzBuzz.fizzbuzz(20));
    }

    @Test
    @DisplayName("Multiplos de 3 y 5 devuelven FizzBuzz")
    void multiploDeAmbos() {
        assertEquals("FizzBuzz", FizzBuzz.fizzbuzz(15));
        assertEquals("FizzBuzz", FizzBuzz.fizzbuzz(30));
    }

    @Test
    @DisplayName("Otros numeros se devuelven como texto")
    void numeroComoTexto() {
        assertEquals("7", FizzBuzz.fizzbuzz(7));
        assertEquals("1", FizzBuzz.fizzbuzz(1));
    }
}
