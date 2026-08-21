package ar.uba.fi.cb100.material.i10_tecnicas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>El problema integrador de la unidad</b>: dar un monto con la MENOR
 * cantidad de monedas, resuelto con las TRES técnicas:
 * <ul>
 *   <li><b>Greedy</b>: la moneda más grande que entre — rapidísimo, pero
 *       SOLO es óptimo con sistemas "canónicos" (como el argentino);</li>
 *   <li><b>Backtracking</b>: prueba todas las combinaciones — siempre óptimo,
 *       pero exponencial;</li>
 *   <li><b>Programación dinámica</b>: óptimo Y eficiente, O(monto × monedas),
 *       construyendo la tabla {@code minimo[m]} de abajo hacia arriba.</li>
 * </ul>
 */
public final class CambioDeMonedas {

    private CambioDeMonedas() {}

    /** GREEDY: siempre la moneda más grande que entre. O(monedas). ¡No siempre óptimo! */
    public static int greedy(int[] monedas, int monto) {
        int[] desc = Arrays.stream(monedas).boxed()
                .sorted((a, b) -> b - a).mapToInt(Integer::intValue).toArray();
        int cantidad = 0;
        for (int moneda : desc) {
            cantidad += monto / moneda;           // todas las que entren
            monto %= moneda;
        }
        return monto == 0 ? cantidad : -1;        // -1: no se pudo formar el monto
    }

    /** BACKTRACKING: explora todas las combinaciones. Óptimo pero exponencial. */
    public static int backtracking(int[] monedas, int monto) {
        int mejor = explorar(monedas, monto, 0, Integer.MAX_VALUE);
        return mejor == Integer.MAX_VALUE ? -1 : mejor;
    }

    private static int explorar(int[] monedas, int resto, int usadas, int mejor) {
        if (resto == 0) {
            return Math.min(mejor, usadas);   // solución completa
        }
        if (usadas + 1 >= mejor) {
            return mejor;   // PODA: ya no puede mejorar
        }
        for (int moneda : monedas) {
            if (moneda <= resto) {                            // ELEGIR y AVANZAR
                mejor = explorar(monedas, resto - moneda, usadas + 1, mejor);
            }
        }
        return mejor;
    }

    /** PROGRAMACIÓN DINÁMICA (tabulación): minimo[m] = mínima cantidad para el monto m. */
    public static int dp(int[] monedas, int monto) {
        int[] minimo = new int[monto + 1];
        Arrays.fill(minimo, Integer.MAX_VALUE);
        minimo[0] = 0;                                        // caso base: $0 = 0 monedas
        for (int m = 1; m <= monto; m++) {
            for (int moneda : monedas) {
                if (moneda <= m && minimo[m - moneda] != Integer.MAX_VALUE) {
                    minimo[m] = Math.min(minimo[m], 1 + minimo[m - moneda]);
                }
            }
        }
        return minimo[monto] == Integer.MAX_VALUE ? -1 : minimo[monto];
    }

    /** DP con reconstrucción: no sólo cuántas, sino CUÁLES monedas. */
    public static List<Integer> dpConMonedas(int[] monedas, int monto) {
        int[] minimo = new int[monto + 1];
        int[] eleccion = new int[monto + 1];                  // qué moneda usó cada monto
        Arrays.fill(minimo, Integer.MAX_VALUE);
        minimo[0] = 0;
        for (int m = 1; m <= monto; m++) {
            for (int moneda : monedas) {
                if (moneda <= m && minimo[m - moneda] != Integer.MAX_VALUE
                        && 1 + minimo[m - moneda] < minimo[m]) {
                    minimo[m] = 1 + minimo[m - moneda];
                    eleccion[m] = moneda;
                }
            }
        }
        List<Integer> resultado = new ArrayList<>();
        if (minimo[monto] == Integer.MAX_VALUE) {
            return resultado;
        }
        for (int m = monto; m > 0; m -= eleccion[m]) {        // deshacer el camino
            resultado.add(eleccion[m]);
        }
        return resultado;
    }

    public static void main(String[] args) {
        // Sistema canónico (billetes argentinos simplificados): greedy acierta.
        int[] pesos = {100, 50, 20, 10, 1};
        System.out.println(greedy(pesos, 180));        // 4  (100+50+20+10)
        System.out.println(dp(pesos, 180));            // 4  (coinciden)

        // Sistema NO canónico: greedy falla, DP y backtracking aciertan.
        int[] raras = {1, 3, 4};
        System.out.println(greedy(raras, 6));          // 3  (4+1+1)  ¡NO es óptimo!
        System.out.println(backtracking(raras, 6));    // 2  (3+3)
        System.out.println(dp(raras, 6));              // 2  (3+3)
        System.out.println(dpConMonedas(raras, 6));    // [3, 3]
    }
}
