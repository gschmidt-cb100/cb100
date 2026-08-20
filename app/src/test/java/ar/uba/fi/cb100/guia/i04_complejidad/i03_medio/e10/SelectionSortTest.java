package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    @Test
    @DisplayName("Ordena un arreglo desordenado con repetidos")
    void ordenaDesordenado() {
        int[] a = {5, 2, 9, 1, 5, 6};
        SelectionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, a);
    }

    @Test
    @DisplayName("Un arreglo ya ordenado no cambia")
    void yaOrdenado() {
        int[] a = {1, 2, 3, 4, 5};
        SelectionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Arreglo en orden inverso")
    void ordenInverso() {
        int[] a = {5, 4, 3, 2, 1};
        SelectionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Casos borde: vacio y un solo elemento")
    void casosBorde() {
        int[] vacio = {};
        SelectionSort.ordenar(vacio);
        assertArrayEquals(new int[]{}, vacio);

        int[] uno = {13};
        SelectionSort.ordenar(uno);
        assertArrayEquals(new int[]{13}, uno);
    }

    @Test
    @DisplayName("Arreglo con negativos")
    void conNegativos() {
        int[] a = {3, -1, 0, -5, 2};
        SelectionSort.ordenar(a);
        assertArrayEquals(new int[]{-5, -1, 0, 2, 3}, a);
    }
}
