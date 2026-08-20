package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PilaTest {

    @Test
    @DisplayName("Una pila recién creada está vacía y con tamaño 0")
    void pilaNuevaEstaVacia() {
        Pila<Integer> pila = new Pila<>();
        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamanio());
    }

    @Test
    @DisplayName("El orden de desapilado es LIFO (último en entrar, primero en salir)")
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
        pila.apilar("x");
        pila.apilar("y");

        assertEquals("y", pila.tope());
        assertEquals(2, pila.tamanio()); // tope no modifica el tamaño
    }

    @Test
    @DisplayName("La pila crece más allá de su capacidad inicial")
    void creceMasAllaDeCapacidadInicial() {
        Pila<Integer> pila = new Pila<>();
        for (int i = 0; i < 100; i++) {
            pila.apilar(i);
        }
        assertEquals(100, pila.tamanio());
        assertEquals(99, pila.tope());
    }

    @Test
    @DisplayName("Desapilar una pila vacía lanza IllegalStateException")
    void desapilarVaciaLanza() {
        Pila<Integer> pila = new Pila<>();
        assertThrows(IllegalStateException.class, pila::desapilar);
    }

    @Test
    @DisplayName("Pedir el tope de una pila vacía lanza IllegalStateException")
    void topeVaciaLanza() {
        Pila<Integer> pila = new Pila<>();
        assertThrows(IllegalStateException.class, pila::tope);
    }
}
