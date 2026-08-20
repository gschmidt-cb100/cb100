package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DosQueSumanTest {

    @Test
    @DisplayName("Encuentra el par que suma el objetivo")
    void encuentraPar() {
        assertArrayEquals(new int[] {0, 1}, DosQueSuman.dosQueSuman(new int[] {2, 7, 11, 15}, 9));
    }

    @Test
    @DisplayName("Funciona con elementos repetidos: 3 + 3 = 6")
    void repetidos() {
        assertArrayEquals(new int[] {0, 1}, DosQueSuman.dosQueSuman(new int[] {3, 3}, 6));
    }

    @Test
    @DisplayName("El par puede estar en cualquier parte del arreglo")
    void parEnElMedio() {
        int[] resultado = DosQueSuman.dosQueSuman(new int[] {1, 4, 6, 8}, 14);
        assertArrayEquals(new int[] {2, 3}, resultado);
    }

    @Test
    @DisplayName("Si no hay par devuelve {-1, -1}")
    void sinPar() {
        assertArrayEquals(new int[] {-1, -1}, DosQueSuman.dosQueSuman(new int[] {1, 2, 3}, 100));
    }

    @Test
    @DisplayName("No usa dos veces el mismo elemento: {5} y objetivo 10 no tiene par")
    void noReusaElMismoElemento() {
        assertArrayEquals(new int[] {-1, -1}, DosQueSuman.dosQueSuman(new int[] {5}, 10));
    }
}
