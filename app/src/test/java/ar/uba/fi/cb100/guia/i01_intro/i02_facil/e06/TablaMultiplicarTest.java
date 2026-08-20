package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TablaMultiplicarTest {

    @Test
    @DisplayName("La tabla del 5 tiene 10 elementos con primer y ultimo multiplo")
    void tablaDelCinco() {
        var t = TablaMultiplicar.tabla(5);
        assertEquals(10, t.length);
        assertEquals(5, t[0]);
        assertEquals(50, t[9]);
    }

    @Test
    @DisplayName("La tabla del 3 en posiciones intermedias")
    void tablaDelTres() {
        var t = TablaMultiplicar.tabla(3);
        assertEquals(3, t[0]);
        assertEquals(15, t[4]);
        assertEquals(30, t[9]);
    }
}
