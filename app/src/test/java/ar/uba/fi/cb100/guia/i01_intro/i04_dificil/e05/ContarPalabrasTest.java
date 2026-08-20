package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 05 - Contar palabras. */
class ContarPalabrasTest {

    @Test
    @DisplayName("Cuenta con espacios multiples y en los extremos")
    void espaciosMultiples() {
        assertEquals(2, ContarPalabras.contarPalabras("  hola   mundo  "));
        assertEquals(3, ContarPalabras.contarPalabras("uno dos tres"));
    }

    @Test
    @DisplayName("Cadena vacia y solo espacios")
    void casosBorde() {
        assertEquals(0, ContarPalabras.contarPalabras(""));
        assertEquals(0, ContarPalabras.contarPalabras("     "));
    }

    @Test
    @DisplayName("Una sola palabra")
    void unaPalabra() {
        assertEquals(1, ContarPalabras.contarPalabras("java"));
        assertEquals(1, ContarPalabras.contarPalabras("   java   "));
    }

    @Test
    @DisplayName("Cadena null lanza excepcion")
    void cadenaNull() {
        assertThrows(IllegalArgumentException.class, () -> ContarPalabras.contarPalabras(null));
    }
}
