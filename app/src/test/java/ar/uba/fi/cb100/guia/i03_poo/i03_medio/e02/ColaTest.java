package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("El orden de desencolado es FIFO (primero en entrar, primero en salir)")
    void ordenFifo() {
        Cola<Integer> cola = new Cola<>();
        cola.encolar(10);
        cola.encolar(20);
        cola.encolar(30);

        assertEquals(3, cola.tamanio());
        assertEquals(10, cola.desencolar());
        assertEquals(20, cola.desencolar());
        assertEquals(30, cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    @DisplayName("frente() devuelve el próximo a salir sin quitarlo")
    void frenteNoQuita() {
        Cola<String> cola = new Cola<>();
        cola.encolar("a");
        cola.encolar("b");

        assertEquals("a", cola.frente());
        assertEquals(2, cola.tamanio());
    }

    @Test
    @DisplayName("Encolar y desencolar intercalado mantiene el orden FIFO con arreglo circular")
    void intercaladoCircular() {
        Cola<Integer> cola = new Cola<>();
        cola.encolar(1);
        cola.encolar(2);
        assertEquals(1, cola.desencolar());
        cola.encolar(3);
        cola.encolar(4);
        assertEquals(2, cola.desencolar());
        assertEquals(3, cola.desencolar());
        assertEquals(4, cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    @DisplayName("La cola crece más allá de su capacidad inicial manteniendo el orden")
    void creceManteniendoOrden() {
        Cola<Integer> cola = new Cola<>();
        for (int i = 0; i < 100; i++) {
            cola.encolar(i);
        }
        assertEquals(100, cola.tamanio());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, cola.desencolar());
        }
    }

    @Test
    @DisplayName("Desencolar o mirar el frente de una cola vacía lanza excepción")
    void operacionesSobreVaciaLanzan() {
        Cola<Integer> cola = new Cola<>();
        assertThrows(IllegalStateException.class, cola::desencolar);
        assertThrows(IllegalStateException.class, cola::frente);
    }
}
