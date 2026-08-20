package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("La copia arranca con el mismo estado que el original")
    void copiaTieneMismoEstado() {
        Punto original = new Punto(1, 2);
        Punto copia = new Punto(original);
        assertEquals(1, copia.getX());
        assertEquals(2, copia.getY());
    }

    @Test
    @DisplayName("Modificar la copia no afecta al original")
    void copiaEsIndependiente() {
        Punto original = new Punto(1, 2);
        Punto copia = new Punto(original);
        copia.setX(99);
        assertEquals(1, original.getX());
        assertEquals(99, copia.getX());
    }
}
