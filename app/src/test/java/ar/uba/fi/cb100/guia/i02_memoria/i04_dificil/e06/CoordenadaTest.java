package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CoordenadaTest {

    @Test
    @DisplayName("Reflexivo: un objeto es igual a si mismo")
    void reflexivo() {
        Coordenada c = new Coordenada(1, 2, 3);
        assertEquals(c, c);
    }

    @Test
    @DisplayName("Simetrico: a.equals(b) implica b.equals(a)")
    void simetrico() {
        Coordenada a = new Coordenada(1, 2, 3);
        Coordenada b = new Coordenada(1, 2, 3);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    @DisplayName("Consistente: repetir equals da siempre el mismo resultado")
    void consistente() {
        Coordenada a = new Coordenada(1, 2, 3);
        Coordenada b = new Coordenada(1, 2, 3);
        assertTrue(a.equals(b));
        assertTrue(a.equals(b));
        assertTrue(a.equals(b));
    }

    @Test
    @DisplayName("Contra null: equals(null) es false")
    void contraNull() {
        Coordenada c = new Coordenada(1, 2, 3);
        assertFalse(c.equals(null));
    }

    @Test
    @DisplayName("Distintas coordenadas no son iguales")
    void distintasNoIguales() {
        Coordenada a = new Coordenada(1, 2, 3);
        Coordenada d = new Coordenada(3, 2, 1);
        assertNotEquals(a, d);
    }

    @Test
    @DisplayName("Objetos iguales tienen el mismo hashCode")
    void mismoHashCode() {
        Coordenada a = new Coordenada(1, 2, 3);
        Coordenada b = new Coordenada(1, 2, 3);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
