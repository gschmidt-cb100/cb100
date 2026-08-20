package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AnagramasTest {

    @Test
    @DisplayName("Roma y amor son anagramas ignorando mayusculas")
    void romaAmor() {
        assertTrue(Anagramas.sonAnagramas("Roma", "amor"));
    }

    @Test
    @DisplayName("ignora los espacios")
    void ignoraEspacios() {
        assertTrue(Anagramas.sonAnagramas("monja", "jamon "));
    }

    @Test
    @DisplayName("mismas letras pero distintas cantidades no es anagrama")
    void distintasCantidades() {
        assertFalse(Anagramas.sonAnagramas("aab", "abb"));
    }

    @Test
    @DisplayName("palabras distintas no son anagramas")
    void palabrasDistintas() {
        assertFalse(Anagramas.sonAnagramas("gato", "perro"));
    }

    @Test
    @DisplayName("dos cadenas vacias son anagramas")
    void cadenasVacias() {
        assertTrue(Anagramas.sonAnagramas("", "  "));
    }
}
