package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BucketSortTest {

    @Test
    @DisplayName("ordena reales en [0,1) igual que Arrays.sort")
    void ordenaIgualQueArraysSort() {
        double[] datos = {0.42, 0.11, 0.99, 0.01, 0.5, 0.73, 0.23};
        double[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, BucketSort.ordenar(datos), 1e-12);
    }

    @Test
    @DisplayName("arreglo vacio")
    void arregloVacio() {
        assertArrayEquals(new double[0], BucketSort.ordenar(new double[0]), 1e-12);
    }

    @Test
    @DisplayName("valores repetidos que caen en la misma cubeta")
    void valoresRepetidos() {
        double[] datos = {0.5, 0.5, 0.5, 0.25, 0.25};
        double[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, BucketSort.ordenar(datos), 1e-12);
    }

    @Test
    @DisplayName("incluye el 0 (limite inferior) y valores cercanos a 1")
    void limites() {
        double[] datos = {0.0, 0.9999, 0.0001, 0.5};
        double[] esperado = datos.clone();
        Arrays.sort(esperado);
        assertArrayEquals(esperado, BucketSort.ordenar(datos), 1e-12);
    }

    @Test
    @DisplayName("valor fuera de [0,1) lanza excepcion")
    void fueraDeRango() {
        assertThrows(IllegalArgumentException.class,
                () -> BucketSort.ordenar(new double[]{0.5, 1.0}));
        assertThrows(IllegalArgumentException.class,
                () -> BucketSort.ordenar(new double[]{-0.1, 0.5}));
    }
}
