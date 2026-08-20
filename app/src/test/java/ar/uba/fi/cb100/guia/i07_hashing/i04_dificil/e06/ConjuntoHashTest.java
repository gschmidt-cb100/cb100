package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ConjuntoHashTest {

    @Test
    @DisplayName("Agregar devuelve true la primera vez y false si ya estaba")
    void agregarSinRepetidos() {
        ConjuntoHash<String> conjunto = new ConjuntoHash<>();
        assertTrue(conjunto.agregar("ana"));
        assertTrue(conjunto.agregar("leo"));
        assertFalse(conjunto.agregar("ana"));
        assertEquals(2, conjunto.tamanio());
    }

    @Test
    @DisplayName("Contiene distingue presentes de ausentes")
    void contiene() {
        ConjuntoHash<String> conjunto = new ConjuntoHash<>();
        conjunto.agregar("juan");
        conjunto.agregar("eva");
        assertTrue(conjunto.contiene("juan"));
        assertTrue(conjunto.contiene("eva"));
        assertFalse(conjunto.contiene("sol"));
    }

    @Test
    @DisplayName("Quitar devuelve true si estaba y false si no")
    void quitar() {
        ConjuntoHash<String> conjunto = new ConjuntoHash<>();
        conjunto.agregar("juan");
        conjunto.agregar("eva");
        conjunto.agregar("sol"); // Las tres colisionan: misma cadena.
        assertTrue(conjunto.quitar("eva"));
        assertFalse(conjunto.quitar("eva"));
        assertFalse(conjunto.contiene("eva"));
        assertTrue(conjunto.contiene("juan"));
        assertTrue(conjunto.contiene("sol"));
        assertEquals(2, conjunto.tamanio());
    }

    @Test
    @DisplayName("Con muchos elementos rehashea y no pierde ninguno")
    void creceSinPerderElementos() {
        ConjuntoHash<Integer> conjunto = new ConjuntoHash<>();
        for (int i = 0; i < 100; i++) {
            assertTrue(conjunto.agregar(i));
        }
        assertEquals(100, conjunto.tamanio());
        for (int i = 0; i < 100; i++) {
            assertTrue(conjunto.contiene(i), "falta el " + i);
        }
        assertFalse(conjunto.contiene(100));
    }

    @Test
    @DisplayName("Agregar de nuevo un elemento quitado vuelve a dar true")
    void agregarDespuesDeQuitar() {
        ConjuntoHash<String> conjunto = new ConjuntoHash<>();
        conjunto.agregar("ana");
        conjunto.quitar("ana");
        assertTrue(conjunto.agregar("ana"));
        assertEquals(1, conjunto.tamanio());
    }
}
