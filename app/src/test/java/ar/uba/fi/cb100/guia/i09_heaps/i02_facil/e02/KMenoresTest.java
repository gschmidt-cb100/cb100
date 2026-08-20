package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KMenoresTest {

    @Test
    @DisplayName("devuelve los k menores de menor a mayor")
    void devuelveLosKMenores() {
        assertEquals(List.of(10, 20, 30),
                KMenores.kMenores(new int[]{40, 10, 30, 50, 20}, 3));
    }

    @Test
    @DisplayName("si k supera el tamano devuelve todos los elementos")
    void kMayorQueTamanoDevuelveTodos() {
        assertEquals(List.of(1, 5, 8),
                KMenores.kMenores(new int[]{8, 1, 5}, 10));
    }

    @Test
    @DisplayName("k igual a cero devuelve la lista vacia")
    void kCeroDaVacio() {
        assertTrue(KMenores.kMenores(new int[]{3, 1, 2}, 0).isEmpty());
    }

    @Test
    @DisplayName("k negativo lanza IllegalArgumentException")
    void kNegativoFalla() {
        assertThrows(IllegalArgumentException.class,
                () -> KMenores.kMenores(new int[]{1, 2}, -1));
    }

    @Test
    @DisplayName("con repetidos, los duplicados cuentan por separado")
    void conRepetidos() {
        assertEquals(List.of(2, 2, 3),
                KMenores.kMenores(new int[]{5, 2, 3, 2}, 3));
    }
}
