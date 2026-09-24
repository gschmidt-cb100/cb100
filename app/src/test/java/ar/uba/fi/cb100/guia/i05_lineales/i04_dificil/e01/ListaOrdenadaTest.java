package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListaOrdenadaTest {

    @Test
    @DisplayName("Insertar en cualquier orden deja la lista ordenada")
    void insertarDesordenado() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        for (int x : new int[]{42, 7, 19, 3, 25}) {
            lista.insertar(x);
        }
        assertEquals("[3, 7, 19, 25, 42]", lista.toString());
        assertEquals(5, lista.tamanio());
    }

    @Test
    @DisplayName("Insertar al principio, al final y en el medio")
    void insertarEnLosExtremos() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        lista.insertar(10);
        lista.insertar(1);     // al principio
        lista.insertar(20);    // al final
        lista.insertar(15);    // en el medio
        assertEquals("[1, 10, 15, 20]", lista.toString());
    }

    @Test
    @DisplayName("Los duplicados se admiten y quedan contiguos")
    void duplicados() {
        ListaOrdenada<String> lista = new ListaOrdenada<>();
        lista.insertar("b");
        lista.insertar("a");
        lista.insertar("b");
        assertEquals("[a, b, b]", lista.toString());
    }

    @Test
    @DisplayName("contiene e indiceDe usan busqueda binaria sobre la lista ordenada")
    void contieneEIndiceDe() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        for (int x : new int[]{3, 7, 19, 25, 42}) {
            lista.insertar(x);
        }
        assertTrue(lista.contiene(19));
        assertFalse(lista.contiene(20));
        assertEquals(3, lista.indiceDe(25));
        assertEquals(-1, lista.indiceDe(99));
    }

    @Test
    @DisplayName("eliminar saca UNA aparicion y avisa si no estaba")
    void eliminar() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        lista.insertar(7);
        lista.insertar(7);
        lista.insertar(3);
        assertTrue(lista.eliminar(7));
        assertEquals("[3, 7]", lista.toString());
        assertFalse(lista.eliminar(99));
        assertEquals(2, lista.tamanio());
    }

    @Test
    @DisplayName("minimo y maximo son los extremos, en O(1)")
    void minimoYMaximo() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        lista.insertar(19);
        lista.insertar(3);
        lista.insertar(42);
        assertEquals(3, lista.minimo());
        assertEquals(42, lista.maximo());
    }

    @Test
    @DisplayName("Lista vacia: minimo y maximo lanzan excepcion, obtener valida el rango")
    void vaciaYRangos() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        assertTrue(lista.estaVacia());
        assertThrows(IllegalStateException.class, lista::minimo);
        assertThrows(IllegalStateException.class, lista::maximo);
        lista.insertar(1);
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(-1));
    }

    @Test
    @DisplayName("null se rechaza")
    void nullSeRechaza() {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        assertThrows(NullPointerException.class, () -> lista.insertar(null));
        assertThrows(NullPointerException.class, () -> lista.contiene(null));
    }
}
