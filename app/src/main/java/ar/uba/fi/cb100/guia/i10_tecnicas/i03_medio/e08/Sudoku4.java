package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e08;

/**
 * e08: resolver un sudoku de 4×4 (cajas de 2×2, valores 1..4).
 *
 * <p><b>Técnica: backtracking.</b> Buscamos la primera celda vacía (0),
 * probamos cada valor de 1 a 4 que no rompa fila, columna ni caja, y
 * recursionamos sobre el resto del tablero. Si ningún valor funciona,
 * devolvemos la celda a 0 (deshacer) y retornamos false para que el
 * nivel anterior pruebe otra cosa. A diferencia de N reinas acá no
 * contamos soluciones: cortamos apenas encontramos una.</p>
 *
 * <p>El tablero se muta in place: si hay solución queda completo y
 * válido; si no la hay, el backtracking deshace todos sus intentos y el
 * tablero queda como estaba.</p>
 *
 * <p>Costo: exponencial en el peor caso (4 candidatos por celda vacía),
 * pero la validación temprana poda casi todo en tableros de 4×4.</p>
 */
public final class Sudoku4 {

    private static final int LADO = 4;
    private static final int CAJA = 2;

    private Sudoku4() {
    }

    /**
     * Completa el tablero in place si tiene solución.
     *
     * @param tablero matriz de 4×4 con valores 1..4 y ceros en las celdas
     *                vacías; se muta a la solución si existe
     * @return true si se encontró solución (tablero completo y válido),
     *         false si el tablero es irresoluble (queda como estaba)
     */
    public static boolean resolver(int[][] tablero) {
        validarDimensiones(tablero);
        return completarDesde(tablero, 0);
    }

    /** Backtracking sobre las celdas en orden fila-columna desde la posición dada. */
    private static boolean completarDesde(int[][] tablero, int posicion) {
        if (posicion == LADO * LADO) {
            return true;                            // no quedan celdas: resuelto
        }
        int fila = posicion / LADO;
        int col = posicion % LADO;
        if (tablero[fila][col] != 0) {
            return completarDesde(tablero, posicion + 1);   // celda fija
        }
        for (int valor = 1; valor <= LADO; valor++) {
            if (puedeIr(tablero, fila, col, valor)) {
                tablero[fila][col] = valor;
                if (completarDesde(tablero, posicion + 1)) {
                    return true;                    // corte: alcanza con una solución
                }
                tablero[fila][col] = 0;             // backtrack: deshacer el intento
            }
        }
        return false;                               // ningún valor sirve acá
    }

    /** Valida fila, columna y caja de 2×2 para el valor propuesto. */
    private static boolean puedeIr(int[][] tablero, int fila, int col, int valor) {
        for (int i = 0; i < LADO; i++) {
            if (tablero[fila][i] == valor || tablero[i][col] == valor) {
                return false;
            }
        }
        int filaCaja = (fila / CAJA) * CAJA;
        int colCaja = (col / CAJA) * CAJA;
        for (int f = filaCaja; f < filaCaja + CAJA; f++) {
            for (int c = colCaja; c < colCaja + CAJA; c++) {
                if (tablero[f][c] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void validarDimensiones(int[][] tablero) {
        if (tablero.length != LADO) {
            throw new IllegalArgumentException("el tablero debe ser de 4x4");
        }
        for (int[] fila : tablero) {
            if (fila.length != LADO) {
                throw new IllegalArgumentException("el tablero debe ser de 4x4");
            }
        }
    }

    public static void main(String[] args) {
        int[][] tablero = {
                {1, 0, 0, 0},
                {0, 0, 3, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 2},
        };
        System.out.println("¿Resuelto? " + resolver(tablero));
        for (int[] fila : tablero) {
            StringBuilder linea = new StringBuilder();
            for (int celda : fila) {
                linea.append(celda).append(' ');
            }
            System.out.println(linea);
        }
    }
}
