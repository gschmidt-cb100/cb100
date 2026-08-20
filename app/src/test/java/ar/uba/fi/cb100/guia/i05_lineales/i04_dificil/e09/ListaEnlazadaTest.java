package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    @Test
    @DisplayName("Medio de largo impar es el central")
    void medioImpar() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        for (int i = 1; i <= 5; i++) {
            l.agregar(i);
        }
        assertEquals(3, l.medio());
    }

    @Test
    @DisplayName("Medio de largo par devuelve el segundo central")
    void medioPar() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        for (int i = 1; i <= 4; i++) {
            l.agregar(i); // [1,2,3,4] -> segundo central = 3
        }
        assertEquals(3, l.medio());
    }

    @Test
    @DisplayName("Medio de un solo elemento es ese elemento")
    void medioUnElemento() {
        ListaEnlazada<String> l = new ListaEnlazada<>();
        l.agregar("solo");
        assertEquals("solo", l.medio());
    }

    @Test
    @DisplayName("Medio de dos elementos es el segundo")
    void medioDosElementos() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        l.agregar(10);
        l.agregar(20);
        assertEquals(20, l.medio());
    }

    @Test
    @DisplayName("Medio de lista vacia lanza excepcion")
    void medioVacia() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        assertThrows(NoSuchElementException.class, l::medio);
    }
}
