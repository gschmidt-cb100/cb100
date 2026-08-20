package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PalindromoMasLargoTest {

    private final PalindromoMasLargo buscador = new PalindromoMasLargo();

    @Test
    @DisplayName("En 'babad' la respuesta es 'bab' o 'aba' (ambas de largo 3)")
    void babad() {
        String resultado = buscador.masLarga("babad");
        assertTrue(List.of("bab", "aba").contains(resultado),
                "Se esperaba bab o aba, vino: " + resultado);
    }

    @Test
    @DisplayName("En 'cbbd' la respuesta es 'bb' (palindromo de largo par)")
    void cbbd() {
        assertEquals("bb", buscador.masLarga("cbbd"));
    }

    @Test
    @DisplayName("Un solo caracter es su propio palindromo")
    void unCaracter() {
        assertEquals("x", buscador.masLarga("x"));
    }

    @Test
    @DisplayName("Sin letras repetidas devuelve un caracter (el primero)")
    void sinRepetidas() {
        assertEquals("a", buscador.masLarga("abcdef"));
    }

    @Test
    @DisplayName("Una palabra palindroma entera se devuelve completa")
    void palabraEntera() {
        assertEquals("neuquen", buscador.masLarga("neuquen"));
        assertEquals("reconocer", buscador.masLarga("xreconocerz"));
    }

    @Test
    @DisplayName("La cadena vacia devuelve vacio y null lanza IllegalArgumentException")
    void bordes() {
        assertEquals("", buscador.masLarga(""));
        assertThrows(IllegalArgumentException.class, () -> buscador.masLarga(null));
    }
}
