package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LargoStringTest {

    @Test
    @DisplayName("caso base: la cadena vacia tiene largo 0")
    void cadenaVacia() {
        assertEquals(0, LargoString.largo(""));
    }

    @Test
    @DisplayName("cadena de un caracter")
    void unCaracter() {
        assertEquals(1, LargoString.largo("a"));
    }

    @Test
    @DisplayName("palabras de distinto largo")
    void palabras() {
        assertEquals(4, LargoString.largo("hola"));
        assertEquals(10, LargoString.largo("algoritmos"));
    }

    @Test
    @DisplayName("cuenta espacios y coincide con length()")
    void coincideConLength() {
        String frase = "division y conquista";
        assertEquals(frase.length(), LargoString.largo(frase));
    }
}
