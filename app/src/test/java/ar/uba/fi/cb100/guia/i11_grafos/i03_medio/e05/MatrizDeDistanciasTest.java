package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e05;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrizDeDistanciasTest {

    private Grafo mapa;

    @BeforeEach
    void armarMapaDeDijkstra() {
        // A=0, B=1, C=2, D=3, E=4, F=5
        mapa = new Grafo(6, false);
        mapa.agregarArista(0, 1, 7);    // A-B  7
        mapa.agregarArista(0, 2, 9);    // A-C  9
        mapa.agregarArista(0, 5, 14);   // A-F 14
        mapa.agregarArista(1, 2, 10);   // B-C 10
        mapa.agregarArista(1, 3, 15);   // B-D 15
        mapa.agregarArista(2, 3, 11);   // C-D 11
        mapa.agregarArista(2, 5, 2);    // C-F  2
        mapa.agregarArista(3, 4, 6);    // D-E  6
        mapa.agregarArista(4, 5, 9);    // E-F  9
    }

    @Test
    @DisplayName("La fila 0 son las distancias de Dijkstra desde A")
    void filaDelOrigenA() {
        int[][] matriz = MatrizDeDistancias.todasLasDistancias(mapa);
        assertArrayEquals(new int[] {0, 7, 9, 20, 20, 11}, matriz[0]);
    }

    @Test
    @DisplayName("En un grafo no dirigido la matriz es simétrica y con diagonal 0")
    void simetricaConDiagonalCero() {
        int[][] matriz = MatrizDeDistancias.todasLasDistancias(mapa);
        int n = mapa.cantidadDeVertices();

        assertEquals(n, matriz.length);
        for (int i = 0; i < n; i++) {
            assertEquals(0, matriz[i][i], "la diagonal debe ser 0");
            for (int j = 0; j < n; j++) {
                assertEquals(matriz[i][j], matriz[j][i],
                        "d(" + i + "," + j + ") debe igualar d(" + j + "," + i + ")");
            }
        }
    }
}
