package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombinacionesTest {

    /** Numero combinatorio C(n, k) para verificar cantidades. */
    private static long combinatorio(int n, int k) {
        long resultado = 1;
        for (int i = 0; i < k; i++) {
            resultado = resultado * (n - i) / (i + 1);
        }
        return resultado;
    }

    @Test
    @DisplayName("cantidad C(n,k) correcta")
    void cantidadCorrecta() {
        assertEquals(combinatorio(4, 2),
                Combinaciones.combinaciones(List.of(1, 2, 3, 4), 2).size());
        assertEquals(combinatorio(5, 3),
                Combinaciones.combinaciones(List.of(1, 2, 3, 4, 5), 3).size());
        assertEquals(combinatorio(6, 0),
                Combinaciones.combinaciones(List.of(1, 2, 3, 4, 5, 6), 0).size());
    }

    @Test
    @DisplayName("k = 0 devuelve solo la combinacion vacia")
    void kCero() {
        List<List<Integer>> todas = Combinaciones.combinaciones(List.of(1, 2, 3), 0);
        assertEquals(1, todas.size());
        assertEquals(List.of(), todas.get(0));
    }

    @Test
    @DisplayName("k = n devuelve una sola combinacion con todos")
    void kIgualN() {
        List<List<Integer>> todas = Combinaciones.combinaciones(List.of(1, 2, 3), 3);
        assertEquals(1, todas.size());
        assertEquals(List.of(1, 2, 3), todas.get(0));
    }

    @Test
    @DisplayName("cada combinacion esta ordenada y sin repetidos entre si")
    void combinacionesValidas() {
        List<List<Integer>> todas = Combinaciones.combinaciones(List.of(1, 2, 3, 4), 2);
        long distintas = todas.stream().distinct().count();
        assertEquals(todas.size(), distintas);
        for (List<Integer> comb : todas) {
            assertEquals(2, comb.size());
            assertTrue(comb.get(0) < comb.get(1), "deberia estar en orden creciente");
        }
    }

    @Test
    @DisplayName("k fuera de rango lanza excepcion")
    void kInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> Combinaciones.combinaciones(List.of(1, 2), 5));
        assertThrows(IllegalArgumentException.class,
                () -> Combinaciones.combinaciones(List.of(1, 2), -1));
    }
}
