package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermutacionesTest {

    @Test
    @DisplayName("hay n! permutaciones")
    void cantidadFactorial() {
        assertEquals(6, Permutaciones.permutaciones(List.of(1, 2, 3)).size());
        assertEquals(24, Permutaciones.permutaciones(List.of(1, 2, 3, 4)).size());
        assertEquals(1, Permutaciones.permutaciones(List.of(7)).size());
    }

    @Test
    @DisplayName("no hay permutaciones repetidas (elementos distintos)")
    void sinRepetidas() {
        List<List<Integer>> todas = Permutaciones.permutaciones(List.of(1, 2, 3, 4));
        long distintas = todas.stream().distinct().count();
        assertEquals(todas.size(), distintas);
    }

    @Test
    @DisplayName("cada permutacion contiene exactamente los elementos originales")
    void mismosElementos() {
        List<Integer> datos = List.of(5, 6, 7);
        for (List<Integer> perm : Permutaciones.permutaciones(datos)) {
            assertEquals(datos.size(), perm.size());
            assertTrue(perm.containsAll(datos));
        }
    }

    @Test
    @DisplayName("lista vacia produce una sola permutacion (la vacia)")
    void listaVacia() {
        List<List<Integer>> todas = Permutaciones.permutaciones(List.of());
        assertEquals(1, todas.size());
        assertEquals(List.of(), todas.get(0));
    }
}
