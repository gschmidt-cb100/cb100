package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e04;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * e04: ¿es bipartito? — coloreo con BFS en dos colores.
 *
 * <p><b>Modelo:</b> un grafo es bipartito si sus vértices se pueden partir en
 * dos bandos de modo que TODA arista cruce de un bando al otro (alumnos y
 * materias, procesos y recursos...). Equivale a que se pueda pintar con dos
 * colores sin que dos vecinos compartan color.</p>
 *
 * <p><b>Técnica:</b> BFS que pinta al origen de un color y a cada vecino del
 * color contrario. Si en algún momento una arista une dos vértices del MISMO
 * color, no hay partición posible (eso pasa exactamente cuando hay un ciclo
 * de largo impar). Se repite desde cada vértice sin pintar para cubrir
 * grafos no conexos. Costo: O(n + m).</p>
 */
public final class ColoreoBipartito {

    private ColoreoBipartito() {
    }

    /** ¿Se pueden pintar los vértices con 2 colores sin vecinos iguales? */
    public static boolean esBipartito(Grafo grafo) {
        int n = grafo.cantidadDeVertices();
        int[] color = new int[n];                   // -1 = sin pintar; bandos 0 y 1
        Arrays.fill(color, -1);

        for (int vertice = 0; vertice < n; vertice++) {
            if (color[vertice] == -1                // componente nueva: la pintamos
                    && !sePuedePintarDesde(grafo, vertice, color)) {
                return false;
            }
        }
        return true;
    }

    /** BFS que pinta la componente alternando colores; false si hay conflicto. */
    private static boolean sePuedePintarDesde(Grafo grafo, int origen, int[] color) {
        Deque<Integer> cola = new ArrayDeque<>();
        color[origen] = 0;
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            int vertice = cola.removeFirst();
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                int vecino = arista.destino();
                if (color[vecino] == -1) {
                    color[vecino] = 1 - color[vertice];   // el color contrario
                    cola.addLast(vecino);
                } else if (color[vecino] == color[vertice]) {
                    return false;                   // dos vecinos del mismo bando
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Ciclo de 4 (par): alternar colores cierra bien -> bipartito.
        Grafo cicloPar = new Grafo(4, false);
        cicloPar.agregarArista(0, 1);
        cicloPar.agregarArista(1, 2);
        cicloPar.agregarArista(2, 3);
        cicloPar.agregarArista(3, 0);
        System.out.println(esBipartito(cicloPar));    // true

        // Triángulo (ciclo impar): el tercer vértice no tiene color posible.
        Grafo triangulo = new Grafo(3, false);
        triangulo.agregarArista(0, 1);
        triangulo.agregarArista(1, 2);
        triangulo.agregarArista(2, 0);
        System.out.println(esBipartito(triangulo));   // false
    }
}
