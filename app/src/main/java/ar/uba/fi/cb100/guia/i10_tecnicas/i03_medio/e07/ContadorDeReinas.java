package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e07;

/**
 * e07: contar las soluciones del problema de las N reinas.
 *
 * <p><b>Técnica: backtracking.</b> Colocamos una reina por fila (dos en la
 * misma fila se atacan seguro, así que la fila viene gratis) y probamos
 * cada columna. Antes de bajar a la fila siguiente validamos que la
 * columna y las dos diagonales estén libres; si un intento no lleva a
 * nada, lo deshacemos y probamos la columna siguiente. La poda está en
 * validar <b>antes</b> de recursionar: un tablero inválido no genera
 * ningún descendiente.</p>
 *
 * <p>Truco de indexación: en un tablero de n×n, las casillas de una misma
 * diagonal ↘ comparten {@code fila - columna} (corrido en n-1 para que no
 * sea negativo) y las de una diagonal ↙ comparten {@code fila + columna}.</p>
 *
 * <p>Costo: exponencial en el peor caso, pero la poda lo hace tratable
 * para los n de interés (n=8 son 92 soluciones).</p>
 */
public final class ContadorDeReinas {

    private ContadorDeReinas() {
    }

    /**
     * Cantidad de formas de ubicar n reinas en un tablero de n×n sin
     * que se ataquen entre sí.
     *
     * @param n lado del tablero, debe ser &gt;= 1
     * @return la cantidad total de soluciones distintas
     */
    public static int contar(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser >= 1, vino " + n);
        }
        return colocar(0, n,
                new boolean[n],                     // columnas ocupadas
                new boolean[2 * n - 1],             // diagonales ↘ (fila - col + n-1)
                new boolean[2 * n - 1]);            // diagonales ↙ (fila + col)
    }

    /** Intenta colocar la reina de la fila dada y cuenta las soluciones. */
    private static int colocar(int fila, int n, boolean[] columnas,
                               boolean[] diagPrincipal, boolean[] diagSecundaria) {
        if (fila == n) {
            return 1;                               // todas colocadas: una solución
        }
        int soluciones = 0;
        for (int col = 0; col < n; col++) {
            int dp = fila - col + n - 1;
            int ds = fila + col;
            if (columnas[col] || diagPrincipal[dp] || diagSecundaria[ds]) {
                continue;                           // poda: esta casilla está atacada
            }
            columnas[col] = diagPrincipal[dp] = diagSecundaria[ds] = true;
            soluciones += colocar(fila + 1, n, columnas, diagPrincipal, diagSecundaria);
            // Backtrack: liberamos la casilla para probar la próxima columna.
            columnas[col] = diagPrincipal[dp] = diagSecundaria[ds] = false;
        }
        return soluciones;
    }

    public static void main(String[] args) {
        for (int n = 1; n <= 8; n++) {
            System.out.println(n + " reinas: " + contar(n) + " soluciones");
        }
    }
}
