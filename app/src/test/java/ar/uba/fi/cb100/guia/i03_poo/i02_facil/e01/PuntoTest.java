package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("Los getters devuelven las coordenadas pasadas al constructor")
    void gettersDevuelvenCoordenadas() {
        Punto p = new Punto(3, 4);
        assertEquals(3, p.getX());
        assertEquals(4, p.getY());
    }

    @Test
    @DisplayName("Admite coordenadas negativas")
    void admiteCoordenadasNegativas() {
        Punto p = new Punto(-1, -8);
        assertEquals(-1, p.getX());
        assertEquals(-8, p.getY());
    }
}
