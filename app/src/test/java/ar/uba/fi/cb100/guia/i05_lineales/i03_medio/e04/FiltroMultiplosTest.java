package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroMultiplosTest {

    @Test
    @DisplayName("Quita los múltiplos de 3 (incluye el 0)")
    void quitaMultiplos() {
        List<Integer> entrada = List.of(0, 1, 2, 3, 4, 5, 6, 9, 10);
        assertEquals(List.of(1, 2, 4, 5, 10), FiltroMultiplos.sinMultiplosDe3(entrada));
    }

    @Test
    @DisplayName("Contempla múltiplos negativos de 3")
    void multiplosNegativos() {
        List<Integer> entrada = List.of(-3, -2, -6, 7);
        assertEquals(List.of(-2, 7), FiltroMultiplos.sinMultiplosDe3(entrada));
    }

    @Test
    @DisplayName("No modifica la lista original")
    void noModificaOriginal() {
        List<Integer> original = new ArrayList<>(List.of(3, 4, 6));
        FiltroMultiplos.sinMultiplosDe3(original);
        assertEquals(List.of(3, 4, 6), original);
    }

    @Test
    @DisplayName("Lista vacía devuelve lista vacía")
    void listaVacia() {
        assertTrue(FiltroMultiplos.sinMultiplosDe3(List.of()).isEmpty());
    }

    @Test
    @DisplayName("Todos múltiplos de 3 devuelve lista vacía")
    void todosMultiplos() {
        assertTrue(FiltroMultiplos.sinMultiplosDe3(List.of(3, 6, 9, 12)).isEmpty());
    }
}
