package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <b>Dijkstra</b>: caminos mínimos desde un origen en grafos con pesos
 * NO NEGATIVOS.
 * <p>
 * Es un goloso (U10) que funciona: en cada paso confirma el vértice
 * pendiente más cercano al origen. Como los pesos no son negativos, ningún
 * camino que pase por un vértice más lejano puede mejorarlo después.
 * <p>
 * La cola de prioridad (U9) entrega siempre "el pendiente más cercano" en
 * O(log n). Costo total: O((n + m) log n).
 */
public final class Dijkstra {

    private Dijkstra() {}

    public static final int INFINITO = Integer.MAX_VALUE;

    /** Un vértice esperando en la cola de prioridad, con su distancia tentativa. */
    private record Pendiente(int vertice, int distancia) {}

    /** Distancias mínimas + de dónde vino cada vértice (para armar caminos). */
    public record Resultado(int[] distancia, int[] anterior) {

        /** El camino mínimo origen→destino como lista de vértices (vacía si no hay). */
        public List<Integer> caminoHasta(int destino) {
            if (distancia[destino] == INFINITO) {
                return List.of();                          // inalcanzable
            }
            List<Integer> camino = new ArrayList<>();
            for (int v = destino; v != -1; v = anterior[v]) {
                camino.add(v);                             // lo armamos de atrás hacia adelante
            }
            return camino.reversed();
        }
    }

    public static Resultado caminosMinimos(Grafo grafo, int origen) {
        int n = grafo.cantidadDeVertices();
        int[] distancia = new int[n];
        int[] anterior = new int[n];
        boolean[] confirmado = new boolean[n];
        Arrays.fill(distancia, INFINITO);
        Arrays.fill(anterior, -1);

        PriorityQueue<Pendiente> cola =
                new PriorityQueue<>(Comparator.comparingInt(Pendiente::distancia));
        distancia[origen] = 0;
        cola.add(new Pendiente(origen, 0));

        while (!cola.isEmpty()) {
            Pendiente pendiente = cola.poll();             // el más cercano al origen
            int vertice = pendiente.vertice();
            if (confirmado[vertice]) {
                continue;                                  // copia vieja: ya lo confirmamos mejor
            }
            confirmado[vertice] = true;                    // su distancia es DEFINITIVA

            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                int nuevaDistancia = distancia[vertice] + arista.peso();
                if (nuevaDistancia < distancia[arista.destino()]) {
                    distancia[arista.destino()] = nuevaDistancia;   // relajar la arista
                    anterior[arista.destino()] = vertice;
                    cola.add(new Pendiente(arista.destino(), nuevaDistancia));
                }
            }
        }
        return new Resultado(distancia, anterior);
    }

    public static void main(String[] args) {
        // El mapa de rutas de la unidad: A=0, B=1, C=2, D=3, E=4, F=5
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

        Resultado resultado = caminosMinimos(mapa, 0);
        System.out.println(Arrays.toString(resultado.distancia()));  // [0, 7, 9, 20, 20, 11]
        System.out.println(resultado.caminoHasta(4));                // [0, 2, 5, 4] = A C F E
        System.out.println(resultado.caminoHasta(3));                // [0, 2, 3]    = A C D
    }
}
