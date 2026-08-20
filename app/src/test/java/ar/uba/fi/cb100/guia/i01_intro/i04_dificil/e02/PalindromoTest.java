package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 02 - Palindromo. */
class PalindromoTest {

    @Test
    @DisplayName("Frase palindroma ignorando mayusculas y espacios")
    void frasePalindroma() {
        assertTrue(Palindromo.esPalindromo("Anita lava la tina"));
        assertTrue(Palindromo.esPalindromo("Somos o no somos"));
    }

    @Test
    @DisplayName("Texto no palindromo")
    void noPalindromo() {
        assertFalse(Palindromo.esPalindromo("hola"));
        assertFalse(Palindromo.esPalindromo("java"));
    }

    @Test
    @DisplayName("Casos borde: cadena vacia y un solo caracter")
    void casosBorde() {
        assertTrue(Palindromo.esPalindromo(""));
        assertTrue(Palindromo.esPalindromo("a"));
    }

    @Test
    @DisplayName("Cadena null lanza excepcion")
    void cadenaNull() {
        assertThrows(IllegalArgumentException.class, () -> Palindromo.esPalindromo(null));
    }
}
