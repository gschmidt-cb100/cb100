package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("dos Punto(1,2) son equals")
    void puntosIgualesSonEquals() {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        assertEquals(a, b);
    }

    @Test
    @DisplayName("dos Punto(1,2) comparten hashCode")
    void puntosIgualesMismoHashCode() {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("Punto(1,2) no es equals a Punto(3,4)")
    void puntosDistintosNoSonEquals() {
        Punto a = new Punto(1, 2);
        Punto c = new Punto(3, 4);
        assertNotEquals(a, c);
    }

    @Test
    @DisplayName("un Punto no es equals a null ni a otro tipo")
    void puntoNoEsEqualsANullNiOtroTipo() {
        Punto a = new Punto(1, 2);
        assertNotEquals(a, null);
        assertNotEquals(a, "Punto(1, 2)");
    }
}
