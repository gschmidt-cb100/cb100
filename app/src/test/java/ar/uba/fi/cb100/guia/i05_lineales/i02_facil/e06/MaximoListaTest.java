package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximoListaTest {

    @Test
    @DisplayName("maximo de una lista de varios elementos")
    void maximoVarios() {
        assertEquals(9, MaximoLista.maximo(List.of(3, 9, 1, 7, 4)));
    }

    @Test
    @DisplayName("maximo con un solo elemento")
    void maximoUno() {
        assertEquals(5, MaximoLista.maximo(List.of(5)));
    }

    @Test
    @DisplayName("maximo con numeros negativos")
    void maximoNegativos() {
        assertEquals(-1, MaximoLista.maximo(List.of(-5, -1, -3)));
    }

    @Test
    @DisplayName("lista vacia lanza IllegalArgumentException")
    void listaVaciaLanza() {
        assertThrows(IllegalArgumentException.class, () -> MaximoLista.maximo(List.of()));
    }
}
