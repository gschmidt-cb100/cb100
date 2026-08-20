package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ConjuntoListaTest {

    @Test
    @DisplayName("Agregar no admite duplicados y devuelve true/false")
    void agregarSinDuplicados() {
        ConjuntoLista<Integer> c = new ConjuntoLista<>();
        assertTrue(c.agregar(1));
        assertTrue(c.agregar(2));
        assertFalse(c.agregar(1)); // ya estaba
        assertEquals(2, c.tamanio());
    }

    @Test
    @DisplayName("contiene refleja los elementos presentes")
    void contiene() {
        ConjuntoLista<String> c = new ConjuntoLista<>();
        c.agregar("a");
        assertTrue(c.contiene("a"));
        assertFalse(c.contiene("b"));
    }

    @Test
    @DisplayName("Eliminar quita el elemento y devuelve si existia")
    void eliminar() {
        ConjuntoLista<Integer> c = new ConjuntoLista<>();
        c.agregar(1);
        c.agregar(2);
        assertTrue(c.eliminar(1));
        assertFalse(c.contiene(1));
        assertFalse(c.eliminar(99)); // no estaba
        assertEquals(1, c.tamanio());
    }

    @Test
    @DisplayName("Union combina sin duplicar y no modifica los originales")
    void union() {
        ConjuntoLista<Integer> a = new ConjuntoLista<>();
        a.agregar(1);
        a.agregar(2);
        ConjuntoLista<Integer> b = new ConjuntoLista<>();
        b.agregar(2);
        b.agregar(3);
        ConjuntoLista<Integer> u = a.union(b);
        assertEquals(3, u.tamanio());
        assertTrue(u.contiene(1));
        assertTrue(u.contiene(2));
        assertTrue(u.contiene(3));
        // Los originales quedan intactos.
        assertEquals(2, a.tamanio());
        assertEquals(2, b.tamanio());
    }

    @Test
    @DisplayName("Admite un unico elemento null")
    void admiteNull() {
        ConjuntoLista<String> c = new ConjuntoLista<>();
        assertTrue(c.agregar(null));
        assertFalse(c.agregar(null));
        assertTrue(c.contiene(null));
        assertTrue(c.eliminar(null));
    }
}
