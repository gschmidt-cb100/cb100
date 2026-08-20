package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PilaArregloDinamicaTest {

    @Test
    @DisplayName("Una pila recién creada está vacía y con tamaño 0")
    void pilaNuevaEstaVacia() {
        PilaArregloDinamica<String> pila = new PilaArregloDinamica<>();
        assertTrue(pila.estaVacia());
        assertEquals(0, pila.tamanio());
    }

    @Test
    @DisplayName("Respeta el orden LIFO al desapilar")
    void respetaLifo() {
        PilaArregloDinamica<String> pila = new PilaArregloDinamica<>();
        pila.apilar("a");
        pila.apilar("b");
        pila.apilar("c");

        assertEquals(3, pila.tamanio());
        assertEquals("c", pila.tope());
        assertEquals("c", pila.desapilar());
        assertEquals("b", pila.desapilar());
        assertEquals("a", pila.desapilar());
        assertTrue(pila.estaVacia());
    }

    @Test
    @DisplayName("Crece más allá de la capacidad inicial manteniendo LIFO")
    void creceMasAllaDeLaCapacidadInicial() {
        PilaArregloDinamica<Integer> pila = new PilaArregloDinamica<>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            pila.apilar(i);
        }
        assertEquals(n, pila.tamanio());
        for (int i = n - 1; i >= 0; i--) {
            assertEquals(i, pila.desapilar());
        }
        assertTrue(pila.estaVacia());
    }

    @Test
    @DisplayName("Desapilar o consultar el tope en una pila vacía lanza excepción")
    void operacionesEnVaciaLanzan() {
        PilaArregloDinamica<Integer> pila = new PilaArregloDinamica<>();
        assertThrows(IllegalStateException.class, pila::desapilar);
        assertThrows(IllegalStateException.class, pila::tope);
    }
}
