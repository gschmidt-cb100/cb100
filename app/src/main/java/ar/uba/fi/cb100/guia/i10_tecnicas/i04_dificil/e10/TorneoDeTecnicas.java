package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e10;

import java.util.Arrays;

/**
 * TECNICA: COMPARACION de las tres tecnicas de la unidad sobre UN MISMO
 * problema, el del vuelto (cambio de monedas con monedas ilimitadas):
 * dar un monto con la minima cantidad de monedas de un sistema dado.
 *
 * 1) GREEDY: agarrar siempre la moneda mas grande que entre. Es O(m log m)
 *    y es OPTIMO en sistemas canonicos como {1, 5, 10, 25}, pero puede
 *    FALLAR en sistemas arbitrarios: con {1, 3, 4} y monto 6 el greedy da
 *    4+1+1 = 3 monedas cuando el optimo es 3+3 = 2. Este contraejemplo es
 *    la moraleja de la unidad: un greedy exige demostrar su correccion.
 *
 * 2) BACKTRACKING PODADO: explora las combinaciones probando monedas de
 *    mayor a menor, con dos podas: (a) cota: si las monedas usadas mas 1
 *    ya no mejoran el mejor resultado conocido, se abandona la rama;
 *    (b) para no repetir combinaciones en distinto orden, cada llamada
 *    solo usa monedas de indice >= al de la ultima usada. Exponencial en
 *    el peor caso, pero SIEMPRE exacto: sirve de oraculo.
 *
 * 3) PROGRAMACION DINAMICA: dp[m] = minima cantidad de monedas para el
 *    monto m; dp[0] = 0 y dp[m] = 1 + min sobre las monedas c <= m de
 *    dp[m - c]. O(monto * cantidadDeMonedas), exacto siempre. Es la
 *    solucion "profesional" del problema general.
 *
 * El metodo comparar corre las tres y devuelve un record Resultado para
 * observar cuando el greedy acierta y cuando no. Invariante esperable en
 * todos los casos: backtracking == dp (ambos exactos), y greedy >= dp
 * cuando encuentra solucion.
 */
public class TorneoDeTecnicas {

    /** Cantidad de monedas segun cada tecnica; -1 significa "no encontro solucion". */
    public record Resultado(int greedy, int backtracking, int dp) { }

    /** Corre las tres tecnicas sobre el mismo sistema y monto. */
    public Resultado comparar(int[] sistema, int monto) {
        validar(sistema, monto);
        return new Resultado(greedy(sistema, monto), backtracking(sistema, monto), dp(sistema, monto));
    }

    /** Greedy: moneda mas grande que entre, repetir. -1 si queda resto sin cubrir. */
    public int greedy(int[] sistema, int monto) {
        int[] monedas = ordenadoDescendente(sistema);
        int restante = monto;
        int usadas = 0;
        for (int moneda : monedas) {
            usadas += restante / moneda;
            restante %= moneda;
        }
        return restante == 0 ? usadas : -1;
    }

    /** Backtracking con poda por cota. -1 si el monto es inalcanzable. */
    public int backtracking(int[] sistema, int monto) {
        int[] monedas = ordenadoDescendente(sistema);
        int mejor = explorar(monedas, 0, monto, 0, Integer.MAX_VALUE);
        return mejor == Integer.MAX_VALUE ? -1 : mejor;
    }

    private int explorar(int[] monedas, int desde, int restante, int usadas, int mejor) {
        if (restante == 0) {
            return Math.min(mejor, usadas);
        }
        // Poda por cota: aunque la proxima moneda cierre el monto de un
        // saque, gastariamos usadas + 1; si eso no mejora, no seguimos.
        if (usadas + 1 >= mejor) {
            return mejor;
        }
        for (int i = desde; i < monedas.length; i++) {
            if (monedas[i] <= restante) {
                // "desde = i" evita generar la misma combinacion en otro orden.
                mejor = explorar(monedas, i, restante - monedas[i], usadas + 1, mejor);
            }
        }
        return mejor;
    }

    /** PD clasica del vuelto. -1 si el monto es inalcanzable. */
    public int dp(int[] sistema, int monto) {
        int[] dp = new int[monto + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int m = 1; m <= monto; m++) {
            for (int moneda : sistema) {
                if (moneda <= m && dp[m - moneda] != Integer.MAX_VALUE) {
                    dp[m] = Math.min(dp[m], dp[m - moneda] + 1);
                }
            }
        }
        return dp[monto] == Integer.MAX_VALUE ? -1 : dp[monto];
    }

    private int[] ordenadoDescendente(int[] sistema) {
        int[] copia = Arrays.copyOf(sistema, sistema.length);
        Arrays.sort(copia);
        // Invertimos el orden ascendente de Arrays.sort.
        for (int i = 0; i < copia.length / 2; i++) {
            int aux = copia[i];
            copia[i] = copia[copia.length - 1 - i];
            copia[copia.length - 1 - i] = aux;
        }
        return copia;
    }

    private void validar(int[] sistema, int monto) {
        if (sistema == null || sistema.length == 0 || monto < 0) {
            throw new IllegalArgumentException("Sistema no vacio y monto >= 0");
        }
        for (int moneda : sistema) {
            if (moneda <= 0) {
                throw new IllegalArgumentException("Las monedas deben ser > 0, vino " + moneda);
            }
        }
    }

    /** Demostracion: el greedy falla en {1,3,4} y acierta en {1,5,10,25}. */
    public static void main(String[] args) {
        TorneoDeTecnicas torneo = new TorneoDeTecnicas();
        System.out.println("{1,3,4} monto 6      -> " + torneo.comparar(new int[] {1, 3, 4}, 6));
        System.out.println("{1,5,10,25} monto 30 -> " + torneo.comparar(new int[] {1, 5, 10, 25}, 30));
        System.out.println("{1,5,10,25} monto 63 -> " + torneo.comparar(new int[] {1, 5, 10, 25}, 63));
    }
}
