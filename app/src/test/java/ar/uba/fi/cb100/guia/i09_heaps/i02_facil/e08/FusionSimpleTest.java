package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FusionSimpleTest {

    @Test
    @DisplayName("fusiona dos listas ordenadas intercalando sus valores")
    void fusionaIntercalando() {
        assertEquals(List.of(1, 2, 3, 4, 9, 10),
                FusionSimple.fusionar(List.of(1, 4, 9), List.of(2, 3, 10)));
    }

    @Test
    @DisplayName("si una lista es vacia devuelve la otra")
    void conUnaVacia() {
        assertEquals(List.of(5, 6, 7),
                FusionSimple.fusionar(List.of(), List.of(5, 6, 7)));
    }

    @Test
    @DisplayName("dos listas vacias dan una lista vacia")
    void ambasVacias() {
        assertTrue(FusionSimple.fusionar(List.of(), List.of()).isEmpty());
    }

    @Test
    @DisplayName("conserva los valores repetidos entre ambas listas")
    void conservaRepetidos() {
        assertEquals(List.of(1, 2, 2, 3),
                FusionSimple.fusionar(List.of(2, 3), List.of(1, 2)));
    }
}
