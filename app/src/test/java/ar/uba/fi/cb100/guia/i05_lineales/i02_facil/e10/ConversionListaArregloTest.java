package ar.uba.fi.cb100.guia.i05_lineales.i02_facil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversionListaArregloTest {

    @Test
    @DisplayName("convierte lista a arreglo")
    void listaAArreglo() {
        assertArrayEquals(new int[]{1, 2, 3}, ConversionListaArreglo.aArreglo(List.of(1, 2, 3)));
    }

    @Test
    @DisplayName("convierte arreglo a lista")
    void arregloALista() {
        assertEquals(List.of(1, 2, 3), ConversionListaArreglo.aLista(new int[]{1, 2, 3}));
    }

    @Test
    @DisplayName("lista vacia a arreglo vacio")
    void listaVaciaAArreglo() {
        assertArrayEquals(new int[]{}, ConversionListaArreglo.aArreglo(List.of()));
    }

    @Test
    @DisplayName("arreglo vacio a lista vacia")
    void arregloVacioALista() {
        assertEquals(List.of(), ConversionListaArreglo.aLista(new int[]{}));
    }

    @Test
    @DisplayName("ida y vuelta conserva los elementos")
    void idaYVuelta() {
        List<Integer> original = List.of(5, 10, 15);
        assertEquals(original, ConversionListaArreglo.aLista(ConversionListaArreglo.aArreglo(original)));
    }
}
