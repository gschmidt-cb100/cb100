package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    @DisplayName("Ordena un arreglo desordenado con repetidos")
    void ordenaDesordenado() {
        int[] a = {5, 2, 9, 1, 5, 6};
        InsertionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, a);
    }

    @Test
    @DisplayName("Un arreglo ya ordenado no cambia (mejor caso O(n))")
    void yaOrdenado() {
        int[] a = {1, 2, 3, 4, 5};
        InsertionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Arreglo en orden inverso (peor caso)")
    void ordenInverso() {
        int[] a = {5, 4, 3, 2, 1};
        InsertionSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Casos borde: vacio y un solo elemento")
    void casosBorde() {
        int[] vacio = {};
        InsertionSort.ordenar(vacio);
        assertArrayEquals(new int[]{}, vacio);

        int[] uno = {7};
        InsertionSort.ordenar(uno);
        assertArrayEquals(new int[]{7}, uno);
    }

    @Test
    @DisplayName("Arreglo con negativos")
    void conNegativos() {
        int[] a = {3, -1, 0, -5, 2};
        InsertionSort.ordenar(a);
        assertArrayEquals(new int[]{-5, -1, 0, 2, 3}, a);
    }
}
