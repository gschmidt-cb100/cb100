package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class SumaArregloTest {

    @Test
    @DisplayName("caso base: arreglo vacio suma 0")
    void arregloVacio() {
        assertEquals(0, SumaArreglo.sumar(new int[0]));
    }

    @Test
    @DisplayName("arreglo de un elemento")
    void unElemento() {
        assertEquals(42, SumaArreglo.sumar(new int[]{42}));
    }

    @Test
    @DisplayName("arreglo de varios elementos")
    void variosElementos() {
        assertEquals(24, SumaArreglo.sumar(new int[]{3, 9, 1, 7, 4}));
    }

    @Test
    @DisplayName("arreglo con negativos")
    void conNegativos() {
        assertEquals(-2, SumaArreglo.sumar(new int[]{-5, 3, -1, 1}));
    }
}
