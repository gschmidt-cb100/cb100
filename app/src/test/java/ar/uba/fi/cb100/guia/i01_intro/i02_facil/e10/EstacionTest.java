package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EstacionTest {

    @Test
    @DisplayName("Estaciones calidas")
    void calidas() {
        assertTrue(Estacion.PRIMAVERA.esCalida());
        assertTrue(Estacion.VERANO.esCalida());
    }

    @Test
    @DisplayName("Estaciones frias")
    void frias() {
        assertFalse(Estacion.OTONO.esCalida());
        assertFalse(Estacion.INVIERNO.esCalida());
    }
}
