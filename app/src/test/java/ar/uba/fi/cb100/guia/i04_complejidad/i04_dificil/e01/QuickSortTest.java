package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    @DisplayName("Ordena un arreglo desordenado con repetidos")
    void ordenaDesordenado() {
        int[] a = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0, 3};
        int[] esperado = {0, 1, 2, 3, 3, 4, 5, 6, 7, 8, 9};
        QuickSort.ordenar(a);
        assertArrayEquals(esperado, a);
    }

    @Test
    @DisplayName("Arreglo ya ordenado (peor caso de Lomuto) queda igual")
    void yaOrdenado() {
        int[] a = {1, 2, 3, 4, 5};
        QuickSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Arreglo en orden inverso")
    void ordenInverso() {
        int[] a = {5, 4, 3, 2, 1};
        QuickSort.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, a);
    }

    @Test
    @DisplayName("Casos borde: vacío y de un elemento")
    void casosBorde() {
        int[] vacio = {};
        QuickSort.ordenar(vacio);
        assertArrayEquals(new int[]{}, vacio);

        int[] uno = {42};
        QuickSort.ordenar(uno);
        assertArrayEquals(new int[]{42}, uno);
    }

    @Test
    @DisplayName("Con negativos")
    void conNegativos() {
        int[] a = {-3, 5, -1, 0, -7, 2};
        QuickSort.ordenar(a);
        assertArrayEquals(new int[]{-7, -3, -1, 0, 2, 5}, a);
    }

    @Test
    @DisplayName("Arreglo null lanza excepción")
    void nullLanza() {
        assertThrows(IllegalArgumentException.class, () -> QuickSort.ordenar(null));
    }
}
