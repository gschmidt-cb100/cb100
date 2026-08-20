package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e07;

import java.util.Arrays;

/**
 * TECNICA: BACKTRACKING vs PROGRAMACION DINAMICA sobre el mismo problema.
 *
 * Problema: dado un arreglo de enteros NO NEGATIVOS, asignarle un signo
 * (+ o -) a cada elemento y contar de cuantas formas la suma resultante
 * da exactamente el objetivo. Ejemplo clasico: [1,1,1,1,1] con objetivo
 * 3 tiene 5 formas (elegir cual de los cinco unos lleva el menos).
 *
 * Version 1 - Backtracking: arbol binario de decisiones (un signo por
 * elemento), 2^n hojas. Sirve como referencia y para arreglos chicos.
 *
 * Version 2 - Programacion dinamica via una REDUCCION a subset-sum:
 * si P es el subconjunto con signo + y N el de signo -, entonces
 *   suma(P) - suma(N) = objetivo   y   suma(P) + suma(N) = S (suma total)
 * Sumando ambas: 2 * suma(P) = S + objetivo. O sea, contar asignaciones
 * de signos equivale a contar SUBCONJUNTOS que suman (S + objetivo) / 2.
 * Si S + objetivo es negativo o impar, no hay ninguna forma.
 * El conteo de subconjuntos es la PD clasica del subset-sum:
 *   dp[c] = cantidad de subconjuntos de los elementos vistos que suman c
 *   al agregar el elemento a: dp[c] += dp[c - a] (recorriendo c de mayor
 *   a menor para no reutilizar el mismo elemento dos veces).
 * Los ceros quedan bien contados solos: cada 0 puede ir con + o con -,
 * y la PD lo refleja porque "tomar" o "no tomar" un 0 da la misma suma
 * y por eso duplica dp[c], igual que duplicaria el backtracking.
 *
 * Complejidad: backtracking O(2^n); PD O(n * (S + objetivo)) en tiempo
 * y O(S + objetivo) en espacio. Usamos long porque el conteo explota.
 */
public class SumaConSignos {

    /** Conteo por fuerza exhaustiva: prueba los 2^n signos. */
    public long formasBacktracking(int[] a, int objetivo) {
        validar(a);
        return contar(a, 0, 0, objetivo);
    }

    private long contar(int[] a, int indice, long acumulado, int objetivo) {
        if (indice == a.length) {
            return acumulado == objetivo ? 1 : 0;
        }
        // Decision binaria: signo + o signo - para a[indice].
        return contar(a, indice + 1, acumulado + a[indice], objetivo)
                + contar(a, indice + 1, acumulado - a[indice], objetivo);
    }

    /** Conteo por PD, reduciendo a contar subconjuntos que suman (S + objetivo) / 2. */
    public long formasDp(int[] a, int objetivo) {
        validar(a);
        long sumaTotal = 0;
        for (int valor : a) {
            sumaTotal += valor;
        }
        // El objetivo alcanzable esta en [-S, S] y ademas S + objetivo debe ser par.
        long doblePositivo = sumaTotal + objetivo;
        if (doblePositivo < 0 || doblePositivo % 2 != 0) {
            return 0;
        }
        int sumaPositiva = (int) (doblePositivo / 2);

        long[] dp = new long[sumaPositiva + 1];
        dp[0] = 1; // El subconjunto vacio suma 0.
        for (int valor : a) {
            // De mayor a menor para que cada elemento se use a lo sumo una vez.
            for (int c = sumaPositiva; c >= valor; c--) {
                dp[c] += dp[c - valor];
            }
        }
        return dp[sumaPositiva];
    }

    private void validar(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        for (int valor : a) {
            if (valor < 0) {
                throw new IllegalArgumentException("Solo se admiten valores >= 0, vino " + valor);
            }
        }
    }

    /** Demostracion: ambas tecnicas cuentan lo mismo. */
    public static void main(String[] args) {
        SumaConSignos contadora = new SumaConSignos();
        int[] datos = {1, 1, 1, 1, 1};
        System.out.println("Arreglo " + Arrays.toString(datos) + ", objetivo 3:");
        System.out.println("  backtracking = " + contadora.formasBacktracking(datos, 3));
        System.out.println("  dp           = " + contadora.formasDp(datos, 3));
    }
}
