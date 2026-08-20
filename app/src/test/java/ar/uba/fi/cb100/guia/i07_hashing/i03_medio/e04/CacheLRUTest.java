package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheLRUTest {

    @Test
    @DisplayName("Guarda y recupera valores mientras no supera la capacidad")
    void guardaYRecupera() {
        CacheLRU<String, Integer> cache = new CacheLRU<>(3);
        cache.poner("a", 1);
        cache.poner("b", 2);

        assertEquals(1, cache.obtener("a"));
        assertEquals(2, cache.obtener("b"));
        assertEquals(2, cache.size());
    }

    @Test
    @DisplayName("Al superar la capacidad se va la entrada menos recientemente usada")
    void expulsaLaMenosUsada() {
        CacheLRU<String, Integer> cache = new CacheLRU<>(2);
        cache.poner("a", 1);
        cache.poner("b", 2);
        cache.poner("c", 3); // "a" es la menos usada: se va.

        assertNull(cache.obtener("a"));
        assertEquals(2, cache.obtener("b"));
        assertEquals(3, cache.obtener("c"));
    }

    @Test
    @DisplayName("obtener() renueva la entrada: la consultada sobrevive a la expulsión")
    void obtenerRenueva() {
        CacheLRU<String, Integer> cache = new CacheLRU<>(2);
        cache.poner("a", 1);
        cache.poner("b", 2);
        cache.obtener("a");  // ahora "b" es la menos reciente.
        cache.poner("c", 3); // se va "b", no "a".

        assertEquals(1, cache.obtener("a"));
        assertNull(cache.obtener("b"));
        assertEquals(3, cache.obtener("c"));
    }

    @Test
    @DisplayName("obtener() de una clave ausente devuelve null")
    void claveAusente() {
        CacheLRU<String, Integer> cache = new CacheLRU<>(2);
        assertNull(cache.obtener("nada"));
    }

    @Test
    @DisplayName("Capacidad inválida lanza IllegalArgumentException")
    void capacidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new CacheLRU<String, Integer>(0));
    }
}
