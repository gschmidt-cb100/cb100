package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContieneTodosTest {

    @Test
    @DisplayName("contiene todos los requeridos")
    void contieneTodos() {
        assertTrue(ContieneTodos.contieneTodos(List.of(1, 2, 3, 4, 5), List.of(2, 4)));
    }

    @Test
    @DisplayName("falta un requerido devuelve false")
    void faltaUno() {
        assertFalse(ContieneTodos.contieneTodos(List.of(1, 2, 3, 4, 5), List.of(2, 9)));
    }

    @Test
    @DisplayName("requeridos vacios siempre devuelve true")
    void requeridosVacios() {
        assertTrue(ContieneTodos.contieneTodos(List.of(1, 2, 3), List.of()));
    }

    @Test
    @DisplayName("lista vacia con requeridos devuelve false")
    void listaVaciaConRequeridos() {
        assertFalse(ContieneTodos.contieneTodos(List.of(), List.of(1)));
    }
}
