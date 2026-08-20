package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ColaTest {

    @Test
    @DisplayName("Una cola recién creada está vacía")
    void colaNuevaEstaVacia() {
        Cola<Integer> cola = new Cola<>();
        assertTrue(cola.estaVacia());
        assertEquals(0, cola.tamanio());
    }

    @Test
    @DisplayName("Respeta el orden FIFO: sale primero el primero encolado")
    void ordenFifo() {
        Cola<Integer> cola = new Cola<>();
        cola.encolar(1);
        cola.encolar(2);
        cola.encolar(3);

        assertEquals(3, cola.tamanio());
        assertEquals(1, cola.desencolar());
        assertEquals(2, cola.desencolar());
        assertEquals(3, cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    @DisplayName("frente() devuelve el primero encolado sin quitarlo")
    void frenteNoQuita() {
        Cola<String> cola = new Cola<>();
        cola.encolar("a");
        cola.encolar("b");

        assertEquals("a", cola.frente());
        assertEquals(2, cola.tamanio());
    }

    @Test
    @DisplayName("desencolar() sobre cola vacía lanza excepción")
    void desencolarVaciaLanza() {
        Cola<Integer> cola = new Cola<>();
        assertThrows(NoSuchElementException.class, cola::desencolar);
    }

    @Test
    @DisplayName("frente() sobre cola vacía lanza excepción")
    void frenteVaciaLanza() {
        Cola<Integer> cola = new Cola<>();
        assertThrows(NoSuchElementException.class, cola::frente);
    }
}
