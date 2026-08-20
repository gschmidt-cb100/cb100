package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiferenciaDeInventariosTest {

    @Test
    @DisplayName("Calcula hoy menos ayer para los productos que cambiaron")
    void calculaDeltas() {
        Map<String, Integer> cambios = DiferenciaDeInventarios.cambios(
                Map.of("yerba", 10, "café", 3),
                Map.of("yerba", 7, "café", 8));

        assertEquals(Map.of("yerba", -3, "café", 5), cambios);
    }

    @Test
    @DisplayName("Los productos sin cambios no aparecen en el resultado")
    void omiteSinCambios() {
        Map<String, Integer> cambios = DiferenciaDeInventarios.cambios(
                Map.of("azúcar", 5),
                Map.of("azúcar", 5));

        assertTrue(cambios.isEmpty());
    }

    @Test
    @DisplayName("Un producto nuevo (no estaba ayer) aparece con delta positivo")
    void productoNuevo() {
        Map<String, Integer> cambios = DiferenciaDeInventarios.cambios(
                Map.of(),
                Map.of("galletitas", 12));

        assertEquals(Map.of("galletitas", 12), cambios);
    }

    @Test
    @DisplayName("Un producto dado de baja (no está hoy) aparece con delta negativo")
    void productoDadoDeBaja() {
        Map<String, Integer> cambios = DiferenciaDeInventarios.cambios(
                Map.of("café", 3),
                Map.of());

        assertEquals(Map.of("café", -3), cambios);
    }

    @Test
    @DisplayName("Con ambos inventarios vacíos devuelve un mapa vacío")
    void ambosVacios() {
        assertTrue(DiferenciaDeInventarios.cambios(Map.of(), Map.of()).isEmpty());
    }
}
