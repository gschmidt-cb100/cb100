package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    @Test
    @DisplayName("Una lista nueva tiene tamaño 0")
    void listaNuevaVacia() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        assertEquals(0, lista.tamanio());
    }

    @Test
    @DisplayName("Agregar respeta el orden de inserción")
    void agregarRespetaOrden() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");
        assertEquals(3, lista.tamanio());
        assertEquals("a", lista.obtener(0));
        assertEquals("b", lista.obtener(1));
        assertEquals("c", lista.obtener(2));
    }

    @Test
    @DisplayName("Obtener con índice fuera de rango lanza excepción")
    void obtenerFueraDeRango() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.agregar(10);
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(1));
    }

    @Test
    @DisplayName("Soporta muchos elementos")
    void soportaMuchos() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        for (int i = 0; i < 500; i++) {
            lista.agregar(i * 2);
        }
        assertEquals(500, lista.tamanio());
        assertEquals(0, lista.obtener(0));
        assertEquals(998, lista.obtener(499));
    }
}
