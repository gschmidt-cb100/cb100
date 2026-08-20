package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    @DisplayName("ordena numeros de distinta cantidad de digitos")
    void ordenaDistintosDigitos() {
        int[] datos = {170, 45, 75, 90, 802, 24, 2, 66};
        int[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, RadixSort.ordenar(datos));
    }

    @Test
    @DisplayName("arreglo vacio")
    void arregloVacio() {
        assertArrayEquals(new int[0], RadixSort.ordenar(new int[0]));
    }

    @Test
    @DisplayName("con ceros y duplicados")
    void cerosYDuplicados() {
        int[] datos = {0, 0, 100, 100, 5, 5, 999};
        int[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, RadixSort.ordenar(datos));
    }

    @Test
    @DisplayName("coincide con Arrays.sort en varios casos")
    void coincideConArraysSort() {
        int[][] casos = {
                {1},
                {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                {12345, 6789, 1, 54321, 999},
                {3, 3, 3, 3}
        };
        for (int[] caso : casos) {
            int[] esperado = caso.clone();
            Arrays.sort(esperado);
            assertArrayEquals(esperado, RadixSort.ordenar(caso));
        }
    }

    @Test
    @DisplayName("negativos lanzan excepcion")
    void negativosLanzan() {
        assertThrows(IllegalArgumentException.class,
                () -> RadixSort.ordenar(new int[]{1, -2, 3}));
    }
}
