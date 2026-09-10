package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * El tablero por separado, con {@code Integer} en lugar de {@code Ficha}:
 * muestra que el genérico {@code T} de verdad no sabe nada del tateti.
 */
@DisplayName("TableroMatriz")
class TableroMatrizTest {

    @Test
    @DisplayName("las posiciones van de 1 a n, y (1,1) es la esquina de arriba a la izquierda")
    void lasPosicionesVanDeUnoAEne() {
        TableroMatriz<Integer> tablero = new TableroMatriz<>(2, 3);

        tablero.colocar(1, 1, 11);
        tablero.colocar(2, 3, 23);

        assertEquals(11, tablero.obtener(1, 1));
        assertEquals(23, tablero.obtener(2, 3));
        assertNull(tablero.obtener(1, 3), "vacía");
        assertEquals(2, tablero.getCantidadDeFilas());
        assertEquals(3, tablero.getCantidadDeColumnas());
    }

    @Test
    @DisplayName("la fila 0 y la fila n+1 están fuera del tablero")
    void losBordesSeValidan() {
        TableroMatriz<Integer> tablero = new TableroMatriz<>(3, 3);

        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(4, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> tablero.obtener(1, 0));
        assertThrows(IllegalArgumentException.class, () -> tablero.obtener(1, 4));
        assertThrows(IllegalArgumentException.class, () -> tablero.estaVacio(4, 4));
    }

    @Test
    @DisplayName("estaLleno recién es true con todas las posiciones ocupadas")
    void estaLleno() {
        TableroMatriz<Integer> tablero = new TableroMatriz<>(2, 2);
        assertFalse(tablero.estaLleno());

        tablero.colocar(1, 1, 1);
        tablero.colocar(1, 2, 2);
        tablero.colocar(2, 1, 3);
        assertFalse(tablero.estaLleno(), "falta una");

        tablero.colocar(2, 2, 4);
        assertTrue(tablero.estaLleno());
    }

    @Test
    @DisplayName("no se coloca sobre una posición ocupada ni un valor nulo")
    void noSePisaNiSeColocaNull() {
        TableroMatriz<Integer> tablero = new TableroMatriz<>(2, 2);
        tablero.colocar(1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(1, 1, 2));
        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(2, 2, null));
        assertEquals(1, tablero.obtener(1, 1), "la original sigue");
    }

    @Test
    @DisplayName("un tablero sin filas o sin columnas no se puede crear")
    void dimensionesInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> new TableroMatriz<Integer>(0, 3));
        assertThrows(IllegalArgumentException.class, () -> new TableroMatriz<Integer>(3, 0));
    }
}
