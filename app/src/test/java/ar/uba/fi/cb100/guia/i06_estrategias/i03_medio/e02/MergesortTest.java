package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergesortTest {

    @Test
    @DisplayName("arreglo vacio y de un elemento")
    void bordes() {
        assertArrayEquals(new int[]{}, Mergesort.ordenar(new int[]{}));
        assertArrayEquals(new int[]{7}, Mergesort.ordenar(new int[]{7}));
    }

    @Test
    @DisplayName("ordena un arreglo con duplicados")
    void conDuplicados() {
        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 5, 9},
                Mergesort.ordenar(new int[]{5, 2, 9, 1, 5, 2, 3}));
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] original = {3, 1, 2};
        Mergesort.ordenar(original);
        assertArrayEquals(new int[]{3, 1, 2}, original);
    }

    @Test
    @DisplayName("coincide con Arrays.sort sobre arreglos aleatorios")
    void coincideConArraysSort() {
        Random r = new Random(42);
        for (int caso = 0; caso < 100; caso++) {
            int[] a = new int[r.nextInt(50)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(200) - 100;
            }
            int[] esperado = Arrays.copyOf(a, a.length);
            Arrays.sort(esperado);
            assertArrayEquals(esperado, Mergesort.ordenar(a));
        }
    }
}
