package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class KadaneTest {

    @Test
    @DisplayName("Caso clásico con positivos y negativos")
    void casoClasico() {
        int[] a = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        assertEquals(6, Kadane.maxSubarreglo(a)); // [4,-1,2,1]
    }

    @Test
    @DisplayName("Todos negativos devuelve el mayor (menos negativo)")
    void todosNegativos() {
        int[] a = {-8, -3, -6, -2, -5};
        assertEquals(-2, Kadane.maxSubarreglo(a));
    }

    @Test
    @DisplayName("Todos positivos devuelve la suma total")
    void todosPositivos() {
        int[] a = {1, 2, 3, 4};
        assertEquals(10, Kadane.maxSubarreglo(a));
    }

    @Test
    @DisplayName("Un solo elemento")
    void unElemento() {
        assertEquals(-5, Kadane.maxSubarreglo(new int[]{-5}));
        assertEquals(7, Kadane.maxSubarreglo(new int[]{7}));
    }

    @Test
    @DisplayName("Null o vacío lanzan excepción")
    void invalidos() {
        assertThrows(IllegalArgumentException.class, () -> Kadane.maxSubarreglo(null));
        assertThrows(IllegalArgumentException.class, () -> Kadane.maxSubarreglo(new int[]{}));
    }
}
