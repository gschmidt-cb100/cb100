package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubconjuntosTest {

    @Test
    @DisplayName("un arreglo de 3 elementos genera 2^3 = 8 subconjuntos")
    void cantidadCorrecta() {
        assertEquals(8, Subconjuntos.subconjuntos(new int[]{1, 2, 3}).size());
    }

    @Test
    @DisplayName("el resultado contiene el subconjunto vacío y el total")
    void contieneVacioYTotal() {
        List<List<Integer>> todos = Subconjuntos.subconjuntos(new int[]{4, 7});
        assertTrue(todos.contains(List.of()), "falta el subconjunto vacio");
        assertTrue(todos.contains(List.of(4, 7)), "falta el subconjunto total");
    }

    @Test
    @DisplayName("no hay subconjuntos repetidos")
    void sinRepetidos() {
        List<List<Integer>> todos = Subconjuntos.subconjuntos(new int[]{1, 2, 3, 4});
        assertEquals(16, todos.size());
        assertEquals(16, new HashSet<>(todos).size());
    }

    @Test
    @DisplayName("el arreglo vacío tiene un único subconjunto: el vacío")
    void arregloVacio() {
        assertEquals(List.of(List.of()), Subconjuntos.subconjuntos(new int[]{}));
    }

    @Test
    @DisplayName("con un elemento salen exactamente {} y {5}")
    void unElemento() {
        List<List<Integer>> todos = Subconjuntos.subconjuntos(new int[]{5});
        assertEquals(2, todos.size());
        assertTrue(todos.contains(List.of()));
        assertTrue(todos.contains(List.of(5)));
    }
}
