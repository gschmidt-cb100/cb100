package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class VectorDinamicoTest {

    @Test
    @DisplayName("Arranca vacio con capacidad inicial 4")
    void arrancaVacio() {
        VectorDinamico<Integer> v = new VectorDinamico<>();
        assertEquals(0, v.tamanio());
        assertEquals(4, v.capacidad());
    }

    @Test
    @DisplayName("Crece mas alla de la capacidad inicial duplicando")
    void creceDuplicando() {
        VectorDinamico<Integer> v = new VectorDinamico<>();
        for (int i = 0; i < 5; i++) {
            v.agregar(i);
        }
        // 5 elementos: la capacidad 4 se duplico a 8.
        assertEquals(5, v.tamanio());
        assertEquals(8, v.capacidad());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, v.obtener(i));
        }
    }

    @Test
    @DisplayName("Crece varias veces manteniendo el orden")
    void creceVariasVeces() {
        VectorDinamico<Integer> v = new VectorDinamico<>();
        for (int i = 0; i < 20; i++) {
            v.agregar(i * 10);
        }
        assertEquals(20, v.tamanio());
        assertTrue(v.capacidad() >= 20);
        assertEquals(0, v.obtener(0));
        assertEquals(190, v.obtener(19));
    }

    @Test
    @DisplayName("Insertar corre los elementos a la derecha")
    void insertar() {
        VectorDinamico<String> v = new VectorDinamico<>();
        v.agregar("a");
        v.agregar("b");
        v.agregar("c");
        v.insertar(1, "x");
        assertEquals("a", v.obtener(0));
        assertEquals("x", v.obtener(1));
        assertEquals("b", v.obtener(2));
        assertEquals("c", v.obtener(3));
        assertEquals(4, v.tamanio());
    }

    @Test
    @DisplayName("Insertar al final (i == tamanio) es valido")
    void insertarAlFinal() {
        VectorDinamico<String> v = new VectorDinamico<>();
        v.agregar("a");
        v.insertar(1, "b");
        assertEquals("b", v.obtener(1));
        assertEquals(2, v.tamanio());
    }

    @Test
    @DisplayName("Eliminar devuelve el elemento y corre a la izquierda")
    void eliminar() {
        VectorDinamico<String> v = new VectorDinamico<>();
        v.agregar("a");
        v.agregar("b");
        v.agregar("c");
        assertEquals("b", v.eliminar(1));
        assertEquals("a", v.obtener(0));
        assertEquals("c", v.obtener(1));
        assertEquals(2, v.tamanio());
    }

    @Test
    @DisplayName("Indices invalidos lanzan excepcion")
    void indicesInvalidos() {
        VectorDinamico<Integer> v = new VectorDinamico<>();
        v.agregar(1);
        assertThrows(IndexOutOfBoundsException.class, () -> v.obtener(1));
        assertThrows(IndexOutOfBoundsException.class, () -> v.obtener(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> v.eliminar(5));
        assertThrows(IndexOutOfBoundsException.class, () -> v.insertar(9, 0));
    }
}
