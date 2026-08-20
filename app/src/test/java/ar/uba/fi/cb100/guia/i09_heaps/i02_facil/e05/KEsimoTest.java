package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KEsimoTest {

    @Test
    @DisplayName("k igual a 1 devuelve el minimo")
    void kUnoEsElMinimo() {
        assertEquals(10, KEsimo.kEsimo(new int[]{50, 20, 40, 10, 30}, 1));
    }

    @Test
    @DisplayName("k intermedio devuelve el k-esimo menor")
    void kIntermedio() {
        assertEquals(30, KEsimo.kEsimo(new int[]{50, 20, 40, 10, 30}, 3));
    }

    @Test
    @DisplayName("k igual al tamano devuelve el maximo")
    void kIgualAlTamanoEsElMaximo() {
        assertEquals(50, KEsimo.kEsimo(new int[]{50, 20, 40, 10, 30}, 5));
    }

    @Test
    @DisplayName("con repetidos, cada copia cuenta como una posicion")
    void conRepetidos() {
        assertEquals(7, KEsimo.kEsimo(new int[]{7, 3, 7, 1}, 3));
    }

    @Test
    @DisplayName("k fuera de rango lanza IllegalArgumentException")
    void kFueraDeRangoFalla() {
        assertThrows(IllegalArgumentException.class,
                () -> KEsimo.kEsimo(new int[]{1, 2, 3}, 0));
        assertThrows(IllegalArgumentException.class,
                () -> KEsimo.kEsimo(new int[]{1, 2, 3}, 4));
    }
}
