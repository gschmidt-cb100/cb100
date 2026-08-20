package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e06;

/**
 * e06: distancia de edición (Levenshtein) entre dos cadenas.
 *
 * <p><b>Técnica: programación dinámica</b> 2D. Es lo que usa el corrector
 * ortográfico para sugerir palabras parecidas: cuenta la mínima cantidad
 * de operaciones (insertar, borrar o reemplazar un carácter) para
 * transformar una cadena en otra.</p>
 *
 * <p><b>Tabla:</b> {@code dp[i][j]} = mínima cantidad de operaciones para
 * transformar el prefijo {@code a[0..i)} en el prefijo {@code b[0..j)}.
 * Casos base: {@code dp[i][0] = i} (borrar todo) y {@code dp[0][j] = j}
 * (insertar todo). Transición: si {@code a[i-1] == b[j-1]},
 * {@code dp[i][j] = dp[i-1][j-1]} (gratis); si no,
 * {@code dp[i][j] = 1 + min(dp[i-1][j],   // borrar de a
 * dp[i][j-1],                             // insertar en a
 * dp[i-1][j-1])                           // reemplazar}.</p>
 *
 * <p>Costo: O(|a| · |b|) en tiempo y memoria.</p>
 */
public final class DistanciaDeEdicion {

    private DistanciaDeEdicion() {
    }

    /**
     * Mínima cantidad de inserciones, borrados y reemplazos para
     * transformar {@code a} en {@code b}.
     *
     * @param a cadena de origen, no nula
     * @param b cadena de destino, no nula
     * @return la distancia de edición (0 si son iguales)
     */
    public static int distancia(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;                           // borrar los i caracteres de a
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;                           // insertar los j caracteres de b
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];    // coinciden: sin costo
                } else {
                    int borrar = dp[i - 1][j];
                    int insertar = dp[i][j - 1];
                    int reemplazar = dp[i - 1][j - 1];
                    dp[i][j] = 1 + Math.min(reemplazar, Math.min(borrar, insertar));
                }
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        System.out.println("distancia(\"casa\", \"calle\") = "
                + distancia("casa", "calle"));
        System.out.println("(casa -> cala -> calla -> calle: "
                + "reemplazo, inserción, reemplazo)");
    }
}
