package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MayorDeDosTest {

    @Test
    @DisplayName("Devuelve el mayor cuando son distintos")
    void distintos() {
        assertEquals(8, MayorDeDos.mayor(3, 8));
        assertEquals(8, MayorDeDos.mayor(8, 3));
    }

    @Test
    @DisplayName("Devuelve el valor cuando son iguales")
    void iguales() {
        assertEquals(10, MayorDeDos.mayor(10, 10));
        assertEquals(-5, MayorDeDos.mayor(-5, -5));
    }
}
