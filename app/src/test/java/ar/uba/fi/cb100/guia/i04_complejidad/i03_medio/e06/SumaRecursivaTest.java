package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SumaRecursivaTest {

    @Test
    @DisplayName("Suma los elementos de un arreglo tipico")
    void sumaTipica() {
        assertEquals(15L, SumaRecursiva.sumar(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("Arreglo vacio suma 0")
    void arregloVacio() {
        assertEquals(0L, SumaRecursiva.sumar(new int[]{}));
    }

    @Test
    @DisplayName("Un solo elemento devuelve ese valor")
    void unElemento() {
        assertEquals(42L, SumaRecursiva.sumar(new int[]{42}));
    }

    @Test
    @DisplayName("Suma con numeros negativos")
    void conNegativos() {
        assertEquals(0L, SumaRecursiva.sumar(new int[]{-1, 1, -2, 2}));
        assertEquals(-6L, SumaRecursiva.sumar(new int[]{-1, -2, -3}));
    }

    @Test
    @DisplayName("Arreglo nulo lanza NullPointerException")
    void arregloNulo() {
        assertThrows(NullPointerException.class, () -> SumaRecursiva.sumar(null));
    }
}
