package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortIterativoTest {

    @Test
    @DisplayName("Ordena tamaño no potencia de dos")
    void tamanioImpar() {
        int[] a = {5, 2, 9, 1, 7, 3, 8};
        MergeSortIterativo.ordenar(a);
        assertArrayEquals(new int[]{1, 2, 3, 5, 7, 8, 9}, a);
    }

    @Test
    @DisplayName("Ordena tamaño potencia de dos con repetidos")
    void potenciaDeDos() {
        int[] a = {4, 4, 2, 8, 1, 1, 6, 3};
        MergeSortIterativo.ordenar(a);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4, 6, 8}, a);
    }

    @Test
    @DisplayName("Orden inverso y negativos")
    void inversoNegativos() {
        int[] a = {3, -1, -5, 10, 0, -2};
        MergeSortIterativo.ordenar(a);
        assertArrayEquals(new int[]{-5, -2, -1, 0, 3, 10}, a);
    }

    @Test
    @DisplayName("Casos borde: vacío y un elemento")
    void casosBorde() {
        int[] vacio = {};
        MergeSortIterativo.ordenar(vacio);
        assertArrayEquals(new int[]{}, vacio);

        int[] uno = {9};
        MergeSortIterativo.ordenar(uno);
        assertArrayEquals(new int[]{9}, uno);
    }

    @Test
    @DisplayName("Arreglo null lanza excepción")
    void nullLanza() {
        assertThrows(IllegalArgumentException.class, () -> MergeSortIterativo.ordenar(null));
    }
}
