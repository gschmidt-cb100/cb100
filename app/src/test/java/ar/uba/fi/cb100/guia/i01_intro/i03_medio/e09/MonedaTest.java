package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MonedaTest {

    @Test
    @DisplayName("Cada moneda expone su símbolo")
    void simbolos() {
        assertEquals("US$", Moneda.DOLAR.simbolo());
        assertEquals("$", Moneda.PESO.simbolo());
        assertEquals("€", Moneda.EURO.simbolo());
    }

    @Test
    @DisplayName("El enum tiene exactamente tres valores")
    void cantidadValores() {
        assertEquals(3, Moneda.values().length);
        assertNotNull(Moneda.valueOf("EURO"));
    }
}
