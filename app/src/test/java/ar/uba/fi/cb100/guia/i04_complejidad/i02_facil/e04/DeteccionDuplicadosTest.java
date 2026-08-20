package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DeteccionDuplicadosTest {

    @Test
    @DisplayName("Con duplicado devuelve true")
    void conDuplicado() {
        assertTrue(DeteccionDuplicados.hayDuplicado(new int[]{1, 2, 3, 2}));
    }

    @Test
    @DisplayName("Sin duplicado devuelve false")
    void sinDuplicado() {
        assertFalse(DeteccionDuplicados.hayDuplicado(new int[]{1, 2, 3, 4}));
    }

    @Test
    @DisplayName("Arreglo vacio no tiene duplicados")
    void arregloVacio() {
        assertFalse(DeteccionDuplicados.hayDuplicado(new int[0]));
    }

    @Test
    @DisplayName("Un solo elemento no tiene duplicados")
    void unSoloElemento() {
        assertFalse(DeteccionDuplicados.hayDuplicado(new int[]{9}));
    }

    @Test
    @DisplayName("Duplicado en las dos ultimas posiciones")
    void duplicadoAlFinal() {
        assertTrue(DeteccionDuplicados.hayDuplicado(new int[]{5, 6, 7, 7}));
    }

    @Test
    @DisplayName("Arreglo nulo lanza excepcion")
    void arregloNulo() {
        assertThrows(NullPointerException.class,
                () -> DeteccionDuplicados.hayDuplicado(null));
    }
}
