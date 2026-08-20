package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InsercionOrdenadaTest {

    @Test
    @DisplayName("Inserta en el medio manteniendo el orden")
    void insertaEnMedio() {
        assertEquals(List.of(1, 2, 3, 4, 5),
                InsercionOrdenada.insertarOrdenado(List.of(1, 2, 4, 5), 3));
    }

    @Test
    @DisplayName("Inserta al principio cuando es el menor")
    void insertaAlPrincipio() {
        assertEquals(List.of(0, 1, 2, 3),
                InsercionOrdenada.insertarOrdenado(List.of(1, 2, 3), 0));
    }

    @Test
    @DisplayName("Inserta al final cuando es el mayor")
    void insertaAlFinal() {
        assertEquals(List.of(1, 2, 3, 9),
                InsercionOrdenada.insertarOrdenado(List.of(1, 2, 3), 9));
    }

    @Test
    @DisplayName("Inserta en lista vacía")
    void insertaEnVacia() {
        assertEquals(List.of(7), InsercionOrdenada.insertarOrdenado(List.of(), 7));
    }

    @Test
    @DisplayName("Admite duplicados y no modifica la lista original")
    void duplicadosSinModificar() {
        List<Integer> original = new ArrayList<>(List.of(1, 2, 2, 3));
        assertEquals(List.of(1, 2, 2, 2, 3),
                InsercionOrdenada.insertarOrdenado(original, 2));
        assertEquals(List.of(1, 2, 2, 3), original);
    }
}
