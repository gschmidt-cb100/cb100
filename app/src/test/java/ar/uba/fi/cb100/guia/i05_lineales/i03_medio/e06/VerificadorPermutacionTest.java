package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificadorPermutacionTest {

    @Test
    @DisplayName("Mismos elementos en distinto orden son permutación")
    void mismosElementosDistintoOrden() {
        assertTrue(VerificadorPermutacion.esPermutacion(
                List.of(1, 2, 3), List.of(3, 1, 2)));
    }

    @Test
    @DisplayName("Distinta cantidad no es permutación")
    void distintaCantidad() {
        assertFalse(VerificadorPermutacion.esPermutacion(
                List.of(1, 2, 3), List.of(1, 2)));
    }

    @Test
    @DisplayName("Distinta multiplicidad no es permutación")
    void distintaMultiplicidad() {
        assertFalse(VerificadorPermutacion.esPermutacion(
                List.of(1, 1, 2), List.of(1, 2, 2)));
    }

    @Test
    @DisplayName("Dos listas vacías son permutación")
    void ambasVacias() {
        assertTrue(VerificadorPermutacion.esPermutacion(List.of(), List.of()));
    }

    @Test
    @DisplayName("Lista consigo misma es permutación")
    void mismaLista() {
        List<Integer> l = List.of(5, 4, 3, 2, 1);
        assertTrue(VerificadorPermutacion.esPermutacion(l, l));
    }
}
