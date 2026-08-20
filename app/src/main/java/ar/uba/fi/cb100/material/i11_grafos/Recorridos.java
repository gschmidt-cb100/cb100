package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Los dos recorridos fundamentales de un grafo.
 * <ul>
 *   <li><b>BFS</b> (Breadth-First Search, a lo ancho): explora por niveles con
 *       una <b>cola</b> (U5). Encuentra el camino con MENOS ARISTAS.</li>
 *   <li><b>DFS</b> (Depth-First Search, en profundidad): se mete hasta el
 *       fondo con la <b>pila</b> de llamadas (o una pila explícita, U5).</li>
 * </ul>
 * Ambos visitan cada vértice y cada arista una sola vez: O(n + m).
 */
public final class Recorridos {

    private Recorridos() {}

    // ------------------------------------------------------------------ BFS

    /** Orden en que BFS visita los vértices alcanzables desde el origen. */
    public static List<Integer> bfs(Grafo grafo, int origen) {
        List<Integer> orden = new ArrayList<>();
        boolean[] visitado = new boolean[grafo.cantidadDeVertices()];
        Deque<Integer> cola = new ArrayDeque<>();

        visitado[origen] = true;                       // se marca al ENCOLAR
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            int vertice = cola.removeFirst();          // el más viejo de la cola
            orden.add(vertice);
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (!visitado[arista.destino()]) {
                    visitado[arista.destino()] = true;
                    cola.addLast(arista.destino());    // a la fila del fondo
                }
            }
        }
        return orden;
    }

    /**
     * Distancia en ARISTAS desde el origen hasta cada vértice (el camino
     * mínimo cuando todas las aristas valen lo mismo). −1 = inalcanzable.
     */
    public static int[] distanciasDesde(Grafo grafo, int origen) {
        int[] distancia = new int[grafo.cantidadDeVertices()];
        Arrays.fill(distancia, -1);
        Deque<Integer> cola = new ArrayDeque<>();

        distancia[origen] = 0;                         // distancia hace de "visitado"
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            int vertice = cola.removeFirst();
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (distancia[arista.destino()] == -1) {
                    distancia[arista.destino()] = distancia[vertice] + 1;
                    cola.addLast(arista.destino());
                }
            }
        }
        return distancia;
    }

    // ------------------------------------------------------------------ DFS

    /** Orden en que DFS (recursivo) visita los vértices desde el origen. */
    public static List<Integer> dfs(Grafo grafo, int origen) {
        List<Integer> orden = new ArrayList<>();
        boolean[] visitado = new boolean[grafo.cantidadDeVertices()];
        dfsDesde(grafo, origen, visitado, orden);
        return orden;
    }

    private static void dfsDesde(Grafo grafo, int vertice, boolean[] visitado,
                                 List<Integer> orden) {
        visitado[vertice] = true;
        orden.add(vertice);
        for (Grafo.Arista arista : grafo.vecinos(vertice)) {
            if (!visitado[arista.destino()]) {
                dfsDesde(grafo, arista.destino(), visitado, orden);  // se mete a fondo
            }
        }
    }

    /**
     * El mismo DFS pero con pila explícita en lugar de recursión (U5: la
     * recursión ES una pila). El orden puede diferir del recursivo porque
     * la pila procesa los vecinos apilados en orden inverso.
     */
    public static List<Integer> dfsIterativo(Grafo grafo, int origen) {
        List<Integer> orden = new ArrayList<>();
        boolean[] visitado = new boolean[grafo.cantidadDeVertices()];
        Deque<Integer> pila = new ArrayDeque<>();

        pila.push(origen);
        while (!pila.isEmpty()) {
            int vertice = pila.pop();                  // el último apilado
            if (visitado[vertice]) {
                continue;                              // ya lo procesamos por otro camino
            }
            visitado[vertice] = true;
            orden.add(vertice);
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (!visitado[arista.destino()]) {
                    pila.push(arista.destino());
                }
            }
        }
        return orden;
    }

    /** ¿Hay algún camino entre dos vértices? (BFS que corta al encontrarlo) */
    public static boolean hayCamino(Grafo grafo, int origen, int destino) {
        return distanciasDesde(grafo, origen)[destino] != -1;
    }

    public static void main(String[] args) {
        // A=0, B=1, C=2, D=3, E=4, F=5
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F

        System.out.println(bfs(grafo, 0));                    // [0, 1, 2, 3, 4, 5] = A B C D E F
        System.out.println(Arrays.toString(distanciasDesde(grafo, 0)));  // [0, 1, 1, 2, 2, 3]
        System.out.println(dfs(grafo, 0));                    // [0, 1, 3, 2, 4, 5] = A B D C E F
        System.out.println(dfsIterativo(grafo, 0));           // [0, 2, 4, 5, 3, 1] = A C E F D B
        System.out.println(hayCamino(grafo, 0, 5));           // true
    }
}
