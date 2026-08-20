package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    @DisplayName("Ordena un arreglo desordenado con repetidos")
    void ordenaDesordenado() {
        int[] a = {5, 2, 9, 1, 5, 6, 3};
        MergeSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 5, 5, 6, 9}, a);
    }

    @Test
    @DisplayName("Un arreglo ya ordenado no cambia")
    void yaOrdenado() {
        int[] a = {1, 2, 3, 4, 5};
        MergeSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Arreglo en orden inverso")
    void ordenInverso() {
        int[] a = {8, 7, 6, 5, 4, 3, 2, 1};
        MergeSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, a);
    }

    @Test
    @DisplayName("Casos borde: vacio y un solo elemento")
    void casosBorde() {
        int[] vacio = {};
        MergeSort.ordenar(vacio);
        assertArrayEquals(new int[]{}, vacio);

        int[] uno = {99};
        MergeSort.ordenar(uno);
        assertArrayEquals(new int[]{99}, uno);
    }

    @Test
    @DisplayName("Arreglo con negativos")
    void conNegativos() {
        int[] a = {3, -1, 0, -5, 2, -5};
        MergeSort.ordenar(a);
        assertArrayEquals(new int[]{-5, -5, -1, 0, 2, 3}, a);
    }
}
