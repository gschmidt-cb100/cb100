package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SumaDeSubconjuntoTest {

    @Test
    @DisplayName("En {3,34,4,12,5,2} existe subconjunto que suma 9")
    void existeNueve() {
        assertTrue(SumaDeSubconjunto.existe(new int[] {3, 34, 4, 12, 5, 2}, 9));
    }

    @Test
    @DisplayName("En {3,34,4,12,5,2} no existe subconjunto que suma 30")
    void noExisteTreinta() {
        assertFalse(SumaDeSubconjunto.existe(new int[] {3, 34, 4, 12, 5, 2}, 30));
    }

    @Test
    @DisplayName("Objetivo 0 siempre es alcanzable con el subconjunto vacío")
    void objetivoCero() {
        assertTrue(SumaDeSubconjunto.existe(new int[] {3, 5}, 0));
        assertTrue(SumaDeSubconjunto.existe(new int[] {}, 0));
    }

    @Test
    @DisplayName("Cada elemento se usa a lo sumo una vez: con {5} no se llega a 10")
    void sinRepetirElementos() {
        assertFalse(SumaDeSubconjunto.existe(new int[] {5}, 10));
        assertTrue(SumaDeSubconjunto.existe(new int[] {5, 5}, 10));
    }

    @Test
    @DisplayName("Conjunto vacío: solo alcanza el objetivo 0")
    void conjuntoVacio() {
        assertFalse(SumaDeSubconjunto.existe(new int[] {}, 7));
    }

    @Test
    @DisplayName("La suma de todos los elementos siempre es alcanzable")
    void sumaTotal() {
        assertTrue(SumaDeSubconjunto.existe(new int[] {3, 34, 4, 12, 5, 2}, 60));
    }
}
