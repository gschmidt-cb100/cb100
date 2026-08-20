package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class TablaHashTest {

    @Test
    @DisplayName("Poner y obtener con claves que colisionan en capacidad 4")
    void ponerYObtenerConColisiones() {
        // "juan", "eva" y "sol" caen todas en el indice 0 con capacidad 4.
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        assertEquals(3, tabla.tamanio());
        assertEquals(25, tabla.obtener("juan"));
        assertEquals(30, tabla.obtener("eva"));
        assertEquals(28, tabla.obtener("sol"));
    }

    @Test
    @DisplayName("Obtener una clave ausente devuelve null")
    void obtenerAusente() {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("juan", 25);
        assertNull(tabla.obtener("ana"));
    }

    @Test
    @DisplayName("Poner una clave existente reemplaza el valor sin duplicar")
    void ponerReemplaza() {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("eva", 30);
        tabla.poner("eva", 31);
        assertEquals(1, tabla.tamanio());
        assertEquals(31, tabla.obtener("eva"));
    }

    @Test
    @DisplayName("Quitar devuelve el valor y saca el par de la cadena")
    void quitar() {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("juan", 25);
        tabla.poner("eva", 30);
        tabla.poner("sol", 28);
        assertEquals(30, tabla.quitar("eva"));
        assertEquals(2, tabla.tamanio());
        assertNull(tabla.obtener("eva"));
        // Las otras dos claves de la misma cadena siguen estando.
        assertEquals(25, tabla.obtener("juan"));
        assertEquals(28, tabla.obtener("sol"));
    }

    @Test
    @DisplayName("Quitar una clave ausente devuelve null y no cambia el tamanio")
    void quitarAusente() {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("juan", 25);
        assertNull(tabla.quitar("ana"));
        assertEquals(1, tabla.tamanio());
    }

    @Test
    @DisplayName("Quitar la cabeza de la cadena no pierde el resto")
    void quitarCabezaDeCadena() {
        TablaHash<String, Integer> tabla = new TablaHash<>(4);
        tabla.poner("juan", 25);
        tabla.poner("eva", 30); // "eva" queda como cabeza (se inserta adelante).
        tabla.poner("sol", 28); // "sol" pasa a ser la cabeza.
        assertEquals(28, tabla.quitar("sol"));
        assertEquals(25, tabla.obtener("juan"));
        assertEquals(30, tabla.obtener("eva"));
        assertEquals(2, tabla.tamanio());
    }
}
