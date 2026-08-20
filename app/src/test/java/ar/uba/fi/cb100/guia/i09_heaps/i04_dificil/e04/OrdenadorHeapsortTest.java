package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class OrdenadorHeapsortTest {

    @Test
    @DisplayName("Coincide con Arrays.sort sobre un arreglo aleatorio de semilla fija")
    void aleatorioConSemillaFija() {
        Random azar = new Random(42); // Semilla fija: el test es reproducible.
        int[] valores = new int[500];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = azar.nextInt(10_000) - 5_000; // Incluye negativos.
        }
        int[] esperado = valores.clone();
        Arrays.sort(esperado);

        OrdenadorHeapsort.ordenar(valores);
        assertArrayEquals(esperado, valores);
    }

    @Test
    @DisplayName("Un arreglo ya ordenado queda igual")
    void yaOrdenado() {
        int[] valores = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        OrdenadorHeapsort.ordenar(valores);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, valores);
    }

    @Test
    @DisplayName("Un arreglo invertido (peor caso intuitivo) queda ordenado")
    void invertido() {
        int[] valores = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        OrdenadorHeapsort.ordenar(valores);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, valores);
    }

    @Test
    @DisplayName("Con repetidos coincide con Arrays.sort")
    void conRepetidos() {
        int[] valores = {5, 1, 5, 3, 1, 5, 3, 3, 1, 5};
        int[] esperado = valores.clone();
        Arrays.sort(esperado);
        OrdenadorHeapsort.ordenar(valores);
        assertArrayEquals(esperado, valores);
    }

    @Test
    @DisplayName("Los casos borde (vacio y de un elemento) no fallan")
    void casosBorde() {
        int[] vacio = {};
        OrdenadorHeapsort.ordenar(vacio);
        assertArrayEquals(new int[] {}, vacio);

        int[] unitario = {7};
        OrdenadorHeapsort.ordenar(unitario);
        assertArrayEquals(new int[] {7}, unitario);
    }
}
