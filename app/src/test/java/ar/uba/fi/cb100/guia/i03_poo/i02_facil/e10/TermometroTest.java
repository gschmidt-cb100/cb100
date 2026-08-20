package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TermometroTest {

    @Test
    @DisplayName("Un valor valido se acepta y se puede leer")
    void aceptaValorValido() {
        Termometro t = new Termometro(25.0);
        assertEquals(25.0, t.getTemperatura(), 1e-9);
        t.setTemperatura(-273.15);
        assertEquals(-273.15, t.getTemperatura(), 1e-9);
    }

    @Test
    @DisplayName("Rechaza temperaturas por debajo del cero absoluto")
    void rechazaBajoCeroAbsoluto() {
        assertThrows(IllegalArgumentException.class, () -> new Termometro(-300));
        Termometro t = new Termometro(0);
        assertThrows(IllegalArgumentException.class, () -> t.setTemperatura(-273.16));
    }
}
