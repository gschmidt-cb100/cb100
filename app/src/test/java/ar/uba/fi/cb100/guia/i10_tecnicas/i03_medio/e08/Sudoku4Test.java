package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Sudoku4Test {

    @Test
    @DisplayName("Un tablero resoluble queda completo y válido")
    void tableroResoluble() {
        int[][] tablero = {
                {1, 0, 0, 0},
                {0, 0, 3, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 2},
        };
        assertTrue(Sudoku4.resolver(tablero));
        assertTrue(esValidoYCompleto(tablero));
    }

    @Test
    @DisplayName("La solución respeta las pistas originales")
    void respetaPistas() {
        int[][] tablero = {
                {0, 2, 0, 0},
                {0, 0, 0, 3},
                {2, 0, 0, 0},
                {0, 0, 4, 0},
        };
        assertTrue(Sudoku4.resolver(tablero));
        assertEquals(2, tablero[0][1]);
        assertEquals(3, tablero[1][3]);
        assertEquals(2, tablero[2][0]);
        assertEquals(4, tablero[3][2]);
        assertTrue(esValidoYCompleto(tablero));
    }

    @Test
    @DisplayName("Un tablero imposible devuelve false")
    void tableroImposible() {
        // La celda (0,1) no admite ningún valor: 1 y 2 están en la fila,
        // 3 en la columna y 4 en la caja de 2x2.
        int[][] tablero = {
                {1, 0, 0, 2},
                {3, 4, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0},
        };
        assertFalse(Sudoku4.resolver(tablero));
    }

    @Test
    @DisplayName("Si no hay solución, el backtracking deja el tablero como estaba")
    void deshaceTodoAlFallar() {
        int[][] tablero = {
                {1, 0, 0, 2},
                {3, 4, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] copia = {
                {1, 0, 0, 2},
                {3, 4, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0},
        };
        assertFalse(Sudoku4.resolver(tablero));
        for (int f = 0; f < 4; f++) {
            assertArrayEquals(copia[f], tablero[f]);
        }
    }

    @Test
    @DisplayName("Un tablero totalmente vacío tiene solución")
    void tableroVacio() {
        int[][] tablero = new int[4][4];
        assertTrue(Sudoku4.resolver(tablero));
        assertTrue(esValidoYCompleto(tablero));
    }

    /** Verifica que cada fila, columna y caja de 2x2 tenga los valores 1..4. */
    private boolean esValidoYCompleto(int[][] t) {
        for (int i = 0; i < 4; i++) {
            boolean[] enFila = new boolean[5];
            boolean[] enColumna = new boolean[5];
            for (int j = 0; j < 4; j++) {
                if (t[i][j] < 1 || t[i][j] > 4 || enFila[t[i][j]]) {
                    return false;
                }
                enFila[t[i][j]] = true;
                if (enColumna[t[j][i]]) {
                    return false;
                }
                enColumna[t[j][i]] = true;
            }
        }
        for (int filaCaja = 0; filaCaja < 4; filaCaja += 2) {
            for (int colCaja = 0; colCaja < 4; colCaja += 2) {
                boolean[] enCaja = new boolean[5];
                for (int f = filaCaja; f < filaCaja + 2; f++) {
                    for (int c = colCaja; c < colCaja + 2; c++) {
                        if (enCaja[t[f][c]]) {
                            return false;
                        }
                        enCaja[t[f][c]] = true;
                    }
                }
            }
        }
        return true;
    }
}
