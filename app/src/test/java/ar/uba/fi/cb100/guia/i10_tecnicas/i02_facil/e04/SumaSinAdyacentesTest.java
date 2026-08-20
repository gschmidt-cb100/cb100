package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SumaSinAdyacentesTest {

    @Test
    @DisplayName("[2, 7, 9, 3, 1] da 12 tomando 2 + 9 + 1")
    void ejemploClasico() {
        assertEquals(12, SumaSinAdyacentes.maximo(new int[]{2, 7, 9, 3, 1}));
    }

    @Test
    @DisplayName("el arreglo vacío da 0")
    void vacioDaCero() {
        assertEquals(0, SumaSinAdyacentes.maximo(new int[]{}));
    }

    @Test
    @DisplayName("con un solo elemento se toma ese elemento")
    void unSoloElemento() {
        assertEquals(5, SumaSinAdyacentes.maximo(new int[]{5}));
    }

    @Test
    @DisplayName("con dos elementos se toma el mayor")
    void dosElementos() {
        assertEquals(7, SumaSinAdyacentes.maximo(new int[]{3, 7}));
    }

    @Test
    @DisplayName("a veces conviene saltear un valor grande: [5, 10, 5, 10, 5] da 20")
    void convieneAlternar() {
        assertEquals(20, SumaSinAdyacentes.maximo(new int[]{5, 10, 5, 10, 5}));
    }
}
