package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContadorDePalabrasTest {

    @Test
    @DisplayName("cuenta las apariciones de cada palabra")
    void cuentaApariciones() {
        Map<String, Long> f = ContadorDePalabras.frecuencias(
                List.of("que", "sera", "sera", "lo", "que", "sera"));
        assertEquals(3L, f.get("sera"));
        assertEquals(2L, f.get("que"));
        assertEquals(1L, f.get("lo"));
        assertEquals(3, f.size());
    }

    @Test
    @DisplayName("una palabra que no aparece no tiene entrada en el mapa")
    void palabraAusente() {
        Map<String, Long> f = ContadorDePalabras.frecuencias(List.of("hola", "hola"));
        assertNull(f.get("chau"));
    }

    @Test
    @DisplayName("lista vacia produce mapa vacio")
    void listaVacia() {
        assertTrue(ContadorDePalabras.frecuencias(List.of()).isEmpty());
    }

    @Test
    @DisplayName("distingue mayusculas de minusculas (no normaliza)")
    void distingueMayusculas() {
        Map<String, Long> f = ContadorDePalabras.frecuencias(List.of("Hola", "hola"));
        assertEquals(1L, f.get("Hola"));
        assertEquals(1L, f.get("hola"));
    }
}
