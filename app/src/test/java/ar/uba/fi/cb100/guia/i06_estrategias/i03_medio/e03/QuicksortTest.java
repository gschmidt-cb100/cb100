package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuicksortTest {

    @Test
    @DisplayName("arreglo vacio y de un elemento")
    void bordes() {
        assertArrayEquals(new int[]{}, Quicksort.ordenar(new int[]{}));
        assertArrayEquals(new int[]{7}, Quicksort.ordenar(new int[]{7}));
    }

    @Test
    @DisplayName("ordena arreglos ya ordenados e invertidos")
    void ordenadosEInvertidos() {
        assertArrayEquals(new int[]{1, 2, 3, 4}, Quicksort.ordenar(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{1, 2, 3, 4}, Quicksort.ordenar(new int[]{4, 3, 2, 1}));
    }

    @Test
    @DisplayName("no modifica el arreglo original")
    void noModificaOriginal() {
        int[] original = {3, 1, 2};
        Quicksort.ordenar(original);
        assertArrayEquals(new int[]{3, 1, 2}, original);
    }

    @Test
    @DisplayName("coincide con Arrays.sort sobre arreglos aleatorios con duplicados")
    void coincideConArraysSort() {
        Random r = new Random(7);
        for (int caso = 0; caso < 100; caso++) {
            int[] a = new int[r.nextInt(60)];
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(20) - 10; // rango chico => muchos duplicados
            }
            int[] esperado = Arrays.copyOf(a, a.length);
            Arrays.sort(esperado);
            assertArrayEquals(esperado, Quicksort.ordenar(a));
        }
    }
}
