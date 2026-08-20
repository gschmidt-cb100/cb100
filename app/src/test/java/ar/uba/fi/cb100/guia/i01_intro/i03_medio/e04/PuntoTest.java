package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("Distancia (0,0)->(3,4) es 5")
    void distanciaTresCuatroCinco() {
        var origen = new Punto(0, 0);
        var p = new Punto(3, 4);
        assertEquals(5.0, origen.distanciaA(p), 1e-9);
    }

    @Test
    @DisplayName("Distancia a sí mismo es 0 y es simétrica")
    void distanciaSimetricaYCero() {
        var a = new Punto(1, 1);
        var b = new Punto(4, 5);
        assertEquals(0.0, a.distanciaA(a), 1e-9);
        assertEquals(b.distanciaA(a), a.distanciaA(b), 1e-9);
    }

    @Test
    @DisplayName("Componentes del record accesibles")
    void componentes() {
        var p = new Punto(3, 4);
        assertEquals(3.0, p.x(), 1e-9);
        assertEquals(4.0, p.y(), 1e-9);
    }
}
