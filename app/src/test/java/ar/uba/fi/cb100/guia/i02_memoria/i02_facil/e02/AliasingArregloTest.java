package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AliasingArregloTest {

    @Test
    @DisplayName("Modificar a través de un alias cambia el arreglo original")
    void aliasModificaOriginal() {
        int[] original = {1, 2, 3};
        int[] devuelto = AliasingArreglo.conAlias(original);
        assertArrayEquals(new int[]{99, 2, 3}, original);
        // el valor devuelto es el MISMO objeto que el original
        assertSame(original, devuelto);
    }

    @Test
    @DisplayName("Arreglo de un solo elemento tambien queda modificado")
    void aliasArregloUnitario() {
        int[] original = {7};
        AliasingArreglo.conAlias(original);
        assertArrayEquals(new int[]{99}, original);
    }
}
