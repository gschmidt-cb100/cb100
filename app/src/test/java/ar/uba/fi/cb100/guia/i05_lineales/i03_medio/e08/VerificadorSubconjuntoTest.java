package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VerificadorSubconjuntoTest {

    @Test
    @DisplayName("Subconjunto propio devuelve true")
    void subconjuntoPropio() {
        assertTrue(VerificadorSubconjunto.esSubconjunto(
                Set.of(1, 2), Set.of(1, 2, 3)));
    }

    @Test
    @DisplayName("Conjunto igual es subconjunto (⊆ no estricto)")
    void conjuntoIgual() {
        assertTrue(VerificadorSubconjunto.esSubconjunto(
                Set.of(1, 2, 3), Set.of(1, 2, 3)));
    }

    @Test
    @DisplayName("Elemento fuera de b hace que no sea subconjunto")
    void noSubconjunto() {
        assertFalse(VerificadorSubconjunto.esSubconjunto(
                Set.of(1, 4), Set.of(1, 2, 3)));
    }

    @Test
    @DisplayName("El conjunto vacío es subconjunto de cualquiera")
    void vacioEsSubconjunto() {
        assertTrue(VerificadorSubconjunto.esSubconjunto(Set.of(), Set.of(1, 2)));
        assertTrue(VerificadorSubconjunto.esSubconjunto(Set.of(), Set.of()));
    }

    @Test
    @DisplayName("Un conjunto no vacío no es subconjunto del vacío")
    void noVacioSobreVacio() {
        assertFalse(VerificadorSubconjunto.esSubconjunto(Set.of(1), Set.of()));
    }
}
