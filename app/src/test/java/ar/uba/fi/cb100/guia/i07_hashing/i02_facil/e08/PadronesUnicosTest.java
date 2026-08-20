package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PadronesUnicosTest {

    @Test
    @DisplayName("cuenta los distintos ignorando repetidos")
    void conRepetidos() {
        assertEquals(3, PadronesUnicos.cantidadDistintos(
                List.of(110001, 110002, 110001, 110003, 110002)));
    }

    @Test
    @DisplayName("lista vacia da 0")
    void listaVacia() {
        assertEquals(0, PadronesUnicos.cantidadDistintos(List.of()));
    }

    @Test
    @DisplayName("todos iguales da 1")
    void todosIguales() {
        assertEquals(1, PadronesUnicos.cantidadDistintos(List.of(7, 7, 7, 7)));
    }

    @Test
    @DisplayName("sin repetidos da el tamanio de la lista")
    void sinRepetidos() {
        assertEquals(4, PadronesUnicos.cantidadDistintos(List.of(1, 2, 3, 4)));
    }
}
