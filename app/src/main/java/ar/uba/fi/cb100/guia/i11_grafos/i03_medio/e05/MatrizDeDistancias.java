package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e05;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.Arrays;

/**
 * e05: todos los caminos mínimos — Dijkstra desde cada vértice.
 *
 * <p><b>Modelo:</b> a veces no alcanza con las distancias desde UN origen:
 * queremos la tabla completa "de todos contra todos" (por ejemplo, para
 * elegir dónde instalar un depósito). La solución más simple: correr
 * Dijkstra n veces, una por origen.</p>
 *
 * <p><b>Técnica:</b> reusar {@code Dijkstra.caminosMinimos} y apilar los
 * arreglos de distancia como filas de una matriz. {@code matriz[i][j]} es el
 * costo mínimo de i a j ({@code Dijkstra.INFINITO} si no hay camino). En un
 * grafo no dirigido la matriz queda simétrica. Costo: O(n · (n + m) log n).</p>
 */
public final class MatrizDeDistancias {

    private MatrizDeDistancias() {
    }

    /** Fila i = distancias mínimas desde el vértice i hacia todos. */
    public static int[][] todasLasDistancias(Grafo grafo) {
        int n = grafo.cantidadDeVertices();
        int[][] matriz = new int[n][];
        for (int origen = 0; origen < n; origen++) {
            matriz[origen] = Dijkstra.caminosMinimos(grafo, origen).distancia();
        }
        return matriz;
    }

    public static void main(String[] args) {
        // El mapa de rutas del apunte: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo mapa = new Grafo(6, false);
        mapa.agregarArista(0, 1, 7);    // A-B  7
        mapa.agregarArista(0, 2, 9);    // A-C  9
        mapa.agregarArista(0, 5, 14);   // A-F 14
        mapa.agregarArista(1, 2, 10);   // B-C 10
        mapa.agregarArista(1, 3, 15);   // B-D 15
        mapa.agregarArista(2, 3, 11);   // C-D 11
        mapa.agregarArista(2, 5, 2);    // C-F  2
        mapa.agregarArista(3, 4, 6);    // D-E  6
        mapa.agregarArista(4, 5, 9);    // E-F  9

        for (int[] fila : todasLasDistancias(mapa)) {
            System.out.println(Arrays.toString(fila));
        }
        // [0, 7, 9, 20, 20, 11]
        // [7, 0, 10, 15, 21, 12]
        // [9, 10, 0, 11, 11, 2]
        // [20, 15, 11, 0, 6, 13]
        // [20, 21, 11, 6, 0, 9]
        // [11, 12, 2, 13, 9, 0]
    }
}
