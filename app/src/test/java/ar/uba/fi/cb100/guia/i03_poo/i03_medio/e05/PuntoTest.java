package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("equals es reflexivo: x.equals(x) es true")
    void reflexivo() {
        Punto x = new Punto(5, 7);
        assertEquals(x, x);
    }

    @Test
    @DisplayName("equals es simétrico: x.equals(y) sii y.equals(x)")
    void simetrico() {
        Punto x = new Punto(1, 2);
        Punto y = new Punto(1, 2);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
    }

    @Test
    @DisplayName("equals es consistente: múltiples llamadas dan el mismo resultado")
    void consistente() {
        Punto x = new Punto(1, 2);
        Punto y = new Punto(1, 2);
        for (int i = 0; i < 5; i++) {
            assertTrue(x.equals(y));
        }
    }

    @Test
    @DisplayName("equals contra null devuelve false")
    void contraNull() {
        Punto x = new Punto(1, 2);
        assertFalse(x.equals(null));
    }

    @Test
    @DisplayName("Puntos distintos no son iguales")
    void distintosNoIguales() {
        assertNotEquals(new Punto(1, 2), new Punto(2, 1));
        assertNotEquals(new Punto(1, 2), new Punto(1, 3));
    }

    @Test
    @DisplayName("Objetos iguales tienen el mismo hashCode")
    void hashCodeCoherenteConEquals() {
        Punto x = new Punto(9, 4);
        Punto y = new Punto(9, 4);
        assertEquals(x, y);
        assertEquals(x.hashCode(), y.hashCode());
    }

    @Test
    @DisplayName("equals contra un objeto de otra clase devuelve false")
    void contraOtraClase() {
        Punto x = new Punto(1, 2);
        assertFalse(x.equals("(1, 2)"));
    }
}
