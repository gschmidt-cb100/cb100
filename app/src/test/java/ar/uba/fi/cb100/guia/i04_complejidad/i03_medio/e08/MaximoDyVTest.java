package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MaximoDyVTest {

    @Test
    @DisplayName("Encuentra el maximo en un arreglo tipico")
    void maximoTipico() {
        assertEquals(9, MaximoDyV.maximo(new int[]{5, 2, 9, 1, 5, 6}));
    }

    @Test
    @DisplayName("Todos negativos: devuelve el mayor (menos negativo)")
    void todosNegativos() {
        assertEquals(-1, MaximoDyV.maximo(new int[]{-3, -1, -7}));
    }

    @Test
    @DisplayName("Un solo elemento")
    void unElemento() {
        assertEquals(42, MaximoDyV.maximo(new int[]{42}));
    }

    @Test
    @DisplayName("El maximo esta en el primer o ultimo lugar")
    void maximoEnBordes() {
        assertEquals(10, MaximoDyV.maximo(new int[]{10, 2, 3, 4}));
        assertEquals(10, MaximoDyV.maximo(new int[]{1, 2, 3, 10}));
    }

    @Test
    @DisplayName("Arreglo vacio lanza IllegalArgumentException")
    void vacioLanza() {
        assertThrows(IllegalArgumentException.class, () -> MaximoDyV.maximo(new int[]{}));
    }
}
