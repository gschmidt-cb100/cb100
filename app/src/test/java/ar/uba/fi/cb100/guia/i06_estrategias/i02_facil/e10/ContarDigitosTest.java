package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ContarDigitosTest {

    @Test
    @DisplayName("caso base: el 0 tiene 1 digito")
    void cero() {
        assertEquals(1, ContarDigitos.cantidadDeDigitos(0));
    }

    @Test
    @DisplayName("numeros de un solo digito")
    void unDigito() {
        assertEquals(1, ContarDigitos.cantidadDeDigitos(7));
        assertEquals(1, ContarDigitos.cantidadDeDigitos(9));
    }

    @Test
    @DisplayName("numeros de varios digitos")
    void variosDigitos() {
        assertEquals(3, ContarDigitos.cantidadDeDigitos(100));
        assertEquals(5, ContarDigitos.cantidadDeDigitos(12345));
    }

    @Test
    @DisplayName("los negativos se cuentan como su valor absoluto")
    void negativos() {
        assertEquals(3, ContarDigitos.cantidadDeDigitos(-908));
        assertEquals(1, ContarDigitos.cantidadDeDigitos(-5));
    }

    @Test
    @DisplayName("numero grande de tipo long")
    void numeroGrande() {
        assertEquals(13, ContarDigitos.cantidadDeDigitos(1234567890123L));
    }
}
