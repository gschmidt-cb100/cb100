package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e08;

/**
 * e08: llegar de n a 1 en la menor cantidad de pasos, pudiendo en cada
 * paso restar 1, dividir por 2 (si el número es par) o dividir por 3
 * (si es divisible por 3).
 *
 * <p><b>Técnica: programación dinámica por tabulación.</b> Este es un
 * caso donde el greedy intuitivo ("dividí siempre que puedas") falla:
 * para 10 conviene restar 1 primero (10 → 9 → 3 → 1, 3 pasos) y no
 * dividir por 2 (10 → 5 → 4 → 2 → 1, 4 pasos). La DP evalúa las tres
 * opciones para cada valor: dp[i] = 1 + mín(dp[i-1], dp[i/2] si i es
 * par, dp[i/3] si es múltiplo de 3), llenando la tabla de 1 a n en O(n).</p>
 */
public final class PasosAUno {

    private PasosAUno() {
    }

    /**
     * Mínima cantidad de operaciones (−1, /2 si es par, /3 si es
     * divisible por 3) para transformar {@code n} en 1.
     *
     * @param n número inicial (n &gt;= 1)
     * @return cantidad mínima de pasos; 0 si n ya es 1
     */
    public static int pasosMinimos(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser >= 1, vino " + n);
        }
        int[] dp = new int[n + 1];
        dp[1] = 0; // ya estamos en 1
        for (int i = 2; i <= n; i++) {
            // Opción siempre disponible: restar 1.
            int mejor = dp[i - 1];
            if (i % 2 == 0) {
                mejor = Math.min(mejor, dp[i / 2]);
            }
            if (i % 3 == 0) {
                mejor = Math.min(mejor, dp[i / 3]);
            }
            dp[i] = 1 + mejor;
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println("pasosMinimos(10) = " + pasosMinimos(10)
                + "  (10 -> 9 -> 3 -> 1)");
        System.out.println("pasosMinimos(1)  = " + pasosMinimos(1));
        System.out.println("pasosMinimos(27) = " + pasosMinimos(27)
                + "  (27 -> 9 -> 3 -> 1)");
    }
}
