package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class IntercambioEnArregloTest {

    @Test
    @DisplayName("swap(int, int) no afecta a las variables de afuera")
    void swapDePrimitivosNoTieneEfecto() {
        int a = 1;
        int b = 2;
        IntercambioEnArreglo.swap(a, b);
        assertEquals(1, a);
        assertEquals(2, b);
    }

    @Test
    @DisplayName("swap(int[], i, j) intercambia los elementos del arreglo")
    void swapSobreArregloIntercambia() {
        int[] v = {10, 20, 30};
        IntercambioEnArreglo.swap(v, 0, 2);
        assertArrayEquals(new int[]{30, 20, 10}, v);
    }

    @Test
    @DisplayName("swap con i == j deja el arreglo igual")
    void swapMismaPosicion() {
        int[] v = {5, 6, 7};
        IntercambioEnArreglo.swap(v, 1, 1);
        assertArrayEquals(new int[]{5, 6, 7}, v);
    }
}
