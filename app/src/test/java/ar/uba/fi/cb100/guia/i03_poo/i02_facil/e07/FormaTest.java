package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FormaTest {

    @Test
    @DisplayName("El cuadrado calcula el area como lado al cuadrado")
    void areaCuadrado() {
        Forma f = new Cuadrado(3);
        assertEquals(9.0, f.area(), 1e-9);
    }

    @Test
    @DisplayName("Un lado cero da area cero")
    void areaLadoCero() {
        Forma f = new Cuadrado(0);
        assertEquals(0.0, f.area(), 1e-9);
    }
}
