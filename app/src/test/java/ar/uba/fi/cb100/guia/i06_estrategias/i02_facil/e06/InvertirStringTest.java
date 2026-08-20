package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class InvertirStringTest {

    @Test
    @DisplayName("caso base: la cadena vacia invertida es vacia")
    void cadenaVacia() {
        assertEquals("", InvertirString.invertir(""));
    }

    @Test
    @DisplayName("un caracter queda igual")
    void unCaracter() {
        assertEquals("a", InvertirString.invertir("a"));
    }

    @Test
    @DisplayName("invierte una palabra")
    void palabra() {
        assertEquals("aloh", InvertirString.invertir("hola"));
        assertEquals("001BC", InvertirString.invertir("CB100"));
    }

    @Test
    @DisplayName("un palindromo invertido es igual al original")
    void palindromo() {
        assertEquals("neuquen", InvertirString.invertir("neuquen"));
    }
}
