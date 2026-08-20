package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class DiccionarioArreglosParalelosTest {

    @Test
    @DisplayName("Obtener una clave inexistente devuelve Optional.empty")
    void obtenerClaveInexistente() {
        DiccionarioArreglosParalelos<String, Integer> d = new DiccionarioArreglosParalelos<>();
        assertEquals(Optional.empty(), d.obtener("x"));
        assertFalse(d.contieneClave("x"));
        assertEquals(0, d.tamanio());
    }

    @Test
    @DisplayName("Poner y obtener recupera el valor asociado")
    void ponerYObtener() {
        DiccionarioArreglosParalelos<String, Integer> d = new DiccionarioArreglosParalelos<>();
        d.poner("uno", 1);
        d.poner("dos", 2);
        assertEquals(1, d.obtener("uno").orElseThrow());
        assertEquals(2, d.obtener("dos").orElseThrow());
        assertTrue(d.contieneClave("uno"));
        assertEquals(2, d.tamanio());
    }

    @Test
    @DisplayName("Poner con una clave existente actualiza el valor sin crecer")
    void ponerActualiza() {
        DiccionarioArreglosParalelos<String, Integer> d = new DiccionarioArreglosParalelos<>();
        d.poner("k", 10);
        d.poner("k", 99);
        assertEquals(99, d.obtener("k").orElseThrow());
        assertEquals(1, d.tamanio());
    }

    @Test
    @DisplayName("Crece correctamente al superar la capacidad inicial")
    void creceAlSuperarCapacidad() {
        DiccionarioArreglosParalelos<Integer, Integer> d = new DiccionarioArreglosParalelos<>();
        for (int i = 0; i < 100; i++) {
            d.poner(i, i * i);
        }
        assertEquals(100, d.tamanio());
        assertEquals(81, d.obtener(9).orElseThrow());
        assertEquals(99 * 99, d.obtener(99).orElseThrow());
        assertTrue(d.obtener(100).isEmpty());
    }
}
