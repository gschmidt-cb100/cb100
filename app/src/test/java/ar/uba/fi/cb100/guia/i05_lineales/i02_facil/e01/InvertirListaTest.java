package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvertirListaTest {

    @Test
    @DisplayName("invierte una lista de varios elementos")
    void invierteVariosElementos() {
        assertEquals(List.of(5, 4, 3, 2, 1), InvertirLista.invertir(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    @DisplayName("lista vacia devuelve lista vacia")
    void listaVacia() {
        assertEquals(List.of(), InvertirLista.invertir(List.of()));
    }

    @Test
    @DisplayName("un solo elemento se mantiene igual")
    void unSoloElemento() {
        assertEquals(List.of(7), InvertirLista.invertir(List.of(7)));
    }

    @Test
    @DisplayName("no modifica la lista original")
    void noModificaOriginal() {
        List<Integer> original = new java.util.ArrayList<>(List.of(1, 2, 3));
        InvertirLista.invertir(original);
        assertEquals(List.of(1, 2, 3), original);
    }
}
