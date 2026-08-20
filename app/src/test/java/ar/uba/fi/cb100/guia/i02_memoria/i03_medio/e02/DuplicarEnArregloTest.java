package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DuplicarEnArregloTest {

    @Test
    @DisplayName("duplicar modifica el arreglo de afuera in place")
    void duplicaInPlace() {
        int[] v = {1, 2, 3, 4};
        DuplicarEnArreglo.duplicar(v);
        assertArrayEquals(new int[]{2, 4, 6, 8}, v);
    }

    @Test
    @DisplayName("duplicar sobre arreglo vacio no falla")
    void duplicaArregloVacio() {
        int[] v = {};
        DuplicarEnArreglo.duplicar(v);
        assertArrayEquals(new int[]{}, v);
    }

    @Test
    @DisplayName("duplicar maneja ceros y negativos")
    void duplicaCerosYNegativos() {
        int[] v = {0, -3, 5};
        DuplicarEnArreglo.duplicar(v);
        assertArrayEquals(new int[]{0, -6, 10}, v);
    }
}
