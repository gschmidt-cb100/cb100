package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class ColaTest {

    @Test
    @DisplayName("Comportamiento FIFO: sale primero el que entro primero")
    void comportamientoFifo() {
        Cola<Integer> c = new Cola<>();
        c.encolar(1);
        c.encolar(2);
        c.encolar(3);
        assertEquals(1, c.desencolar());
        assertEquals(2, c.desencolar());
        assertEquals(3, c.desencolar());
        assertTrue(c.estaVacia());
    }

    @Test
    @DisplayName("frente no quita el elemento")
    void frenteNoQuita() {
        Cola<String> c = new Cola<>();
        c.encolar("x");
        c.encolar("y");
        assertEquals("x", c.frente());
        assertEquals("x", c.frente());
        assertEquals(2, c.tamanio());
    }

    @Test
    @DisplayName("Encolar tras vaciar reutiliza correctamente los punteros")
    void encolarTrasVaciar() {
        Cola<Integer> c = new Cola<>();
        c.encolar(1);
        assertEquals(1, c.desencolar());
        assertTrue(c.estaVacia());
        c.encolar(2);
        c.encolar(3);
        assertEquals(2, c.frente());
        assertEquals(2, c.tamanio());
    }

    @Test
    @DisplayName("Operaciones en cola vacia lanzan excepcion")
    void operacionesEnVacio() {
        Cola<Integer> c = new Cola<>();
        assertThrows(NoSuchElementException.class, c::desencolar);
        assertThrows(NoSuchElementException.class, c::frente);
    }
}
