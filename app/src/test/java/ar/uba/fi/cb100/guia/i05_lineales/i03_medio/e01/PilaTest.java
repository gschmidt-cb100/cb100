package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PilaTest {

    @Test
    @DisplayName("Una pila recién creada está vacía y tiene tamaño 0")
    void pilaNuevaEstaVacia() {
        Pila<Integer> pila = new Pila<>();
        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamanio());
    }

    @Test
    @DisplayName("Respeta el orden LIFO: sale primero el último apilado")
    void ordenLifo() {
        Pila<Integer> pila = new Pila<>();
        pila.apilar(1);
        pila.apilar(2);
        pila.apilar(3);

        assertEquals(3, pila.tamanio());
        assertEquals(3, pila.desapilar());
        assertEquals(2, pila.desapilar());
        assertEquals(1, pila.desapilar());
        assertTrue(pila.estaVacia());
    }

    @Test
    @DisplayName("tope() devuelve el último apilado sin quitarlo")
    void topeNoQuita() {
        Pila<String> pila = new Pila<>();
        pila.apilar("a");
        pila.apilar("b");

        assertEquals("b", pila.tope());
        assertEquals(2, pila.tamanio());
    }

    @Test
    @DisplayName("desapilar() sobre pila vacía lanza excepción")
    void desapilarVaciaLanza() {
        Pila<Integer> pila = new Pila<>();
        assertThrows(NoSuchElementException.class, pila::desapilar);
    }

    @Test
    @DisplayName("tope() sobre pila vacía lanza excepción")
    void topeVaciaLanza() {
        Pila<Integer> pila = new Pila<>();
        assertThrows(NoSuchElementException.class, pila::tope);
    }
}
