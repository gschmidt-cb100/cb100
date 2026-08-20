package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximosTest {

    @Test
    @DisplayName("maximo devuelve el mayor elemento")
    void maximoDevuelveElMayor() {
        assertEquals(8, Maximos.maximo(new int[]{3, 8, 1, 5}));
    }

    @Test
    @DisplayName("maximo de un arreglo vacio lanza IllegalArgumentException")
    void maximoDeVacioFalla() {
        assertThrows(IllegalArgumentException.class, () -> Maximos.maximo(new int[]{}));
    }

    @Test
    @DisplayName("enOrdenDescendente ordena de mayor a menor")
    void descendenteOrdena() {
        assertEquals(List.of(9, 7, 4, 2),
                Maximos.enOrdenDescendente(new int[]{4, 9, 2, 7}));
    }

    @Test
    @DisplayName("enOrdenDescendente conserva los repetidos")
    void descendenteConRepetidos() {
        assertEquals(List.of(6, 6, 1),
                Maximos.enOrdenDescendente(new int[]{6, 1, 6}));
    }

    @Test
    @DisplayName("maximo funciona con todos negativos")
    void maximoConNegativos() {
        assertEquals(-2, Maximos.maximo(new int[]{-9, -2, -7}));
    }
}
