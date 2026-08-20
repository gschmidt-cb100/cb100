package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e03;

/**
 * e03: longitud de la subsecuencia común más larga (LCS).
 *
 * <p><b>Técnica: programación dinámica</b> 2D. Una subsecuencia no es una
 * subcadena: los caracteres deben mantener el orden pero pueden no ser
 * contiguos. Es la base de {@code diff} y del control de versiones.</p>
 *
 * <p><b>Tabla:</b> {@code dp[i][j]} = longitud de la LCS entre el prefijo
 * {@code a[0..i)} y el prefijo {@code b[0..j)}. Casos base: fila 0 y
 * columna 0 en cero (prefijo vacío). Transición: si
 * {@code a[i-1] == b[j-1]} entonces {@code dp[i][j] = dp[i-1][j-1] + 1}
 * (ese carácter extiende la LCS); si no,
 * {@code dp[i][j] = max(dp[i-1][j], dp[i][j-1])} (descartamos el último
 * carácter de una u otra).</p>
 *
 * <p>Costo: O(|a| · |b|) en tiempo y memoria.</p>
 */
public final class SubsecuenciaComun {

    private SubsecuenciaComun() {
    }

    /**
     * Longitud de la subsecuencia común más larga entre dos cadenas.
     *
     * @param a primera cadena, no nula
     * @param b segunda cadena, no nula
     * @return la longitud de la LCS (0 si alguna es vacía)
     */
    public static int lcs(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    // Coinciden: la LCS crece en 1 respecto de ambos prefijos.
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // No coinciden: lo mejor de descartar uno u otro final.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        String a = "AGGTAB";
        String b = "GXTXAYB";
        System.out.println("LCS(\"" + a + "\", \"" + b + "\") = " + lcs(a, b));
        System.out.println("(la subsecuencia común es \"GTAB\")");
    }
}
