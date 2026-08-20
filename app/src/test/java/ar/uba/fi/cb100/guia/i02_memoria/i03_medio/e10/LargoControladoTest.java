package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class LargoControladoTest {

    @Test
    @DisplayName("largo(null) lanza NullPointerException")
    void largoConNullLanzaNpe() {
        assertThrows(NullPointerException.class, () -> LargoControlado.largo(null));
    }

    @Test
    @DisplayName("largo devuelve el largo de un texto no nulo")
    void largoConTexto() {
        assertEquals(4, LargoControlado.largo("hola"));
    }

    @Test
    @DisplayName("largoSeguro(null) devuelve 0")
    void largoSeguroConNull() {
        assertEquals(0, LargoControlado.largoSeguro(null));
    }

    @Test
    @DisplayName("largoSeguro devuelve el largo de un texto no nulo")
    void largoSeguroConTexto() {
        assertEquals(5, LargoControlado.largoSeguro("mundo"));
    }
}
