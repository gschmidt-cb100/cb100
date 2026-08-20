package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximoAlFinalTest {

    @Test
    @DisplayName("Mueve el máximo al final conservando el resto en orden")
    void maximoAlFinalBasico() {
        assertEquals(List.of(3, 1, 4, 2, 9),
                MaximoAlFinal.maximoAlFinal(List.of(3, 9, 1, 4, 2)));
    }

    @Test
    @DisplayName("Si el máximo ya está al final, la lista no cambia")
    void maximoYaAlFinal() {
        assertEquals(List.of(1, 2, 3, 5),
                MaximoAlFinal.maximoAlFinal(List.of(1, 2, 3, 5)));
    }

    @Test
    @DisplayName("Con máximo repetido mueve solo la primera aparición")
    void maximoRepetido() {
        assertEquals(List.of(2, 5, 1, 5),
                MaximoAlFinal.maximoAlFinal(List.of(5, 2, 5, 1)));
    }

    @Test
    @DisplayName("Un único elemento devuelve la misma lista")
    void unicoElemento() {
        assertEquals(List.of(7), MaximoAlFinal.maximoAlFinal(List.of(7)));
    }

    @Test
    @DisplayName("Lista vacía lanza excepción y no modifica la original")
    void listaVaciaLanza() {
        assertThrows(IllegalArgumentException.class,
                () -> MaximoAlFinal.maximoAlFinal(List.of()));

        List<Integer> original = new ArrayList<>(List.of(3, 9, 1));
        MaximoAlFinal.maximoAlFinal(original);
        assertEquals(List.of(3, 9, 1), original);
    }
}
