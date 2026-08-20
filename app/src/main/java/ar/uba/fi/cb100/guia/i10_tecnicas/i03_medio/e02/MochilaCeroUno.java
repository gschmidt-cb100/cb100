package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * e02: mochila 0/1 — elegir objetos sin fraccionar maximizando valor.
 *
 * <p><b>Técnica: programación dinámica</b> (bottom-up) sobre dos
 * dimensiones. A diferencia de la mochila fraccionaria, acá el greedy por
 * "valor por kilo" no funciona: cada objeto se lleva entero o no se lleva.</p>
 *
 * <p><b>Tabla:</b> {@code dp[i][c]} = valor máximo usando solo los primeros
 * {@code i} objetos con capacidad {@code c}. Caso base {@code dp[0][c] = 0};
 * transición: {@code dp[i][c] = max(dp[i-1][c],  // no llevo el objeto i-1
 * dp[i-1][c - peso[i-1]] + valor[i-1])          // lo llevo, si entra}.
 * Para reconstruir recorremos la tabla desde {@code dp[n][capacidad]}:
 * si el valor difiere del de la fila anterior, el objeto fue elegido.</p>
 *
 * <p>Costo: O(n · capacidad) en tiempo y memoria (pseudo-polinomial).</p>
 */
public final class MochilaCeroUno {

    private MochilaCeroUno() {
    }

    /**
     * Valor máximo alcanzable sin exceder la capacidad.
     *
     * @param pesos     peso de cada objeto (todos &gt; 0)
     * @param valores   valor de cada objeto, alineado con {@code pesos}
     * @param capacidad capacidad total de la mochila, &gt;= 0
     * @return la suma de valores del mejor subconjunto que entra
     */
    public static int valorMaximo(int[] pesos, int[] valores, int capacidad) {
        int[][] dp = tabla(pesos, valores, capacidad);
        return dp[pesos.length][capacidad];
    }

    /**
     * Índices (ordenados ascendentemente) de los objetos de una solución óptima.
     *
     * @param pesos     peso de cada objeto (todos &gt; 0)
     * @param valores   valor de cada objeto, alineado con {@code pesos}
     * @param capacidad capacidad total de la mochila, &gt;= 0
     * @return los índices elegidos; lista vacía si no conviene llevar nada
     */
    public static List<Integer> indicesElegidos(int[] pesos, int[] valores,
                                                int capacidad) {
        int[][] dp = tabla(pesos, valores, capacidad);
        List<Integer> elegidos = new ArrayList<>();
        int c = capacidad;
        // Reconstrucción de atrás hacia adelante: si el óptimo con i objetos
        // difiere del óptimo con i-1, el objeto i-1 está en la solución.
        for (int i = pesos.length; i > 0; i--) {
            if (dp[i][c] != dp[i - 1][c]) {
                elegidos.add(i - 1);
                c -= pesos[i - 1];
            }
        }
        Collections.reverse(elegidos);              // quedaron de mayor a menor
        return elegidos;
    }

    /** Construye la tabla completa dp[0..n][0..capacidad]. */
    private static int[][] tabla(int[] pesos, int[] valores, int capacidad) {
        if (pesos.length != valores.length) {
            throw new IllegalArgumentException("pesos y valores desalineados");
        }
        if (capacidad < 0) {
            throw new IllegalArgumentException("capacidad negativa: " + capacidad);
        }
        int n = pesos.length;
        int[][] dp = new int[n + 1][capacidad + 1];
        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= capacidad; c++) {
                dp[i][c] = dp[i - 1][c];            // opción 1: no llevarlo
                if (pesos[i - 1] <= c) {            // opción 2: llevarlo si entra
                    int conObjeto = dp[i - 1][c - pesos[i - 1]] + valores[i - 1];
                    dp[i][c] = Math.max(dp[i][c], conObjeto);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        int[] pesos = {2, 3, 4};
        int[] valores = {3, 4, 5};
        int capacidad = 5;
        System.out.println("Pesos:    " + Arrays.toString(pesos));
        System.out.println("Valores:  " + Arrays.toString(valores));
        System.out.println("Capacidad " + capacidad
                + " -> valor máximo " + valorMaximo(pesos, valores, capacidad)
                + ", objetos " + indicesElegidos(pesos, valores, capacidad));
    }
}
