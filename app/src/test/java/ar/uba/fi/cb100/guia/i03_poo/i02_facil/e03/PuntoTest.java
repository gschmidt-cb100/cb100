package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    @DisplayName("Dos puntos iguales cumplen equals y comparten hashCode")
    void puntosIgualesEqualsYHashCode() {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("Puntos con distintas coordenadas no son iguales")
    void puntosDistintosNoSonIguales() {
        Punto a = new Punto(1, 2);
        Punto c = new Punto(9, 9);
        assertNotEquals(a, c);
    }
}
