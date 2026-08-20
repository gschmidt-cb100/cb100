package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ListaDoblementeEnlazadaTest {

    @Test
    @DisplayName("Agregar mantiene el orden")
    void agregar() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.agregar(1);
        l.agregar(2);
        l.agregar(3);
        assertEquals(3, l.tamanio());
        assertEquals(1, l.obtener(0));
        assertEquals(3, l.obtener(2));
    }

    @Test
    @DisplayName("Insertar al frente, medio y final")
    void insertar() {
        ListaDoblementeEnlazada<String> l = new ListaDoblementeEnlazada<>();
        l.agregar("b");
        l.insertar(0, "a");
        l.insertar(2, "d");
        l.insertar(2, "c");
        assertEquals("a", l.obtener(0));
        assertEquals("b", l.obtener(1));
        assertEquals("c", l.obtener(2));
        assertEquals("d", l.obtener(3));
    }

    @Test
    @DisplayName("Eliminar del medio reengancha anterior y siguiente")
    void eliminarDelMedio() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        for (int i = 1; i <= 5; i++) {
            l.agregar(i);
        }
        assertEquals(3, l.eliminar(2));
        assertEquals(4, l.tamanio());
        assertEquals(1, l.obtener(0));
        assertEquals(2, l.obtener(1));
        assertEquals(4, l.obtener(2));
        assertEquals(5, l.obtener(3));
    }

    @Test
    @DisplayName("Eliminar cabeza y cola actualiza los extremos")
    void eliminarExtremos() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.agregar(1);
        l.agregar(2);
        l.agregar(3);
        assertEquals(1, l.eliminar(0));
        assertEquals(3, l.eliminar(1));
        assertEquals(1, l.tamanio());
        assertEquals(2, l.obtener(0));
    }

    @Test
    @DisplayName("indiceDe e indices invalidos")
    void indiceDeYErrores() {
        ListaDoblementeEnlazada<String> l = new ListaDoblementeEnlazada<>();
        l.agregar("x");
        l.agregar("y");
        assertEquals(1, l.indiceDe("y"));
        assertEquals(-1, l.indiceDe("z"));
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(2));
        assertThrows(IndexOutOfBoundsException.class, () -> l.eliminar(9));
    }
}
