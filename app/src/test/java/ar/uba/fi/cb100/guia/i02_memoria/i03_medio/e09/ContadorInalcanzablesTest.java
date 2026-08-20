package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContadorInalcanzablesTest {

    @Test
    @DisplayName("cuenta los false del arreglo")
    void cuentaLosFalse() {
        boolean[] alcanzable = {true, false, true, false, false};
        assertEquals(3, ContadorInalcanzables.recolectables(alcanzable));
    }

    @Test
    @DisplayName("todos alcanzables devuelve cero")
    void todosAlcanzables() {
        boolean[] alcanzable = {true, true, true};
        assertEquals(0, ContadorInalcanzables.recolectables(alcanzable));
    }

    @Test
    @DisplayName("arreglo vacio devuelve cero")
    void arregloVacio() {
        assertEquals(0, ContadorInalcanzables.recolectables(new boolean[]{}));
    }

    @Test
    @DisplayName("todos inalcanzables devuelve el largo")
    void todosInalcanzables() {
        assertEquals(4, ContadorInalcanzables.recolectables(new boolean[]{false, false, false, false}));
    }
}
