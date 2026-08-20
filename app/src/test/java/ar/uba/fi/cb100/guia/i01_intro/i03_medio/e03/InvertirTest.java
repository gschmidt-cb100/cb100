package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class InvertirTest {

    @Test
    @DisplayName("Invierte un arreglo de varios elementos")
    void invierteVarios() {
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, Invertir.invertir(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{2, 1}, Invertir.invertir(new int[]{1, 2}));
    }

    @Test
    @DisplayName("Casos borde: vacío y un elemento; no modifica el original")
    void casosBorde() {
        assertArrayEquals(new int[]{}, Invertir.invertir(new int[]{}));
        assertArrayEquals(new int[]{7}, Invertir.invertir(new int[]{7}));

        int[] original = {1, 2, 3};
        Invertir.invertir(original);
        assertArrayEquals(new int[]{1, 2, 3}, original);
    }

    @Test
    @DisplayName("Arreglo nulo lanza IllegalArgumentException")
    void arregloNulo() {
        assertThrows(IllegalArgumentException.class, () -> Invertir.invertir(null));
    }
}
