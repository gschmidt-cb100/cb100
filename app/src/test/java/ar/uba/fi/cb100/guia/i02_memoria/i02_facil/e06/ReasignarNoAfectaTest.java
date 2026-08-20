package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ReasignarNoAfectaTest {

    @Test
    @DisplayName("Reasignar el parametro no cambia el arreglo del llamador")
    void reasignacionNoAfectaAfuera() {
        int[] original = {1, 2, 3};
        ReasignarNoAfecta.reasignar(original);
        assertArrayEquals(new int[]{1, 2, 3}, original);
    }
}
