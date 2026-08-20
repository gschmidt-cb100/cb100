package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TopKTest {

    @Test
    @DisplayName("Los 3 mayores de un stream chico, en orden descendente")
    void casoChico() {
        List<Integer> datos = List.of(4, 1, 7, 3, 8, 5);
        assertEquals(List.of(8, 7, 5), TopK.topK(datos.iterator(), 3));
    }

    @Test
    @DisplayName("Con 1000 valores pseudoaleatorios coincide con ordenar todo")
    void streamGrandeDeterministico() {
        // Semilla fija: la secuencia es siempre la misma.
        Random azar = new Random(42);
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            datos.add(azar.nextInt(10_000));
        }
        // Referencia "fuerza bruta": ordenar todo y quedarse con los 10 primeros.
        List<Integer> esperado = new ArrayList<>(datos);
        esperado.sort(Comparator.reverseOrder());
        esperado = esperado.subList(0, 10);

        assertEquals(esperado, TopK.topK(datos.iterator(), 10));
    }

    @Test
    @DisplayName("Si el stream tiene menos de k elementos devuelve todos")
    void streamMasCortoQueK() {
        List<Integer> datos = List.of(2, 9);
        assertEquals(List.of(9, 2), TopK.topK(datos.iterator(), 5));
    }

    @Test
    @DisplayName("Con duplicados los repite tantas veces como aparecen")
    void conDuplicados() {
        List<Integer> datos = List.of(5, 5, 5, 1, 2);
        assertEquals(List.of(5, 5, 5), TopK.topK(datos.iterator(), 3));
    }

    @Test
    @DisplayName("k = 0 no tiene sentido y lanza IllegalArgumentException")
    void kInvalido() {
        List<Integer> datos = List.of(1, 2, 3);
        assertThrows(IllegalArgumentException.class,
                () -> TopK.topK(datos.iterator(), 0));
    }
}
