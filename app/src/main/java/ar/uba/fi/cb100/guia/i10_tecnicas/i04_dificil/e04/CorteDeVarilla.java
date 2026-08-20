package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TECNICA: PROGRAMACION DINAMICA con RECONSTRUCCION de la solucion.
 *
 * Problema del corte de varilla (rod cutting): tenemos una varilla de
 * largo L y una tabla de precios donde precioPorLargo[i] es lo que paga
 * el mercado por un trozo de largo i+1. Queremos cortarla (o no) en
 * trozos enteros maximizando el ingreso total.
 *
 * Por que programacion dinamica: si el primer trozo que cortamos mide j,
 * el resto es EXACTAMENTE el mismo problema con largo L - j. La recursion
 * directa mejor(L) = max_j { precio[j] + mejor(L - j) } recalcula los
 * mismos largos una cantidad exponencial de veces; con una tabla de L+1
 * entradas cada subproblema se resuelve una sola vez.
 *
 * Recurrencia: dp[0] = 0 y
 *   dp[largo] = max sobre j=1..min(largo, maxLargoConPrecio) de
 *               precio(j) + dp[largo - j]
 *
 * Reconstruccion: ademas del valor optimo guardamos, para cada largo,
 * cual fue el PRIMER corte j que alcanzo ese optimo (eleccion[largo]).
 * Despues recorremos: cortamos eleccion[L], seguimos con L - eleccion[L],
 * y asi hasta llegar a 0. Es el mismo patron "tabla de decisiones" que
 * se usa para reconstruir caminos en otros problemas de PD.
 *
 * Complejidad: O(L^2) en tiempo, O(L) en espacio.
 */
public class CorteDeVarilla {

    /**
     * Maximo ingreso posible cortando una varilla de {@code largo} con la
     * tabla dada ({@code precioPorLargo[i]} = precio del trozo de largo i+1).
     */
    public int mejorPrecio(int[] precioPorLargo, int largo) {
        return resolver(precioPorLargo, largo).valores[largo];
    }

    /**
     * Lista de largos de los trozos de una solucion optima, en el orden
     * en que se reconstruyen. La suma de los cortes es {@code largo}.
     */
    public List<Integer> cortesOptimos(int[] precioPorLargo, int largo) {
        Tablas tablas = resolver(precioPorLargo, largo);
        List<Integer> cortes = new ArrayList<>();
        int restante = largo;
        while (restante > 0) {
            int corte = tablas.elecciones[restante];
            cortes.add(corte);
            restante -= corte;
        }
        return cortes;
    }

    /** Resultado interno: tabla de valores optimos y tabla de decisiones. */
    private record Tablas(int[] valores, int[] elecciones) { }

    private Tablas resolver(int[] precioPorLargo, int largo) {
        if (largo < 0) {
            throw new IllegalArgumentException("El largo debe ser >= 0, vino " + largo);
        }
        int[] dp = new int[largo + 1];
        int[] eleccion = new int[largo + 1];
        for (int actual = 1; actual <= largo; actual++) {
            int mejor = Integer.MIN_VALUE;
            int mejorCorte = -1;
            int maximoCorte = Math.min(actual, precioPorLargo.length);
            for (int corte = 1; corte <= maximoCorte; corte++) {
                int candidato = precioPorLargo[corte - 1] + dp[actual - corte];
                if (candidato > mejor) {
                    mejor = candidato;
                    mejorCorte = corte;
                }
            }
            if (mejorCorte == -1) {
                throw new IllegalArgumentException(
                        "No hay precio para ningun trozo que entre en largo " + actual);
            }
            dp[actual] = mejor;
            eleccion[actual] = mejorCorte;
        }
        return new Tablas(dp, eleccion);
    }

    /** Demostracion: el ejemplo clasico [1, 5, 8, 9] con largo 4. */
    public static void main(String[] args) {
        CorteDeVarilla cortadora = new CorteDeVarilla();
        int[] precios = {1, 5, 8, 9};
        int largo = 4;
        System.out.println("Precios por largo 1..4: " + Arrays.toString(precios));
        System.out.println("Mejor precio para largo " + largo + ": "
                + cortadora.mejorPrecio(precios, largo));
        System.out.println("Cortes optimos: " + cortadora.cortesOptimos(precios, largo));
    }
}
