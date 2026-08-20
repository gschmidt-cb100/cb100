package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RotarDerechaTest {

    @Test
    @DisplayName("rota k posiciones a la derecha")
    void rotaNormal() {
        assertEquals(List.of(4, 5, 1, 2, 3), RotarDerecha.rotarDerecha(List.of(1, 2, 3, 4, 5), 2));
    }

    @Test
    @DisplayName("k mayor al tamano se normaliza")
    void kMayorAlTamano() {
        assertEquals(List.of(4, 5, 1, 2, 3), RotarDerecha.rotarDerecha(List.of(1, 2, 3, 4, 5), 7));
    }

    @Test
    @DisplayName("k igual a cero deja la lista igual")
    void kCero() {
        assertEquals(List.of(1, 2, 3), RotarDerecha.rotarDerecha(List.of(1, 2, 3), 0));
    }

    @Test
    @DisplayName("k multiplo del tamano deja la lista igual")
    void kMultiploDelTamano() {
        assertEquals(List.of(1, 2, 3), RotarDerecha.rotarDerecha(List.of(1, 2, 3), 6));
    }

    @Test
    @DisplayName("lista vacia devuelve lista vacia")
    void listaVacia() {
        assertEquals(List.of(), RotarDerecha.rotarDerecha(List.of(), 3));
    }

    @Test
    @DisplayName("no modifica la lista original")
    void noModificaOriginal() {
        List<Integer> original = new java.util.ArrayList<>(List.of(1, 2, 3));
        RotarDerecha.rotarDerecha(original, 1);
        assertEquals(List.of(1, 2, 3), original);
    }
}
