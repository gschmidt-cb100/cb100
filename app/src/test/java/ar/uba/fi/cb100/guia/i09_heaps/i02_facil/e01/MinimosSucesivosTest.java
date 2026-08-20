package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinimosSucesivosTest {

    @Test
    @DisplayName("desencolar de a uno devuelve los elementos ordenados")
    void saleOrdenado() {
        assertEquals(List.of(2, 2, 5, 7, 9),
                MinimosSucesivos.enOrden(new int[]{7, 2, 9, 2, 5}));
    }

    @Test
    @DisplayName("un arreglo ya ordenado queda igual")
    void yaOrdenadoQuedaIgual() {
        assertEquals(List.of(1, 3, 8),
                MinimosSucesivos.enOrden(new int[]{1, 3, 8}));
    }

    @Test
    @DisplayName("un arreglo vacio da una lista vacia")
    void vacioDaVacio() {
        assertTrue(MinimosSucesivos.enOrden(new int[]{}).isEmpty());
    }

    @Test
    @DisplayName("funciona con numeros negativos")
    void conNegativos() {
        assertEquals(List.of(-5, -1, 0, 4),
                MinimosSucesivos.enOrden(new int[]{0, -5, 4, -1}));
    }
}
