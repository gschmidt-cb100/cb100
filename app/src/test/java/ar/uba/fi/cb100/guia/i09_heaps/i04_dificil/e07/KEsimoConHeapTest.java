package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class KEsimoConHeapTest {

    @Test
    @DisplayName("k = 1 devuelve el minimo y k = n devuelve el maximo")
    void extremos() {
        int[] valores = {12, 5, 9, 3, 20, 7};
        assertEquals(3, KEsimoConHeap.kEsimoMenor(valores, 1));
        assertEquals(20, KEsimoConHeap.kEsimoMenor(valores, valores.length));
    }

    @Test
    @DisplayName("Un k intermedio devuelve el valor que quedaria en esa posicion al ordenar")
    void kIntermedio() {
        int[] valores = {12, 5, 9, 3, 20, 7};
        // Ordenado: 3, 5, 7, 9, 12, 20.
        assertEquals(7, KEsimoConHeap.kEsimoMenor(valores, 3));
        assertEquals(9, KEsimoConHeap.kEsimoMenor(valores, 4));
    }

    @Test
    @DisplayName("Los duplicados cuentan por posicion: en {7,7,1} el 2do menor es 7")
    void conDuplicados() {
        assertEquals(7, KEsimoConHeap.kEsimoMenor(new int[] {7, 7, 1}, 2));
        assertEquals(3, KEsimoConHeap.kEsimoMenor(new int[] {5, 3, 5, 3, 5}, 2));
    }

    @Test
    @DisplayName("Coincide con ordenar y indexar, sobre un arreglo aleatorio de semilla fija")
    void contraOrdenar() {
        Random azar = new Random(7);
        int[] valores = new int[200];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = azar.nextInt(1000);
        }
        int[] ordenado = valores.clone();
        Arrays.sort(ordenado);
        for (int k : new int[] {1, 2, 50, 100, 199, 200}) {
            assertEquals(ordenado[k - 1], KEsimoConHeap.kEsimoMenor(valores, k));
        }
    }

    @Test
    @DisplayName("kEsimoMenor no modifica el arreglo original")
    void noModificaElOriginal() {
        int[] valores = {12, 5, 9, 3, 20, 7};
        KEsimoConHeap.kEsimoMenor(valores, 4);
        assertArrayEquals(new int[] {12, 5, 9, 3, 20, 7}, valores);
    }

    @Test
    @DisplayName("k fuera de rango o arreglo null lanzan IllegalArgumentException")
    void argumentosInvalidos() {
        int[] valores = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> KEsimoConHeap.kEsimoMenor(valores, 0));
        assertThrows(IllegalArgumentException.class, () -> KEsimoConHeap.kEsimoMenor(valores, 4));
        assertThrows(IllegalArgumentException.class, () -> KEsimoConHeap.kEsimoMenor(null, 1));
    }
}
