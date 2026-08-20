package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BuscarTest {

    @Test
    @DisplayName("Elemento presente devuelve su índice")
    void presente() {
        int[] datos = {10, 20, 30, 40};
        assertEquals(0, Buscar.indiceDe(datos, 10));
        assertEquals(2, Buscar.indiceDe(datos, 30));
        assertEquals(3, Buscar.indiceDe(datos, 40));
    }

    @Test
    @DisplayName("Elemento ausente devuelve -1")
    void ausente() {
        int[] datos = {10, 20, 30};
        assertEquals(-1, Buscar.indiceDe(datos, 99));
        assertEquals(-1, Buscar.indiceDe(new int[]{}, 1));
        assertEquals(-1, Buscar.indiceDe(null, 1));
    }

    @Test
    @DisplayName("Con repetidos devuelve la primera aparición")
    void primeraAparicion() {
        assertEquals(1, Buscar.indiceDe(new int[]{5, 7, 7, 7}, 7));
    }
}
