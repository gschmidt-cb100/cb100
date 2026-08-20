package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 07 - Matriz transpuesta. */
class MatrizTranspuestaTest {

    @Test
    @DisplayName("Transpone una matriz rectangular 2x3")
    void matrizRectangular() {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] t = MatrizTranspuesta.transponer(m);
        assertArrayEquals(new int[] {1, 4}, t[0]);
        assertArrayEquals(new int[] {2, 5}, t[1]);
        assertArrayEquals(new int[] {3, 6}, t[2]);
    }

    @Test
    @DisplayName("Transpone una matriz cuadrada 2x2")
    void matrizCuadrada() {
        int[][] m = {
            {1, 2},
            {3, 4}
        };
        int[][] t = MatrizTranspuesta.transponer(m);
        assertArrayEquals(new int[] {1, 3}, t[0]);
        assertArrayEquals(new int[] {2, 4}, t[1]);
    }

    @Test
    @DisplayName("Matriz null lanza excepcion")
    void matrizNull() {
        assertThrows(IllegalArgumentException.class, () -> MatrizTranspuesta.transponer(null));
    }
}
