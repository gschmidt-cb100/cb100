package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PilaTest {

    /**
     * Verifica el contrato LIFO común a cualquier implementación de Pila.
     * Ambos tests concretos delegan aquí para garantizar el mismo comportamiento.
     */
    private void verificarComportamientoLifo(Pila<Integer> pila) {
        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamanio());
        assertThrows(IllegalStateException.class, pila::desapilar);
        assertThrows(IllegalStateException.class, pila::tope);

        int n = 100;
        for (int i = 0; i < n; i++) {
            pila.apilar(i);
        }
        assertFalse(pila.estaVacia());
        assertEquals(n, pila.tamanio());
        assertEquals(n - 1, pila.tope());

        for (int i = n - 1; i >= 0; i--) {
            assertEquals(i, pila.tope());
            assertEquals(i, pila.desapilar());
        }
        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamanio());
    }

    @Test
    @DisplayName("PilaArreglo respeta el contrato LIFO")
    void pilaArregloLifo() {
        verificarComportamientoLifo(new PilaArreglo<>());
    }

    @Test
    @DisplayName("PilaEnlazada respeta el contrato LIFO")
    void pilaEnlazadaLifo() {
        verificarComportamientoLifo(new PilaEnlazada<>());
    }

    @Test
    @DisplayName("Ambas implementaciones producen la misma secuencia de desapilado")
    void ambasSeComportanIgual() {
        Pila<String> arreglo = new PilaArreglo<>();
        Pila<String> enlazada = new PilaEnlazada<>();
        String[] entrada = {"a", "b", "c", "d", "e"};

        for (String s : entrada) {
            arreglo.apilar(s);
            enlazada.apilar(s);
        }

        while (!arreglo.estaVacia() && !enlazada.estaVacia()) {
            assertEquals(arreglo.desapilar(), enlazada.desapilar());
        }
        assertTrue(arreglo.estaVacia());
        assertTrue(enlazada.estaVacia());
    }
}
