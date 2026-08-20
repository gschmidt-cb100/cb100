package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e02;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * e02: reconstruir el camino con menos aristas — BFS con {@code anterior[]}.
 *
 * <p><b>Modelo:</b> BFS explora por niveles, así que el PRIMER camino con el
 * que descubre a un vértice es uno de los que menos aristas usan. El truco
 * para poder RECONSTRUIRLO (y no sólo saber su largo) es anotar, por cada
 * vértice, DESDE DÓNDE lo descubrimos: el arreglo {@code anterior[]}.</p>
 *
 * <p><b>Técnica:</b> BFS normal + {@code anterior[destino] = vertice} al
 * encolar. Al final, desde el destino seguimos los {@code anterior} hacia
 * atrás hasta el origen y damos vuelta la lista. Costo: O(n + m).</p>
 */
public final class CaminoConMenosAristas {

    private CaminoConMenosAristas() {
    }

    /**
     * El camino con menos aristas entre {@code origen} y {@code destino},
     * como lista de vértices (incluye ambos extremos). Lista vacía si no
     * hay camino.
     */
    public static List<Integer> caminoConMenosAristas(Grafo grafo, int origen,
                                                      int destino) {
        int n = grafo.cantidadDeVertices();
        int[] anterior = new int[n];                // desde dónde descubrimos a cada uno
        boolean[] visitado = new boolean[n];
        Arrays.fill(anterior, -1);

        // BFS propio: igual al de Recorridos, pero anotando el "padre".
        Deque<Integer> cola = new ArrayDeque<>();
        visitado[origen] = true;
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            int vertice = cola.removeFirst();
            if (vertice == destino) {
                break;                              // ya lo descubrimos: podemos cortar
            }
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (!visitado[arista.destino()]) {
                    visitado[arista.destino()] = true;
                    anterior[arista.destino()] = vertice;
                    cola.addLast(arista.destino());
                }
            }
        }

        if (!visitado[destino]) {
            return List.of();                       // inalcanzable
        }

        // Reconstrucción: del destino hacia atrás, y después damos vuelta.
        List<Integer> camino = new ArrayList<>();
        for (int vertice = destino; vertice != -1; vertice = anterior[vertice]) {
            camino.add(vertice);
        }
        return camino.reversed();
    }

    public static void main(String[] args) {
        // El grafo del apunte: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F

        System.out.println(caminoConMenosAristas(grafo, 0, 5));  // [0, 1, 3, 5] = A B D F
        System.out.println(caminoConMenosAristas(grafo, 4, 1));  // [4, 2, 0, 1] = E C A B (3 aristas)
    }
}
