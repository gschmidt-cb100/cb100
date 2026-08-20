package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e04;

import java.util.Arrays;

/**
 * e04: longitud de la subsecuencia creciente más larga (LIS).
 *
 * <p><b>Técnica: programación dinámica</b> O(n²). Igual que en LCS,
 * "subsecuencia" permite saltear elementos manteniendo el orden.</p>
 *
 * <p><b>Tabla:</b> {@code dp[i]} = longitud de la subsecuencia
 * estrictamente creciente más larga que <b>termina exactamente</b> en
 * {@code a[i]}. Caso base {@code dp[i] = 1} (el elemento solo). Transición:
 * {@code dp[i] = 1 + max(dp[j])} sobre todos los {@code j < i} con
 * {@code a[j] < a[i]}. La respuesta no es {@code dp[n-1]} sino el máximo
 * de toda la tabla, porque la LIS puede terminar en cualquier posición.</p>
 *
 * <p>Costo: O(n²) en tiempo, O(n) en memoria. (Existe una variante
 * O(n log n) con búsqueda binaria, fuera del alcance de este ejercicio.)</p>
 */
public final class SubsecuenciaCreciente {

    private SubsecuenciaCreciente() {
    }

    /**
     * Longitud de la subsecuencia estrictamente creciente más larga.
     *
     * @param a arreglo de valores, no nulo
     * @return la longitud de la LIS; 0 si el arreglo es vacío
     */
    public static int lis(int[] a) {
        if (a.length == 0) {
            return 0;
        }
        int[] dp = new int[a.length];
        Arrays.fill(dp, 1);                         // cada elemento solo ya es LIS de 1
        int mejor = 1;
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                // a[i] puede extender toda subsecuencia que termina antes
                // en un valor estrictamente menor.
                if (a[j] < a[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
            mejor = Math.max(mejor, dp[i]);
        }
        return mejor;
    }

    public static void main(String[] args) {
        int[] a = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Secuencia: " + Arrays.toString(a));
        System.out.println("LIS = " + lis(a) + " (por ejemplo 2, 3, 7, 101)");
    }
}
