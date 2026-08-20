package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e06;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.GrafoMatriz;

import java.util.Arrays;

/**
 * TECNICA: convertir entre las dos representaciones de la unidad.
 *
 * La LISTA de adyacencia solo guarda las aristas que existen (O(n + m) de
 * memoria); la MATRIZ reserva una celda para cada PAR de vertices (O(n^2)),
 * exista la arista o no. Convertir es barrer la lista y "pintar" cada
 * arista en su celda: fila = origen, columna = destino, valor = peso.
 * Las celdas que nadie pinta quedan en {@link GrafoMatriz#SIN_ARISTA} (-1).
 *
 * En un grafo no dirigido la matriz sale SIMETRICA sola, porque la lista
 * ya guarda cada arista en los dos sentidos.
 */
public final class ConversorAMatriz {

    private ConversorAMatriz() {}

    /** La matriz de adyacencia del grafo: celda [o][d] = peso, -1 si no hay arista. */
    public static int[][] aMatriz(Grafo grafo) {
        int n = grafo.cantidadDeVertices();
        int[][] matriz = new int[n][n];
        for (int[] fila : matriz) {
            Arrays.fill(fila, GrafoMatriz.SIN_ARISTA);      // arrancamos "todo vacio"
        }
        for (int origen = 0; origen < n; origen++) {
            for (Grafo.Arista arista : grafo.vecinos(origen)) {
                matriz[origen][arista.destino()] = arista.peso();
            }
        }
        return matriz;
    }

    public static void main(String[] args) {
        // El grafo A-F de la unidad, sin pesos (peso 1).
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F

        for (int[] fila : aMatriz(grafo)) {
            System.out.println(Arrays.toString(fila));
        }
        // Simetrica, con 1 donde hay arista y -1 donde no.
    }
}
