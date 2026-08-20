package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MaximoArregloTest {

    @Test
    @DisplayName("caso base: arreglo de un elemento")
    void unElemento() {
        assertEquals(7, MaximoArreglo.maximo(new int[]{7}));
    }

    @Test
    @DisplayName("maximo de varios elementos")
    void variosElementos() {
        assertEquals(9, MaximoArreglo.maximo(new int[]{3, 9, 1, 7, 4}));
    }

    @Test
    @DisplayName("el maximo puede estar al final")
    void maximoAlFinal() {
        assertEquals(100, MaximoArreglo.maximo(new int[]{1, 2, 3, 100}));
    }

    @Test
    @DisplayName("maximo con numeros negativos")
    void conNegativos() {
        assertEquals(-1, MaximoArreglo.maximo(new int[]{-5, -1, -3, -8}));
    }

    @Test
    @DisplayName("arreglo vacio lanza IllegalArgumentException")
    void arregloVacioLanza() {
        assertThrows(IllegalArgumentException.class, () -> MaximoArreglo.maximo(new int[0]));
    }
}
