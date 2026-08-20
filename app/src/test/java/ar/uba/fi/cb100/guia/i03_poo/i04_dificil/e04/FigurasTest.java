package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class FigurasTest {

    private static final double DELTA = 1e-9;

    @Test
    @DisplayName("El círculo calcula área y perímetro correctamente")
    void circuloCalcula() {
        Circulo c = new Circulo(2);
        assertEquals(Math.PI * 4, c.area(), DELTA);
        assertEquals(Math.PI * 4, c.perimetro(), DELTA);
    }

    @Test
    @DisplayName("El rectángulo calcula área y perímetro correctamente")
    void rectanguloCalcula() {
        Rectangulo r = new Rectangulo(2, 3);
        assertEquals(6, r.area(), DELTA);
        assertEquals(10, r.perimetro(), DELTA);
    }

    @Test
    @DisplayName("Polimorfismo: se opera sobre figuras a través de la interfaz")
    void polimorfismo() {
        Figura f = new Circulo(1);
        assertEquals(Math.PI, f.area(), DELTA);
        assertTrue(f instanceof FiguraBase);
    }

    @Test
    @DisplayName("Arrays.sort ordena las figuras por área ascendente")
    void ordenaPorArea() {
        Figura[] figuras = {
                new Rectangulo(5, 5),  // 25
                new Circulo(1),        // ~3.14
                new Rectangulo(2, 3),  // 6
                new Circulo(2)         // ~12.57
        };
        Arrays.sort(figuras);

        for (int i = 0; i < figuras.length - 1; i++) {
            assertTrue(figuras[i].area() <= figuras[i + 1].area(),
                    "La figura en " + i + " debería tener área menor o igual a la siguiente");
        }
        // la primera es la de menor área (círculo de radio 1)
        assertTrue(figuras[0] instanceof Circulo);
        // la última es la de mayor área (rectángulo 5x5)
        assertEquals(25, figuras[figuras.length - 1].area(), DELTA);
    }

    @Test
    @DisplayName("Rechaza dimensiones negativas")
    void rechazaNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new Circulo(-1));
        assertThrows(IllegalArgumentException.class, () -> new Rectangulo(-1, 2));
    }
}
