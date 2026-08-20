package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * e01: vuelto con mínima cantidad de monedas, ahora bien resuelto.
 *
 * <p><b>Técnica: programación dinámica</b> (bottom-up) con reconstrucción
 * de la solución. El greedy de "siempre la moneda más grande" falla con
 * sistemas no canónicos como {1,3,4}: para 6 elige 4+1+1 (3 monedas)
 * cuando lo óptimo es 3+3 (2 monedas).</p>
 *
 * <p><b>Tabla:</b> {@code dp[m]} = mínima cantidad de monedas para formar
 * el monto {@code m}, o infinito si es imposible. Caso base
 * {@code dp[0] = 0}; transición
 * {@code dp[m] = 1 + min(dp[m - moneda])} sobre todas las monedas que
 * "entran" en m. Para reconstruir guardamos en {@code eleccion[m]} qué
 * moneda logró ese mínimo y la seguimos hacia atrás desde el monto.</p>
 *
 * <p>Costo: O(monto · cantidadDeMonedas) en tiempo, O(monto) en memoria.</p>
 */
public final class VueltoOptimo {

    /** Marcador de "imposible" que no desborda al sumarle 1. */
    private static final int INFINITO = Integer.MAX_VALUE - 1;

    private VueltoOptimo() {
    }

    /**
     * Mínima cantidad de monedas del sistema para formar el monto.
     *
     * @param sistema denominaciones disponibles (todas &gt; 0, stock infinito)
     * @param monto   monto a formar, debe ser &gt;= 0
     * @return la cantidad mínima de monedas, o -1 si el monto no se puede formar
     */
    public static int minimo(int[] sistema, int monto) {
        int[] dp = tabla(sistema, monto)[0];
        return dp[monto] >= INFINITO ? -1 : dp[monto];
    }

    /**
     * Una combinación óptima de monedas que forma el monto.
     *
     * @param sistema denominaciones disponibles (todas &gt; 0, stock infinito)
     * @param monto   monto a formar, debe ser &gt;= 0
     * @return lista de monedas cuya suma es el monto y cuyo tamaño es
     *         {@link #minimo}; lista vacía si el monto es 0 o es imposible
     */
    public static List<Integer> monedasOptimas(int[] sistema, int monto) {
        int[][] tablas = tabla(sistema, monto);
        int[] dp = tablas[0];
        int[] eleccion = tablas[1];
        List<Integer> resultado = new ArrayList<>();
        if (dp[monto] >= INFINITO) {
            return resultado;                       // imposible: sin monedas
        }
        // Reconstrucción: desde el monto, restamos la moneda elegida.
        int restante = monto;
        while (restante > 0) {
            resultado.add(eleccion[restante]);
            restante -= eleccion[restante];
        }
        return resultado;
    }

    /** Arma dp[] y eleccion[] en una sola pasada. */
    private static int[][] tabla(int[] sistema, int monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("monto negativo: " + monto);
        }
        int[] dp = new int[monto + 1];
        int[] eleccion = new int[monto + 1];
        Arrays.fill(dp, INFINITO);
        dp[0] = 0;                                  // monto 0: cero monedas
        for (int m = 1; m <= monto; m++) {
            for (int moneda : sistema) {
                if (moneda <= m && dp[m - moneda] + 1 < dp[m]) {
                    dp[m] = dp[m - moneda] + 1;
                    eleccion[m] = moneda;           // esta moneda logró el mínimo
                }
            }
        }
        return new int[][] {dp, eleccion};
    }

    public static void main(String[] args) {
        int[] sistema = {1, 3, 4};
        int monto = 6;
        List<Integer> monedas = monedasOptimas(sistema, monto);
        Collections.sort(monedas);
        System.out.println("Sistema: " + Arrays.toString(sistema)
                + ", monto: " + monto);
        System.out.println("Mínimo:  " + minimo(sistema, monto)
                + " monedas -> " + monedas);
        System.out.println("El greedy hubiera dado 4+1+1 = 3 monedas.");
    }
}
