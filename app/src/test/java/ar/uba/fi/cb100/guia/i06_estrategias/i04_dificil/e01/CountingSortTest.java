package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CountingSortTest {

    @Test
    @DisplayName("ordena un arreglo con valores repetidos")
    void ordenaConDuplicados() {
        int[] datos = {4, 2, 2, 8, 3, 3, 1, 0, 7};
        int[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, CountingSort.ordenar(datos, 8));
    }

    @Test
    @DisplayName("arreglo vacio devuelve arreglo vacio")
    void arregloVacio() {
        assertArrayEquals(new int[0], CountingSort.ordenar(new int[0], 0));
    }

    @Test
    @DisplayName("un solo elemento")
    void unElemento() {
        assertArrayEquals(new int[]{5}, CountingSort.ordenar(new int[]{5}, 10));
    }

    @Test
    @DisplayName("coincide con Arrays.sort en varios casos")
    void coincideConArraysSort() {
        int[][] casos = {
                {0, 0, 0, 0},
                {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                {1},
                {3, 1, 4, 1, 5, 9, 2, 6}
        };
        for (int[] caso : casos) {
            int maximo = Arrays.stream(caso).max().orElse(0);
            int[] esperado = caso.clone();
            Arrays.sort(esperado);
            assertArrayEquals(esperado, CountingSort.ordenar(caso, maximo));
        }
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] datos = {3, 1, 2};
        CountingSort.ordenar(datos, 3);
        assertArrayEquals(new int[]{3, 1, 2}, datos);
    }

    @Test
    @DisplayName("valor fuera de rango lanza excepcion")
    void fueraDeRango() {
        assertThrows(IllegalArgumentException.class,
                () -> CountingSort.ordenar(new int[]{1, 2, 9}, 5));
    }
}
