package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BusquedaBinariaTest {

    @Test
    @DisplayName("Encuentra un elemento existente y devuelve un indice valido")
    void encuentraElementoExistente() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        int i = BusquedaBinaria.buscar(a, 7);
        assertTrue(i >= 0);
        assertEquals(7, a[i]);
    }

    @Test
    @DisplayName("Encuentra el primer y el ultimo elemento (bordes)")
    void encuentraBordes() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(0, BusquedaBinaria.buscar(a, 1));
        assertEquals(6, BusquedaBinaria.buscar(a, 13));
    }

    @Test
    @DisplayName("Devuelve -1 cuando el elemento no esta")
    void devuelveMenosUnoSiNoEsta() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(-1, BusquedaBinaria.buscar(a, 8));
        assertEquals(-1, BusquedaBinaria.buscar(a, 0));
        assertEquals(-1, BusquedaBinaria.buscar(a, 100));
    }

    @Test
    @DisplayName("Arreglo vacio siempre devuelve -1")
    void arregloVacio() {
        assertEquals(-1, BusquedaBinaria.buscar(new int[]{}, 5));
    }

    @Test
    @DisplayName("Arreglo nulo lanza NullPointerException")
    void arregloNulo() {
        assertThrows(NullPointerException.class, () -> BusquedaBinaria.buscar(null, 5));
    }
}
