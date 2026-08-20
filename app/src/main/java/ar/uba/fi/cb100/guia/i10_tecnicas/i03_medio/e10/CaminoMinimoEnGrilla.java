package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e10;

import java.util.ArrayList;
import java.util.List;

/**
 * e10: camino de costo mínimo en una grilla, moviéndose solo a la
 * derecha o hacia abajo.
 *
 * <p><b>Técnica: programación dinámica</b> 2D con reconstrucción del
 * camino. Desde cada casilla solo se puede ir a la derecha ("D") o
 * abajo ("A"), así que el subproblema "mejor camino desde acá hasta la
 * esquina" solo depende de dos vecinos.</p>
 *
 * <p><b>Tabla:</b> {@code dp[i][j]} = costo mínimo para ir desde la
 * casilla {@code (i,j)} hasta la esquina inferior derecha, incluyendo
 * ambas. Caso base: la esquina vale su propio costo. Transición:
 * {@code dp[i][j] = grilla[i][j] + min(dp[i][j+1], dp[i+1][j])} (en el
 * borde derecho solo se puede bajar; en el borde inferior, solo ir a la
 * derecha). Se llena de abajo hacia arriba y de derecha a izquierda.
 * Definirla "desde (i,j) hasta el final" hace la reconstrucción directa:
 * desde (0,0) avanzamos siempre hacia el vecino de menor dp.</p>
 *
 * <p>Costo: O(filas · columnas) en tiempo y memoria.</p>
 */
public final class CaminoMinimoEnGrilla {

    private CaminoMinimoEnGrilla() {
    }

    /**
     * Costo mínimo para ir de la esquina superior izquierda a la inferior
     * derecha, sumando las casillas visitadas.
     *
     * @param grilla matriz rectangular no vacía de costos (todos &gt;= 0)
     * @return el costo total del mejor camino, ambos extremos incluidos
     */
    public static int costoMinimo(int[][] grilla) {
        int[][] dp = tabla(grilla);
        return dp[0][0];
    }

    /**
     * Un camino óptimo como lista de movimientos: "D" (derecha) o "A" (abajo).
     *
     * @param grilla matriz rectangular no vacía de costos (todos &gt;= 0)
     * @return los movimientos desde (0,0) hasta la esquina inferior derecha;
     *         lista vacía si la grilla es de 1×1
     */
    public static List<String> camino(int[][] grilla) {
        int[][] dp = tabla(grilla);
        int filas = grilla.length;
        int columnas = grilla[0].length;
        List<String> movimientos = new ArrayList<>();
        int i = 0;
        int j = 0;
        // Reconstrucción hacia adelante: en cada paso seguimos al vecino
        // cuyo dp es menor (los bordes fuerzan el único movimiento posible).
        while (i < filas - 1 || j < columnas - 1) {
            boolean puedeDerecha = j < columnas - 1;
            boolean puedeAbajo = i < filas - 1;
            if (puedeDerecha && (!puedeAbajo || dp[i][j + 1] <= dp[i + 1][j])) {
                movimientos.add("D");
                j++;
            } else {
                movimientos.add("A");
                i++;
            }
        }
        return movimientos;
    }

    /** Llena dp[i][j] = costo mínimo desde (i,j) hasta la esquina inferior derecha. */
    private static int[][] tabla(int[][] grilla) {
        if (grilla.length == 0 || grilla[0].length == 0) {
            throw new IllegalArgumentException("la grilla no puede ser vacía");
        }
        int filas = grilla.length;
        int columnas = grilla[0].length;
        int[][] dp = new int[filas][columnas];
        for (int i = filas - 1; i >= 0; i--) {
            for (int j = columnas - 1; j >= 0; j--) {
                if (i == filas - 1 && j == columnas - 1) {
                    dp[i][j] = grilla[i][j];        // la esquina: caso base
                } else if (i == filas - 1) {
                    dp[i][j] = grilla[i][j] + dp[i][j + 1];     // solo derecha
                } else if (j == columnas - 1) {
                    dp[i][j] = grilla[i][j] + dp[i + 1][j];     // solo abajo
                } else {
                    dp[i][j] = grilla[i][j] + Math.min(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        int[][] grilla = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1},
        };
        System.out.println("Costo mínimo: " + costoMinimo(grilla));
        System.out.println("Camino:       " + camino(grilla));
    }
}
