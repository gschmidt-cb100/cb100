package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class BusquedaBinariaTest {

    @Test
    @DisplayName("encuentra elementos presentes en cada posicion")
    void encuentraPresentes() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(0, BusquedaBinaria.buscar(a, 1));
        assertEquals(3, BusquedaBinaria.buscar(a, 7));
        assertEquals(6, BusquedaBinaria.buscar(a, 13));
    }

    @Test
    @DisplayName("devuelve -1 cuando el valor no esta")
    void noEncuentraAusentes() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(-1, BusquedaBinaria.buscar(a, 0));
        assertEquals(-1, BusquedaBinaria.buscar(a, 8));
        assertEquals(-1, BusquedaBinaria.buscar(a, 20));
    }

    @Test
    @DisplayName("arreglo vacio siempre devuelve -1")
    void arregloVacio() {
        assertEquals(-1, BusquedaBinaria.buscar(new int[]{}, 5));
    }

    @Test
    @DisplayName("arreglo de un solo elemento")
    void unElemento() {
        assertEquals(0, BusquedaBinaria.buscar(new int[]{42}, 42));
        assertEquals(-1, BusquedaBinaria.buscar(new int[]{42}, 7));
    }

    @Test
    @DisplayName("consistente contra busqueda lineal en arreglo grande")
    void consistenteConLineal() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i * 2; // pares 0..1998
        }
        for (int x = -1; x <= 2000; x++) {
            int esperado = (x >= 0 && x % 2 == 0 && x <= 1998) ? x / 2 : -1;
            assertEquals(esperado, BusquedaBinaria.buscar(a, x), "fallo para x=" + x);
        }
    }
}
