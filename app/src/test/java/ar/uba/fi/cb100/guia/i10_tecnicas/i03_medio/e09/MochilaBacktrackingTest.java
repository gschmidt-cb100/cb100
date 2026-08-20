package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MochilaBacktrackingTest {

    @Test
    @DisplayName("Caso clásico: pesos {2,3,4}, valores {3,4,5}, capacidad 5 -> 7")
    void casoClasico() {
        int[] pesos = {2, 3, 4};
        int[] valores = {3, 4, 5};
        assertEquals(dpDeReferencia(pesos, valores, 5),
                MochilaBacktracking.valorMaximo(pesos, valores, 5));
        assertEquals(7, MochilaBacktracking.valorMaximo(pesos, valores, 5));
    }

    @Test
    @DisplayName("Coincide con la DP en un caso donde el greedy por valor falla")
    void greedyFalla() {
        // El objeto de valor 60 tienta, pero 40 + 50 = 90 es mejor.
        int[] pesos = {10, 4, 6};
        int[] valores = {60, 40, 50};
        int capacidad = 10;
        assertEquals(dpDeReferencia(pesos, valores, capacidad),
                MochilaBacktracking.valorMaximo(pesos, valores, capacidad));
        assertEquals(90, MochilaBacktracking.valorMaximo(pesos, valores, capacidad));
    }

    @Test
    @DisplayName("Coincide con la DP en un caso pseudoaleatorio de 15 objetos")
    void casoAleatorioDeterministico() {
        Random azar = new Random(42);
        int n = 15;
        int[] pesos = new int[n];
        int[] valores = new int[n];
        for (int i = 0; i < n; i++) {
            pesos[i] = 1 + azar.nextInt(20);
            valores[i] = 1 + azar.nextInt(100);
        }
        int capacidad = 50;
        assertEquals(dpDeReferencia(pesos, valores, capacidad),
                MochilaBacktracking.valorMaximo(pesos, valores, capacidad));
    }

    @Test
    @DisplayName("Capacidad 0 o sin objetos: el valor máximo es 0")
    void casosBorde() {
        assertEquals(0, MochilaBacktracking.valorMaximo(
                new int[] {1, 2}, new int[] {10, 20}, 0));
        assertEquals(0, MochilaBacktracking.valorMaximo(
                new int[] {}, new int[] {}, 100));
    }

    /** DP clásica de mochila 0/1, usada como oráculo de referencia. */
    private static int dpDeReferencia(int[] pesos, int[] valores, int capacidad) {
        int[] dp = new int[capacidad + 1];
        for (int i = 0; i < pesos.length; i++) {
            for (int c = capacidad; c >= pesos[i]; c--) {
                dp[c] = Math.max(dp[c], dp[c - pesos[i]] + valores[i]);
            }
        }
        return dp[capacidad];
    }
}
