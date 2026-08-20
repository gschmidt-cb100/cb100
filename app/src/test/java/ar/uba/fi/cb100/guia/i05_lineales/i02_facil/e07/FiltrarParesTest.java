package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltrarParesTest {

    @Test
    @DisplayName("filtra los pares de una lista mixta")
    void filtraPares() {
        assertEquals(List.of(2, 4, 6), FiltrarPares.pares(List.of(1, 2, 3, 4, 5, 6)));
    }

    @Test
    @DisplayName("sin pares devuelve lista vacia")
    void sinPares() {
        assertEquals(List.of(), FiltrarPares.pares(List.of(1, 3, 5)));
    }

    @Test
    @DisplayName("lista vacia devuelve lista vacia")
    void listaVacia() {
        assertEquals(List.of(), FiltrarPares.pares(List.of()));
    }

    @Test
    @DisplayName("el cero se considera par")
    void ceroEsPar() {
        assertEquals(List.of(0, -2), FiltrarPares.pares(List.of(0, 1, -2, 3)));
    }
}
