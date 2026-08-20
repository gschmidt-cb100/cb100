package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CopiaPrimitivoTest {

    @Test
    @DisplayName("Copiar un primitivo no vincula las variables: a sigue 5, b pasa a 99")
    void copiaPrimitivoIndependiente() {
        int[] resultado = CopiaPrimitivo.valores();
        assertArrayEquals(new int[]{5, 99}, resultado);
    }

    @Test
    @DisplayName("a conserva su valor original luego de modificar b")
    void aNoCambia() {
        int[] resultado = CopiaPrimitivo.valores();
        assertEquals(5, resultado[0]);
    }
}
