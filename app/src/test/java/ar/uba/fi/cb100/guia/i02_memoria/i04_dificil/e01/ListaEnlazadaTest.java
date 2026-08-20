package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    @Test
    @DisplayName("Lista vacia: tamanio 0 y arreglo vacio")
    void listaVacia() {
        ListaEnlazada lista = new ListaEnlazada();
        assertEquals(0, lista.tamanio());
        assertArrayEquals(new int[]{}, lista.aArreglo());
    }

    @Test
    @DisplayName("Agregar respeta el orden de insercion")
    void agregarMantieneOrden() {
        ListaEnlazada lista = new ListaEnlazada();
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        assertEquals(3, lista.tamanio());
        assertArrayEquals(new int[]{1, 2, 3}, lista.aArreglo());
    }

    @Test
    @DisplayName("Caso borde: un unico elemento")
    void unSoloElemento() {
        ListaEnlazada lista = new ListaEnlazada();
        lista.agregar(42);
        assertEquals(1, lista.tamanio());
        assertArrayEquals(new int[]{42}, lista.aArreglo());
    }

    @Test
    @DisplayName("Admite valores repetidos y negativos")
    void valoresRepetidosYNegativos() {
        ListaEnlazada lista = new ListaEnlazada();
        lista.agregar(-5);
        lista.agregar(-5);
        lista.agregar(0);
        assertArrayEquals(new int[]{-5, -5, 0}, lista.aArreglo());
    }
}
