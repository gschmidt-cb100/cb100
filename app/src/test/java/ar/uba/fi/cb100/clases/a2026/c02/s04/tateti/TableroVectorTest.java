package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * el tablero por separado muestra que el genérico
 * de verdad no sabe nada del tateti
 */
@DisplayName("TableroVector")
class TableroVectorTest {

    @Test
    @DisplayName("las posiciones van de 1 a n, y (1,1) es la esquina de arriba a la izquierda")
    void lasPosicionesVanDeUnoAEne() {
        TableroVector<Integer> tablero = new TableroVector<>(2, 3);

        tablero.colocar(1, 1, 11);
        tablero.colocar(2, 3, 23);

        assertEquals(11, tablero.obtener(1, 1));
        assertEquals(23, tablero.obtener(2, 3));
        assertNull(tablero.obtener(1, 3), "vacía");
        assertEquals(2, tablero.getCantidadDeFilas());
        assertEquals(3, tablero.getCantidadDeColumnas());
    }

    @Test
    @DisplayName("cada fila ocupa un tramo consecutivo del vector, una detrás de la otra")
    void elMapeoDeFilaYColumnaAIndiceEsCorrecto() {
        // tablero de 3x3: la fila 1 ocupa los índices 0,1,2 del vector interno,
        // la fila 2 los índices 3,4,5 y la fila 3 los índices 6,7,8
        TableroVector<Integer> tablero = new TableroVector<>(3, 3);

        tablero.colocar(1, 1, 1); // índice 0
        tablero.colocar(1, 3, 2); // índice 2
        tablero.colocar(2, 1, 3); // índice 3
        tablero.colocar(3, 3, 4); // índice 8

        assertEquals(1, tablero.obtener(1, 1));
        assertEquals(2, tablero.obtener(1, 3));
        assertEquals(3, tablero.obtener(2, 1));
        assertEquals(4, tablero.obtener(3, 3));
        assertNull(tablero.obtener(2, 2), "el centro sigue vacío");
    }

    @Test
    @DisplayName("la fila 0 y la fila n+1 están fuera del tablero")
    void losBordesSeValidan() {
        TableroVector<Integer> tablero = new TableroVector<>(3, 3);

        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(4, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> tablero.obtener(1, 0));
        assertThrows(IllegalArgumentException.class, () -> tablero.obtener(1, 4));
        assertThrows(IllegalArgumentException.class, () -> tablero.estaVacio(4, 4));
    }

    @Test
    @DisplayName("estaLleno recién es true con todas las posiciones ocupadas")
    void estaLleno() {
        TableroVector<Integer> tablero = new TableroVector<>(2, 2);
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
        TableroVector<Integer> tablero = new TableroVector<>(2, 2);
        tablero.colocar(1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(1, 1, 2));
        assertThrows(IllegalArgumentException.class, () -> tablero.colocar(2, 2, null));
        assertEquals(1, tablero.obtener(1, 1), "la original sigue");
    }

    @Test
    @DisplayName("un tablero sin filas o sin columnas no se puede crear")
    void dimensionesInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> new TableroVector<Integer>(0, 3));
        assertThrows(IllegalArgumentException.class, () -> new TableroVector<Integer>(3, 0));
    }

    @Test
    @DisplayName("un tateti típico de 3x3 con fichas X y O funciona igual que con TableroMatriz")
    void unTatetiDeTresPorTresConFichas() {
        TableroVector<Ficha> tablero = new TableroVector<>(3, 3);

        tablero.colocar(1, 1, Ficha.X);
        tablero.colocar(2, 2, Ficha.O);
        tablero.colocar(3, 3, Ficha.X);

        assertEquals(Ficha.X, tablero.obtener(1, 1));
        assertEquals(Ficha.O, tablero.obtener(2, 2));
        assertEquals(Ficha.X, tablero.obtener(3, 3));
        assertTrue(tablero.estaVacio(1, 2));
        assertFalse(tablero.estaLleno());
    }
}