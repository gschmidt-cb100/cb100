package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SinDuplicadosTest {

    @Test
    @DisplayName("elimina duplicados preservando el orden de aparicion")
    void eliminaDuplicados() {
        assertEquals(List.of(3, 1, 2, 5), SinDuplicados.sinDuplicados(List.of(3, 1, 3, 2, 1, 5)));
    }

    @Test
    @DisplayName("lista sin repetidos queda igual")
    void sinRepetidos() {
        assertEquals(List.of(1, 2, 3), SinDuplicados.sinDuplicados(List.of(1, 2, 3)));
    }

    @Test
    @DisplayName("lista vacia devuelve lista vacia")
    void listaVacia() {
        assertEquals(List.of(), SinDuplicados.sinDuplicados(List.of()));
    }

    @Test
    @DisplayName("todos iguales deja un solo elemento")
    void todosIguales() {
        assertEquals(List.of(9), SinDuplicados.sinDuplicados(List.of(9, 9, 9, 9)));
    }
}
