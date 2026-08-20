package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e06;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.GrafoMatriz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversorAMatrizTest {

    /** Aristas con peso del grafo de prueba: {origen, destino, peso}. */
    private static final int[][] ARISTAS = {
            {0, 1, 7}, {0, 2, 9}, {1, 3, 15}, {2, 3, 11}
    };

    @Test
    @DisplayName("La conversion coincide celda a celda con un GrafoMatriz armado igual")
    void coincideConGrafoMatriz() {
        Grafo lista = new Grafo(4, false);
        GrafoMatriz matrizPosta = new GrafoMatriz(4, false);
        for (int[] arista : ARISTAS) {
            lista.agregarArista(arista[0], arista[1], arista[2]);
            matrizPosta.agregarArista(arista[0], arista[1], arista[2]);
        }

        int[][] convertida = ConversorAMatriz.aMatriz(lista);
        for (int o = 0; o < 4; o++) {
            for (int d = 0; d < 4; d++) {
                assertEquals(matrizPosta.peso(o, d), convertida[o][d],
                        "difieren en la celda [" + o + "][" + d + "]");
            }
        }
    }

    @Test
    @DisplayName("En un grafo no dirigido la matriz queda simetrica")
    void simetrica() {
        Grafo lista = new Grafo(4, false);
        for (int[] arista : ARISTAS) {
            lista.agregarArista(arista[0], arista[1], arista[2]);
        }
        int[][] matriz = ConversorAMatriz.aMatriz(lista);
        for (int o = 0; o < 4; o++) {
            for (int d = 0; d < 4; d++) {
                assertEquals(matriz[o][d], matriz[d][o]);
            }
        }
    }

    @Test
    @DisplayName("En un dirigido la arista aparece en un solo sentido")
    void dirigido() {
        Grafo lista = new Grafo(3, true);
        lista.agregarArista(0, 1, 5);
        int[][] matriz = ConversorAMatriz.aMatriz(lista);
        assertEquals(5, matriz[0][1]);
        assertEquals(GrafoMatriz.SIN_ARISTA, matriz[1][0]);   // la vuelta no existe
    }

    @Test
    @DisplayName("Donde no hay arista queda SIN_ARISTA, incluida la diagonal")
    void celdasVacias() {
        Grafo lista = new Grafo(3, false);
        lista.agregarArista(0, 1, 2);
        int[][] matriz = ConversorAMatriz.aMatriz(lista);
        assertEquals(GrafoMatriz.SIN_ARISTA, matriz[0][2]);
        assertEquals(GrafoMatriz.SIN_ARISTA, matriz[0][0]);   // sin bucles: diagonal vacia
    }
}
