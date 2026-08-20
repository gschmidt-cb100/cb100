package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MergesortListaTest {

    @Test
    @DisplayName("ordena una lista desordenada")
    void ordenaListaDesordenada() {
        Nodo lista = MergesortLista.desdeArreglo(new int[]{5, 2, 9, 1, 5, 6, 3});
        Nodo ordenada = MergesortLista.ordenar(lista);
        assertArrayEquals(new int[]{1, 2, 3, 5, 5, 6, 9}, MergesortLista.aArreglo(ordenada));
    }

    @Test
    @DisplayName("lista vacia y de un elemento")
    void casosBase() {
        assertNull(MergesortLista.ordenar(null));
        Nodo unico = MergesortLista.desdeArreglo(new int[]{42});
        assertArrayEquals(new int[]{42}, MergesortLista.aArreglo(MergesortLista.ordenar(unico)));
    }

    @Test
    @DisplayName("coincide con Arrays.sort en varios casos")
    void coincideConArraysSort() {
        int[][] casos = {
                {3, 1, 2},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {-5, 3, -1, 0, 8, -2},
                {4, 4, 4, 4},
                {1, 2, 3, 4, 5}
        };
        for (int[] caso : casos) {
            int[] esperado = caso.clone();
            Arrays.sort(esperado);
            Nodo ordenada = MergesortLista.ordenar(MergesortLista.desdeArreglo(caso));
            assertArrayEquals(esperado, MergesortLista.aArreglo(ordenada));
        }
    }

    @Test
    @DisplayName("la lista ordenada conserva la cantidad de nodos")
    void conservaCantidad() {
        int[] datos = {8, 3, 7, 3, 9, 1};
        Nodo ordenada = MergesortLista.ordenar(MergesortLista.desdeArreglo(datos));
        assertEquals(datos.length, MergesortLista.aArreglo(ordenada).length);
    }
}
