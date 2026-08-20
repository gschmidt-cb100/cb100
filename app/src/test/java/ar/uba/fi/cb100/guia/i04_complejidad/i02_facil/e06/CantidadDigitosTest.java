package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CantidadDigitosTest {

    @Test
    @DisplayName("El cero tiene 1 digito")
    void cero() {
        assertEquals(1, CantidadDigitos.cantidadDigitos(0));
    }

    @Test
    @DisplayName("Numero de varios digitos")
    void variosDigitos() {
        assertEquals(5, CantidadDigitos.cantidadDigitos(12345));
    }

    @Test
    @DisplayName("Numero negativo cuenta digitos sin el signo")
    void negativo() {
        assertEquals(2, CantidadDigitos.cantidadDigitos(-99));
    }

    @Test
    @DisplayName("Un solo digito")
    void unSoloDigito() {
        assertEquals(1, CantidadDigitos.cantidadDigitos(7));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE no desborda al tomar valor absoluto")
    void minValue() {
        assertEquals(10, CantidadDigitos.cantidadDigitos(Integer.MIN_VALUE));
    }
}
