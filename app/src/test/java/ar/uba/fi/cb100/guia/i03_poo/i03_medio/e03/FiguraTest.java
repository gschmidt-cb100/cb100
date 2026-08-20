package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FiguraTest {

    private static final double DELTA = 1e-9;

    @Test
    @DisplayName("El círculo calcula su área con pi * r^2")
    void areaCirculo() {
        Figura c = new Circulo(2.0);
        assertEquals(Math.PI * 4.0, c.area(), DELTA);
    }

    @Test
    @DisplayName("El rectángulo calcula su área con base * altura")
    void areaRectangulo() {
        Figura r = new Rectangulo(3.0, 4.0);
        assertEquals(12.0, r.area(), DELTA);
    }

    @Test
    @DisplayName("sumaAreas recorre polimórficamente el arreglo de figuras")
    void sumaAreasPolimorfica() {
        Figura[] figuras = {
                new Rectangulo(2.0, 5.0), // 10
                new Circulo(1.0),         // pi
                new Rectangulo(1.0, 1.0)  // 1
        };
        double esperado = 10.0 + Math.PI + 1.0;
        assertEquals(esperado, Figura.sumaAreas(figuras), DELTA);
    }

    @Test
    @DisplayName("sumaAreas de un arreglo vacío es 0")
    void sumaAreasVacio() {
        assertEquals(0.0, Figura.sumaAreas(new Figura[0]), DELTA);
    }

    @Test
    @DisplayName("Crear una figura con dimensiones negativas lanza excepción")
    void dimensionesNegativas() {
        assertThrows(IllegalArgumentException.class, () -> new Circulo(-1.0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangulo(-1.0, 2.0));
    }
}
