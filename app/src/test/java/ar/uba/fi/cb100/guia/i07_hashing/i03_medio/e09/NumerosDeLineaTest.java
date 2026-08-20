package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NumerosDeLineaTest {

    @Test
    @DisplayName("Registra los índices de línea (desde 0) de cada palabra buscada")
    void indicesBasicos() {
        Map<String, List<Integer>> resultado = NumerosDeLinea.ocurrencias(
                List.of("el mate está listo", "no hay agua", "el agua está fría"),
                Set.of("el", "agua"));

        assertEquals(List.of(0, 2), resultado.get("el"));
        assertEquals(List.of(1, 2), resultado.get("agua"));
    }

    @Test
    @DisplayName("Ignora las palabras que no están entre las buscadas")
    void ignoraNoBuscadas() {
        Map<String, List<Integer>> resultado = NumerosDeLinea.ocurrencias(
                List.of("uno dos tres"),
                Set.of("dos"));

        assertEquals(1, resultado.size());
        assertEquals(List.of(0), resultado.get("dos"));
    }

    @Test
    @DisplayName("Una palabra repetida en la misma línea registra el índice una sola vez")
    void noDuplicaIndices() {
        Map<String, List<Integer>> resultado = NumerosDeLinea.ocurrencias(
                List.of("agua agua agua"),
                Set.of("agua"));

        assertEquals(List.of(0), resultado.get("agua"));
    }

    @Test
    @DisplayName("Una palabra buscada que no aparece no figura en el resultado")
    void buscadaAusente() {
        Map<String, List<Integer>> resultado = NumerosDeLinea.ocurrencias(
                List.of("hola mundo"),
                Set.of("chau"));

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Con lista de líneas vacía devuelve un mapa vacío")
    void sinLineas() {
        assertTrue(NumerosDeLinea.ocurrencias(List.of(), Set.of("algo")).isEmpty());
    }
}
