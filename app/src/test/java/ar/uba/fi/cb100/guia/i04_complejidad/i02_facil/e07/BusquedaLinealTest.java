package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BusquedaLinealTest {

    @Test
    @DisplayName("Elemento presente en el medio")
    void presenteEnMedio() {
        assertEquals(2, BusquedaLineal.indiceDe(new int[]{4, 8, 15, 16}, 15));
    }

    @Test
    @DisplayName("Elemento ausente devuelve -1")
    void ausente() {
        assertEquals(-1, BusquedaLineal.indiceDe(new int[]{4, 8, 15}, 99));
    }

    @Test
    @DisplayName("Primera posicion")
    void primeraPosicion() {
        assertEquals(0, BusquedaLineal.indiceDe(new int[]{7, 8, 9}, 7));
    }

    @Test
    @DisplayName("Devuelve la primera aparicion cuando hay repetidos")
    void primeraAparicion() {
        assertEquals(1, BusquedaLineal.indiceDe(new int[]{3, 5, 5, 5}, 5));
    }

    @Test
    @DisplayName("Arreglo vacio devuelve -1")
    void arregloVacio() {
        assertEquals(-1, BusquedaLineal.indiceDe(new int[0], 1));
    }

    @Test
    @DisplayName("Arreglo nulo lanza excepcion")
    void arregloNulo() {
        assertThrows(NullPointerException.class,
                () -> BusquedaLineal.indiceDe(null, 1));
    }
}
