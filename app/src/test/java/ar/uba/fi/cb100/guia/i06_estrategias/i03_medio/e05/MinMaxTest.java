package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxTest {

    @Test
    @DisplayName("arreglo de un solo elemento: min = max")
    void unElemento() {
        assertArrayEquals(new int[]{4, 4}, MinMax.minMax(new int[]{4}));
    }

    @Test
    @DisplayName("arreglo de dos elementos")
    void dosElementos() {
        assertArrayEquals(new int[]{2, 9}, MinMax.minMax(new int[]{9, 2}));
        assertArrayEquals(new int[]{2, 9}, MinMax.minMax(new int[]{2, 9}));
    }

    @Test
    @DisplayName("arreglo general con negativos")
    void general() {
        assertArrayEquals(new int[]{-7, 9}, MinMax.minMax(new int[]{5, -7, 9, 1, 0, 3}));
    }

    @Test
    @DisplayName("arreglo vacio lanza IllegalArgumentException")
    void vacioLanza() {
        assertThrows(IllegalArgumentException.class, () -> MinMax.minMax(new int[]{}));
    }

    @Test
    @DisplayName("coincide con el recorrido lineal en arreglos aleatorios")
    void coincideConLineal() {
        Random r = new Random(123);
        for (int caso = 0; caso < 200; caso++) {
            int[] a = new int[1 + r.nextInt(40)];
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < a.length; i++) {
                a[i] = r.nextInt(1000) - 500;
                min = Math.min(min, a[i]);
                max = Math.max(max, a[i]);
            }
            assertArrayEquals(new int[]{min, max}, MinMax.minMax(a));
        }
    }
}
