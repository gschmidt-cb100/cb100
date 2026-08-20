package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MaximoTest {

    @Test
    @DisplayName("Máximo de un arreglo con varios elementos")
    void maximoVariosElementos() {
        assertEquals(9, Maximo.maximo(new int[]{3, 9, 1, 7, 2}));
        assertEquals(7, Maximo.maximo(new int[]{-5, 0, 7, 3}));
        assertEquals(-1, Maximo.maximo(new int[]{-5, -1, -9}));
    }

    @Test
    @DisplayName("Máximo de un arreglo con un solo elemento")
    void maximoUnElemento() {
        assertEquals(42, Maximo.maximo(new int[]{42}));
        assertEquals(0, Maximo.maximo(new int[]{0}));
    }

    @Test
    @DisplayName("Arreglo vacío o nulo lanza IllegalArgumentException")
    void maximoArregloVacio() {
        assertThrows(IllegalArgumentException.class, () -> Maximo.maximo(new int[]{}));
        assertThrows(IllegalArgumentException.class, () -> Maximo.maximo(null));
    }
}
