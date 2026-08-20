package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FrecuenciasTest {

    @Test
    @DisplayName("cuenta bien las apariciones de banana")
    void cuentaBanana() {
        Map<Character, Integer> f = Frecuencias.frecuencias("banana");
        assertEquals(1, f.get('b'));
        assertEquals(3, f.get('a'));
        assertEquals(2, f.get('n'));
        assertEquals(3, f.size());
    }

    @Test
    @DisplayName("cadena vacia devuelve mapa vacio")
    void cadenaVacia() {
        assertTrue(Frecuencias.frecuencias("").isEmpty());
    }

    @Test
    @DisplayName("distingue mayusculas de minusculas")
    void distingueMayusculas() {
        Map<Character, Integer> f = Frecuencias.frecuencias("Aa");
        assertEquals(1, f.get('A'));
        assertEquals(1, f.get('a'));
    }

    @Test
    @DisplayName("cuenta tambien los espacios")
    void cuentaEspacios() {
        Map<Character, Integer> f = Frecuencias.frecuencias("a a a");
        assertEquals(3, f.get('a'));
        assertEquals(2, f.get(' '));
    }
}
