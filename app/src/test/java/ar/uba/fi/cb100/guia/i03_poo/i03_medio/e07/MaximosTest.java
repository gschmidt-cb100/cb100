package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MaximosTest {

    @Test
    @DisplayName("maximo funciona con Integer")
    void maximoConEnteros() {
        Integer[] a = {3, 9, 1, 7, 4};
        assertEquals(9, Maximos.maximo(a));
    }

    @Test
    @DisplayName("maximo funciona con String (orden lexicográfico)")
    void maximoConStrings() {
        String[] a = {"banana", "manzana", "kiwi", "durazno"};
        assertEquals("manzana", Maximos.maximo(a));
    }

    @Test
    @DisplayName("maximo sobre un único elemento devuelve ese elemento")
    void maximoUnElemento() {
        assertEquals(42, Maximos.maximo(new Integer[]{42}));
    }

    @Test
    @DisplayName("maximo con el mayor en la primera posición")
    void maximoAlPrincipio() {
        assertEquals(100, Maximos.maximo(new Integer[]{100, 1, 2, 3}));
    }

    @Test
    @DisplayName("maximo lanza excepción con arreglo vacío o null")
    void maximoVacioONullLanza() {
        assertThrows(IllegalArgumentException.class, () -> Maximos.maximo(new Integer[0]));
        assertThrows(IllegalArgumentException.class, () -> Maximos.maximo(null));
    }
}
