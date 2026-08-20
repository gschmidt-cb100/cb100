package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e09;

import java.util.Arrays;

/**
 * e09: mochila 0/1 resuelta por backtracking con poda.
 *
 * <p><b>Técnica: backtracking con poda por cota optimista</b>
 * (branch and bound casero). Exploramos el árbol binario de decisiones
 * "¿llevo el objeto i o no?" pero antes de entrar a cada rama calculamos
 * una <b>cota optimista</b>: el valor acumulado más la suma de valores de
 * todos los objetos que todavía no decidimos, como si entraran todos.
 * Si ni siquiera ese escenario ideal supera al mejor valor ya encontrado,
 * la rama entera no puede mejorar nada y la cortamos sin explorarla.</p>
 *
 * <p>La poda no cambia el resultado (la cota nunca subestima, así que
 * solo se descartan ramas que de verdad no pueden ganar); solo cambia
 * cuánto del árbol de 2^n hojas se recorre. Compárese con la DP de e02:
 * mismo problema, misma respuesta, técnica distinta — el backtracking no
 * depende de que la capacidad sea chica, la DP sí.</p>
 */
public final class MochilaBacktracking {

    private MochilaBacktracking() {
    }

    /**
     * Valor máximo alcanzable sin exceder la capacidad, cada objeto
     * entra entero o no entra.
     *
     * @param pesos     peso de cada objeto (todos &gt; 0)
     * @param valores   valor de cada objeto (todos &gt;= 0), alineado con {@code pesos}
     * @param capacidad capacidad total de la mochila, &gt;= 0
     * @return la suma de valores del mejor subconjunto que entra
     */
    public static int valorMaximo(int[] pesos, int[] valores, int capacidad) {
        if (pesos.length != valores.length) {
            throw new IllegalArgumentException("pesos y valores desalineados");
        }
        if (capacidad < 0) {
            throw new IllegalArgumentException("capacidad negativa: " + capacidad);
        }
        // valorRestante[i] = suma de valores desde el objeto i hasta el final:
        // es la cota optimista de lo que puede aportar lo aún no decidido.
        int[] valorRestante = new int[valores.length + 1];
        for (int i = valores.length - 1; i >= 0; i--) {
            valorRestante[i] = valorRestante[i + 1] + valores[i];
        }
        return explorar(0, capacidad, 0, 0, pesos, valores, valorRestante);
    }

    /**
     * Explora las decisiones desde el objeto {@code i} y devuelve el mejor
     * valor global conocido tras esa exploración.
     */
    private static int explorar(int i, int capacidadRestante, int valorAcumulado,
                                int mejorHastaAhora, int[] pesos, int[] valores,
                                int[] valorRestante) {
        // Poda: ni llevando TODO lo que falta se supera al mejor conocido.
        if (valorAcumulado + valorRestante[i] <= mejorHastaAhora) {
            return mejorHastaAhora;
        }
        if (i == pesos.length) {
            return Math.max(mejorHastaAhora, valorAcumulado);   // hoja del árbol
        }
        int mejor = mejorHastaAhora;
        // Rama 1: llevar el objeto i (solo si entra).
        if (pesos[i] <= capacidadRestante) {
            mejor = explorar(i + 1, capacidadRestante - pesos[i],
                    valorAcumulado + valores[i], mejor,
                    pesos, valores, valorRestante);
        }
        // Rama 2: no llevarlo.
        mejor = explorar(i + 1, capacidadRestante, valorAcumulado, mejor,
                pesos, valores, valorRestante);
        return mejor;
    }

    public static void main(String[] args) {
        int[] pesos = {2, 3, 4};
        int[] valores = {3, 4, 5};
        System.out.println("Pesos:   " + Arrays.toString(pesos));
        System.out.println("Valores: " + Arrays.toString(valores));
        System.out.println("Capacidad 5 -> valor máximo "
                + valorMaximo(pesos, valores, 5));
    }
}
