package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DiferenciaConjuntosTest {

    @Test
    @DisplayName("Diferencia clásica con superposición parcial")
    void diferenciaParcial() {
        Set<Integer> a = Set.of(1, 2, 3, 4);
        Set<Integer> b = Set.of(3, 4, 5);
        assertEquals(Set.of(1, 2), DiferenciaConjuntos.diferencia(a, b));
    }

    @Test
    @DisplayName("Sin elementos comunes devuelve A completo")
    void sinComunes() {
        Set<Integer> a = Set.of(1, 2);
        Set<Integer> b = Set.of(3, 4);
        assertEquals(Set.of(1, 2), DiferenciaConjuntos.diferencia(a, b));
    }

    @Test
    @DisplayName("B contiene a A devuelve conjunto vacío")
    void bContieneA() {
        Set<Integer> a = Set.of(1, 2);
        Set<Integer> b = Set.of(1, 2, 3);
        assertTrue(DiferenciaConjuntos.diferencia(a, b).isEmpty());
    }

    @Test
    @DisplayName("No modifica el conjunto original A")
    void noModificaOriginal() {
        Set<Integer> a = new HashSet<>(Set.of(1, 2, 3));
        DiferenciaConjuntos.diferencia(a, Set.of(1));
        assertEquals(Set.of(1, 2, 3), a);
    }

    @Test
    @DisplayName("A vacío devuelve conjunto vacío")
    void aVacio() {
        assertTrue(DiferenciaConjuntos.diferencia(Set.of(), Set.of(1, 2)).isEmpty());
    }
}
