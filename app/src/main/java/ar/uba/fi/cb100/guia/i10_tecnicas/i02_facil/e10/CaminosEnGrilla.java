package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e10;

/**
 * e10: ¿cuántos caminos distintos hay desde la esquina superior izquierda
 * hasta la inferior derecha de una grilla, si sólo se puede avanzar hacia
 * la derecha o hacia abajo?
 *
 * <p><b>Técnica: programación dinámica 2D.</b> A cada celda se llega
 * únicamente desde arriba o desde la izquierda, así que la cantidad de
 * caminos hasta (f, c) es dp[f-1][c] + dp[f][c-1]. Los bordes tienen un
 * único camino posible (todo derecho). Es el mismo esquema de e01 y e09
 * pero con una tabla de dos dimensiones: la recursión directa costaría
 * O(2^(filas+columnas)) y la tabla lo baja a O(filas × columnas).</p>
 */
public final class CaminosEnGrilla {

    private CaminosEnGrilla() {
    }

    /**
     * Cantidad de caminos de (0,0) a (filas-1, columnas-1) moviéndose
     * sólo hacia la derecha o hacia abajo.
     *
     * @param filas    cantidad de filas de la grilla (&gt;= 1)
     * @param columnas cantidad de columnas de la grilla (&gt;= 1)
     * @return cantidad de caminos distintos
     */
    public static long caminos(int filas, int columnas) {
        if (filas < 1 || columnas < 1) {
            throw new IllegalArgumentException(
                    "la grilla debe ser al menos de 1x1, vino " + filas + "x" + columnas);
        }
        long[][] dp = new long[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (f == 0 || c == 0) {
                    // Primera fila o primera columna: un solo camino,
                    // todo derecho o todo para abajo.
                    dp[f][c] = 1;
                } else {
                    dp[f][c] = dp[f - 1][c] + dp[f][c - 1];
                }
            }
        }
        return dp[filas - 1][columnas - 1];
    }

    public static void main(String[] args) {
        System.out.println("Caminos en una grilla de 3x3: " + caminos(3, 3));
        System.out.println("Caminos en una grilla de 1x5: " + caminos(1, 5));
        System.out.println("Caminos en una grilla de 4x6: " + caminos(4, 6));
    }
}
