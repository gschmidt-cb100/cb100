package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubconjuntosTest {

    @Test
    @DisplayName("hay 2^n subconjuntos")
    void cantidadCorrecta() {
        assertEquals(8, Subconjuntos.subconjuntos(List.of(1, 2, 3)).size());
        assertEquals(16, Subconjuntos.subconjuntos(List.of(1, 2, 3, 4)).size());
    }

    @Test
    @DisplayName("incluye el conjunto vacio y el conjunto total")
    void incluyeVacioYTotal() {
        List<Integer> datos = List.of(1, 2, 3);
        List<List<Integer>> todos = Subconjuntos.subconjuntos(datos);
        assertTrue(todos.contains(List.of()), "deberia contener el vacio");
        assertTrue(todos.contains(List.of(1, 2, 3)), "deberia contener el total");
    }

    @Test
    @DisplayName("lista vacia produce solo el subconjunto vacio")
    void listaVacia() {
        List<List<Integer>> todos = Subconjuntos.subconjuntos(List.of());
        assertEquals(1, todos.size());
        assertEquals(List.of(), todos.get(0));
    }

    @Test
    @DisplayName("no hay subconjuntos repetidos")
    void sinRepetidos() {
        List<List<Integer>> todos = Subconjuntos.subconjuntos(List.of(1, 2, 3));
        long distintos = todos.stream().distinct().count();
        assertEquals(todos.size(), distintos);
    }
}
