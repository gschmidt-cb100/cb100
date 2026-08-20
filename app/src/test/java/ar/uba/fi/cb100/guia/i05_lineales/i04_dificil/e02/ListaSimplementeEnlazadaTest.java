package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ListaSimplementeEnlazadaTest {

    @Test
    @DisplayName("Agregar mantiene el orden de insercion al final")
    void agregar() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<>();
        l.agregar(10);
        l.agregar(20);
        l.agregar(30);
        assertEquals(3, l.tamanio());
        assertEquals(10, l.obtener(0));
        assertEquals(20, l.obtener(1));
        assertEquals(30, l.obtener(2));
    }

    @Test
    @DisplayName("Insertar en el frente, medio y final")
    void insertar() {
        ListaSimplementeEnlazada<String> l = new ListaSimplementeEnlazada<>();
        l.agregar("b");
        l.insertar(0, "a");   // frente
        l.insertar(2, "d");   // final (i == tamanio)
        l.insertar(2, "c");   // medio
        assertEquals(4, l.tamanio());
        assertEquals("a", l.obtener(0));
        assertEquals("b", l.obtener(1));
        assertEquals("c", l.obtener(2));
        assertEquals("d", l.obtener(3));
    }

    @Test
    @DisplayName("Eliminar cabeza y medio devuelve el dato")
    void eliminar() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<>();
        l.agregar(1);
        l.agregar(2);
        l.agregar(3);
        assertEquals(1, l.eliminar(0));
        assertEquals(3, l.eliminar(1));
        assertEquals(1, l.tamanio());
        assertEquals(2, l.obtener(0));
    }

    @Test
    @DisplayName("indiceDe encuentra el elemento o devuelve -1")
    void indiceDe() {
        ListaSimplementeEnlazada<String> l = new ListaSimplementeEnlazada<>();
        l.agregar("x");
        l.agregar("y");
        assertEquals(0, l.indiceDe("x"));
        assertEquals(1, l.indiceDe("y"));
        assertEquals(-1, l.indiceDe("z"));
    }

    @Test
    @DisplayName("Operaciones con indice invalido lanzan excepcion")
    void indicesInvalidos() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<>();
        l.agregar(1);
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(5));
        assertThrows(IndexOutOfBoundsException.class, () -> l.eliminar(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> l.insertar(9, 0));
    }
}
