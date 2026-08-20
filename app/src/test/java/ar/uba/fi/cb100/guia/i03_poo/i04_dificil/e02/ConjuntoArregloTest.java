package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ConjuntoArregloTest {

    @Test
    @DisplayName("Un conjunto nuevo tiene tamaño 0")
    void conjuntoNuevoVacio() {
        ConjuntoArreglo<String> c = new ConjuntoArreglo<>();
        assertEquals(0, c.tamanio());
        assertFalse(c.contiene("x"));
    }

    @Test
    @DisplayName("Ignora los duplicados según equals")
    void ignoraDuplicados() {
        ConjuntoArreglo<String> c = new ConjuntoArreglo<>();
        assertTrue(c.agregar("hola"));
        assertFalse(c.agregar("hola")); // duplicado
        assertFalse(c.agregar(new String("hola"))); // equals, no ==
        assertEquals(1, c.tamanio());
        assertTrue(c.contiene("hola"));
    }

    @Test
    @DisplayName("Agrega múltiples elementos distintos y crece si hace falta")
    void agregaVariosYcrece() {
        ConjuntoArreglo<Integer> c = new ConjuntoArreglo<>();
        for (int i = 0; i < 50; i++) {
            assertTrue(c.agregar(i));
        }
        // reintentar los mismos no aumenta el tamaño
        for (int i = 0; i < 50; i++) {
            assertFalse(c.agregar(i));
        }
        assertEquals(50, c.tamanio());
        assertTrue(c.contiene(0));
        assertTrue(c.contiene(49));
        assertFalse(c.contiene(50));
    }
}
