package ar.uba.fi.cb100.material.i10_tecnicas;

/**
 * <b>Programación dinámica, el ejemplo de entrada</b>: ¿de cuántas formas se
 * puede subir una escalera de n escalones dando pasos de 1 o de 2?
 * <p>
 * La subestructura: para llegar al escalón n vine del n−1 (paso de 1) o del
 * n−2 (paso de 2), así que {@code formas(n) = formas(n−1) + formas(n−2)} —
 * ¡Fibonacci disfrazado! (U6). La versión recursiva ingenua repite
 * subproblemas y es O(2^n); acá van la memoización (top-down) y la
 * tabulación (bottom-up), ambas O(n).
 */
public final class Escaleras {

    private Escaleras() {}

    /** MEMOIZACIÓN (top-down): recursión + tabla de resultados ya calculados. */
    public static long conMemoizacion(int n) {
        return memo(n, new long[n + 1]);          // 0 = "todavía no calculado"
    }

    private static long memo(int n, long[] memo) {
        if (n <= 1) {
            return 1;   // 1 forma de subir 0 o 1 escalón
        }
        if (memo[n] != 0) {
            return memo[n];   // ya lo calculé: lo devuelvo GRATIS
        }
        memo[n] = memo(n - 1, memo) + memo(n - 2, memo);
        return memo[n];
    }

    /** TABULACIÓN (bottom-up): llenar la tabla desde el caso base. */
    public static long conTabulacion(int n) {
        if (n <= 1) {
            return 1;
        }
        long[] formas = new long[n + 1];
        formas[0] = 1;                            // casos base
        formas[1] = 1;
        for (int i = 2; i <= n; i++) {
            formas[i] = formas[i - 1] + formas[i - 2];   // la recurrencia, sin recursión
        }
        return formas[n];
    }

    public static void main(String[] args) {
        System.out.println(conTabulacion(5));     // 8
        System.out.println(conMemoizacion(5));    // 8
        System.out.println(conTabulacion(50));    // 20365011074 (instantáneo)
    }
}
