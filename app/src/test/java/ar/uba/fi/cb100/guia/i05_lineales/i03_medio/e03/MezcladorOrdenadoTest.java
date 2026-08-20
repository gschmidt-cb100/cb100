package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MezcladorOrdenadoTest {

    @Test
    @DisplayName("Mezcla dos listas ordenadas intercaladas")
    void mezclaBasica() {
        List<Integer> a = List.of(1, 3, 5);
        List<Integer> b = List.of(2, 4, 6);
        assertEquals(List.of(1, 2, 3, 4, 5, 6), MezcladorOrdenado.mezclarOrdenadas(a, b));
    }

    @Test
    @DisplayName("Mezcla con listas de distinto tamaño")
    void distintoTamanio() {
        List<Integer> a = List.of(1, 2, 3, 10);
        List<Integer> b = List.of(4);
        assertEquals(List.of(1, 2, 3, 4, 10), MezcladorOrdenado.mezclarOrdenadas(a, b));
    }

    @Test
    @DisplayName("Conserva duplicados entre ambas listas")
    void conservaDuplicados() {
        List<Integer> a = List.of(1, 2, 2);
        List<Integer> b = List.of(2, 3);
        assertEquals(List.of(1, 2, 2, 2, 3), MezcladorOrdenado.mezclarOrdenadas(a, b));
    }

    @Test
    @DisplayName("Una lista vacía devuelve la otra")
    void unaVacia() {
        assertEquals(List.of(1, 2, 3),
                MezcladorOrdenado.mezclarOrdenadas(List.of(), List.of(1, 2, 3)));
        assertEquals(List.of(1, 2, 3),
                MezcladorOrdenado.mezclarOrdenadas(List.of(1, 2, 3), List.of()));
    }

    @Test
    @DisplayName("Ambas vacías devuelve lista vacía")
    void ambasVacias() {
        assertTrue(MezcladorOrdenado.mezclarOrdenadas(List.of(), List.of()).isEmpty());
    }
}
