package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class QuickSelectTest {

    @Test
    @DisplayName("Encuentra mínimo, mediana y máximo")
    void minMedianaMax() {
        int[] a = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        assertEquals(0, QuickSelect.kesimoMenor(a, 1));   // mínimo
        assertEquals(9, QuickSelect.kesimoMenor(a, 10));  // máximo
        assertEquals(4, QuickSelect.kesimoMenor(a, 5));   // 5to menor
    }

    @Test
    @DisplayName("Funciona con repetidos")
    void conRepetidos() {
        int[] a = {5, 1, 5, 1, 5, 3};
        assertEquals(1, QuickSelect.kesimoMenor(a, 1));
        assertEquals(1, QuickSelect.kesimoMenor(a, 2));
        assertEquals(3, QuickSelect.kesimoMenor(a, 3));
        assertEquals(5, QuickSelect.kesimoMenor(a, 6));
    }

    @Test
    @DisplayName("No modifica el arreglo original")
    void noModifica() {
        int[] a = {4, 2, 3, 1};
        QuickSelect.kesimoMenor(a, 2);
        assertArrayEquals(new int[]{4, 2, 3, 1}, a);
    }

    @Test
    @DisplayName("Arreglo de un elemento")
    void unElemento() {
        assertEquals(7, QuickSelect.kesimoMenor(new int[]{7}, 1));
    }

    @Test
    @DisplayName("k fuera de rango y arreglo inválido lanzan excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.kesimoMenor(new int[]{1, 2}, 0));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.kesimoMenor(new int[]{1, 2}, 3));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.kesimoMenor(new int[]{}, 1));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.kesimoMenor(null, 1));
    }
}
