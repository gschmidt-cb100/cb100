package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ParTest {

    @Test
    @DisplayName("Los accesores devuelven los componentes")
    void accesores() {
        Par<String, Integer> p = new Par<>("clave", 7);
        assertEquals("clave", p.primero());
        assertEquals(7, p.segundo());
    }

    @Test
    @DisplayName("equals y hashCode son consistentes para pares iguales")
    void equalsYHashCode() {
        Par<String, Integer> a = new Par<>("x", 1);
        Par<String, Integer> b = new Par<>("x", 1);
        Par<String, Integer> c = new Par<>("x", 2);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    @DisplayName("toString incluye ambos componentes")
    void toStringIncluyeComponentes() {
        Par<String, Integer> p = new Par<>("edad", 30);
        String s = p.toString();
        assertTrue(s.contains("edad"));
        assertTrue(s.contains("30"));
    }

    @Test
    @DisplayName("Admite componentes nulos")
    void admiteNulos() {
        Par<String, String> p = new Par<>(null, "b");
        assertNull(p.primero());
        assertEquals("b", p.segundo());
        assertEquals(new Par<>(null, "b"), p);
    }
}
