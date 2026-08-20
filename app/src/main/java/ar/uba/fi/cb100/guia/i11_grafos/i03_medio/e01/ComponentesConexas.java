package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e01;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * e01: componentes conexas de un grafo NO dirigido — BFS repetido.
 *
 * <p><b>Modelo:</b> una componente conexa es un "grupo" de vértices donde
 * todos se alcanzan entre sí. Un grafo conexo tiene una sola; una red
 * partida tiene varias.</p>
 *
 * <p><b>Técnica:</b> el BFS desde un vértice pinta exactamente su componente.
 * Entonces recorremos los vértices en orden y, cada vez que encontramos uno
 * sin pintar, arrancó una componente nueva: le hacemos BFS y marcamos a todos
 * sus alcanzables con el mismo número. Cada vértice y cada arista se visitan
 * una sola vez en total: O(n + m).</p>
 */
public final class ComponentesConexas {

    private ComponentesConexas() {
    }

    /** Cuántas componentes conexas tiene el grafo. */
    public static int cantidadDeComponentes(Grafo grafo) {
        int[] componente = componenteDe(grafo);
        int maximo = -1;
        for (int numero : componente) {
            maximo = Math.max(maximo, numero);
        }
        return maximo + 1;                          // las numeramos desde 0
    }

    /**
     * El número de componente (0, 1, 2, ...) de cada vértice. Dos vértices
     * están conectados si y sólo si tienen el mismo número.
     */
    public static int[] componenteDe(Grafo grafo) {
        int n = grafo.cantidadDeVertices();
        int[] componente = new int[n];
        Arrays.fill(componente, -1);                // -1 = todavía sin pintar

        int numero = 0;
        for (int vertice = 0; vertice < n; vertice++) {
            if (componente[vertice] == -1) {        // arrancó una componente nueva
                pintarConBfs(grafo, vertice, numero, componente);
                numero++;
            }
        }
        return componente;
    }

    /** BFS clásico que marca todos los alcanzables con el mismo número. */
    private static void pintarConBfs(Grafo grafo, int origen, int numero,
                                     int[] componente) {
        Deque<Integer> cola = new ArrayDeque<>();
        componente[origen] = numero;                // se marca al ENCOLAR
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            int vertice = cola.removeFirst();
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (componente[arista.destino()] == -1) {
                    componente[arista.destino()] = numero;
                    cola.addLast(arista.destino());
                }
            }
        }
    }

    public static void main(String[] args) {
        // Dos triángulos separados y un vértice suelto: 3 componentes.
        Grafo grafo = new Grafo(7, false);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 0);
        grafo.agregarArista(3, 4);
        grafo.agregarArista(4, 5);
        grafo.agregarArista(5, 3);
        // el 6 queda solo

        System.out.println(cantidadDeComponentes(grafo));          // 3
        System.out.println(Arrays.toString(componenteDe(grafo)));  // [0, 0, 0, 1, 1, 1, 2]
    }
}
