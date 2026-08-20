package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PalindromoTest {

    @Test
    @DisplayName("palabra palindroma devuelve true")
    void esPalindromo() {
        assertTrue(Palindromo.esPalindromo(List.of('n', 'e', 'u', 'q', 'u', 'e', 'n')));
    }

    @Test
    @DisplayName("palabra no palindroma devuelve false")
    void noEsPalindromo() {
        assertFalse(Palindromo.esPalindromo(List.of('c', 'a', 's', 'a')));
    }

    @Test
    @DisplayName("lista vacia es palindromo")
    void listaVacia() {
        assertTrue(Palindromo.esPalindromo(List.of()));
    }

    @Test
    @DisplayName("un solo caracter es palindromo")
    void unCaracter() {
        assertTrue(Palindromo.esPalindromo(List.of('x')));
    }

    @Test
    @DisplayName("palindromo de longitud par")
    void longitudPar() {
        assertTrue(Palindromo.esPalindromo(List.of('a', 'b', 'b', 'a')));
    }
}
