package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class PilaTest {

    @Test
    @DisplayName("Comportamiento LIFO: sale primero el ultimo que entro")
    void comportamientoLifo() {
        Pila<Integer> p = new Pila<>();
        p.apilar(1);
        p.apilar(2);
        p.apilar(3);
        assertEquals(3, p.desapilar());
        assertEquals(2, p.desapilar());
        assertEquals(1, p.desapilar());
        assertTrue(p.estaVacia());
    }

    @Test
    @DisplayName("tope no quita el elemento")
    void topeNoQuita() {
        Pila<String> p = new Pila<>();
        p.apilar("x");
        assertEquals("x", p.tope());
        assertEquals("x", p.tope());
        assertEquals(1, p.tamanio());
    }

    @Test
    @DisplayName("estaVacia y tamanio reflejan el estado")
    void estadoVacia() {
        Pila<Integer> p = new Pila<>();
        assertTrue(p.estaVacia());
        assertEquals(0, p.tamanio());
        p.apilar(5);
        assertFalse(p.estaVacia());
        assertEquals(1, p.tamanio());
    }

    @Test
    @DisplayName("Desapilar o tope en pila vacia lanza excepcion")
    void operacionesEnVacio() {
        Pila<Integer> p = new Pila<>();
        assertThrows(NoSuchElementException.class, p::desapilar);
        assertThrows(NoSuchElementException.class, p::tope);
    }
}
