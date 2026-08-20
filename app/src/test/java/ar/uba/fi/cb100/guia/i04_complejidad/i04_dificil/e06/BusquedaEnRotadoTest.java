package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BusquedaEnRotadoTest {

    @Test
    @DisplayName("Encuentra elementos en un arreglo rotado")
    void encuentra() {
        int[] a = {4, 5, 6, 7, 0, 1, 2};
        assertEquals(4, BusquedaEnRotado.buscar(a, 0));
        assertEquals(0, BusquedaEnRotado.buscar(a, 4));
        assertEquals(6, BusquedaEnRotado.buscar(a, 2));
        assertEquals(3, BusquedaEnRotado.buscar(a, 7));
    }

    @Test
    @DisplayName("Devuelve -1 cuando no está")
    void noEsta() {
        int[] a = {4, 5, 6, 7, 0, 1, 2};
        assertEquals(-1, BusquedaEnRotado.buscar(a, 3));
        assertEquals(-1, BusquedaEnRotado.buscar(a, 100));
    }

    @Test
    @DisplayName("Arreglo no rotado (rotación 0) también funciona")
    void sinRotar() {
        int[] a = {1, 2, 3, 4, 5};
        assertEquals(2, BusquedaEnRotado.buscar(a, 3));
        assertEquals(-1, BusquedaEnRotado.buscar(a, 6));
    }

    @Test
    @DisplayName("Casos borde: vacío y un elemento")
    void casosBorde() {
        assertEquals(-1, BusquedaEnRotado.buscar(new int[]{}, 1));
        assertEquals(0, BusquedaEnRotado.buscar(new int[]{9}, 9));
        assertEquals(-1, BusquedaEnRotado.buscar(new int[]{9}, 1));
    }

    @Test
    @DisplayName("Arreglo null lanza excepción")
    void nullLanza() {
        assertThrows(IllegalArgumentException.class, () -> BusquedaEnRotado.buscar(null, 1));
    }
}
