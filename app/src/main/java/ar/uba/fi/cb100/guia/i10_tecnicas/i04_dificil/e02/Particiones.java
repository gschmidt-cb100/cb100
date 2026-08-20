package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e02;

/**
 * TECNICA: PROGRAMACION DINAMICA (subproblemas superpuestos + tabla).
 *
 * p(n) = cantidad de PARTICIONES de n: formas de escribir n como suma de
 * enteros positivos SIN importar el orden. Por ejemplo p(5) = 7:
 * 5, 4+1, 3+2, 3+1+1, 2+2+1, 2+1+1+1, 1+1+1+1+1.
 *
 * Por que programacion dinamica: la recursion directa "particiones de n
 * con partes hasta k" se ramifica y repite los mismos subproblemas una
 * cantidad exponencial de veces. Como los subproblemas se identifican
 * con solo dos numeros (suma restante, parte maxima permitida), conviene
 * tabularlos.
 *
 * Para no contar dos veces la misma particion en distinto orden usamos
 * el MISMO esquema que el problema del vuelto con monedas ilimitadas:
 * recorremos las "monedas" (partes posibles 1..n) en el lazo EXTERNO y
 * las sumas en el interno. Asi dp[s] cuenta las formas de armar s usando
 * partes de valor a lo sumo k (el k del lazo externo), y cada particion
 * se genera una unica vez con sus partes en orden no creciente.
 *
 * Recurrencia (version 1D): al habilitar la parte k,
 *   dp[s] += dp[s - k]   para s = k..n
 * Caso base: dp[0] = 1 (la particion vacia de 0).
 *
 * Complejidad: O(n^2) en tiempo (n partes x n sumas) y O(n) en espacio.
 */
public class Particiones {

    /**
     * Cantidad de particiones de n. Precondicion: n >= 0.
     *
     * @throws IllegalArgumentException si n es negativo.
     */
    public int particiones(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0, vino " + n);
        }
        int[] dp = new int[n + 1];
        dp[0] = 1; // Unica forma de sumar 0: no usar ninguna parte.
        for (int parte = 1; parte <= n; parte++) {
            for (int suma = parte; suma <= n; suma++) {
                // Toda particion de "suma" que usa al menos una parte de valor
                // "parte" (y ninguna mayor) se obtiene de una de "suma - parte".
                dp[suma] += dp[suma - parte];
            }
        }
        return dp[n];
    }

    /** Demostracion: los primeros valores de p(n). */
    public static void main(String[] args) {
        Particiones calculadora = new Particiones();
        for (int n = 0; n <= 10; n++) {
            System.out.println("p(" + n + ") = " + calculadora.particiones(n));
        }
        System.out.println("p(50) = " + calculadora.particiones(50));
    }
}
