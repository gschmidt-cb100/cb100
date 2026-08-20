package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <b>Prim</b>: la otra forma de calcular el árbol de tendido mínimo.
 * <p>
 * En vez de mirar las aristas globalmente (Kruskal), Prim hace CRECER un
 * árbol desde un vértice inicial: en cada paso agrega la arista MÁS BARATA
 * que conecta el árbol con un vértice de afuera. La cola de prioridad (U9)
 * entrega esa arista en O(log m).
 * <p>
 * Costo: O(m log m). Llega al MISMO costo total que Kruskal (con pesos
 * distintos, incluso al mismo árbol), aunque elija los tramos en otro orden.
 */
public final class Prim {

    private Prim() {}

    /**
     * Los n−1 tramos del árbol de tendido mínimo, creciendo desde el vértice inicial.
     *
     * @throws IllegalArgumentException si el grafo es dirigido
     * @throws IllegalStateException    si el grafo no es conexo
     */
    public static List<Tramo> arbolDeTendidoMinimo(Grafo grafo, int inicial) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException("el árbol de tendido se define sobre grafos no dirigidos");
        }
        int n = grafo.cantidadDeVertices();
        boolean[] enElArbol = new boolean[n];
        PriorityQueue<Tramo> frontera =                     // aristas árbol→afuera, la más barata primero
                new PriorityQueue<>(Comparator.comparingInt(Tramo::peso));
        List<Tramo> arbol = new ArrayList<>();

        agregar(grafo, inicial, enElArbol, frontera);       // el árbol arranca con un solo vértice

        while (!frontera.isEmpty() && arbol.size() < n - 1) {
            Tramo tramo = frontera.poll();                  // la arista más barata de la frontera
            if (enElArbol[tramo.destino()]) {
                continue;                                   // ya entró por otro lado: descartada
            }
            arbol.add(tramo);                               // ¡nuevo tramo del árbol!
            agregar(grafo, tramo.destino(), enElArbol, frontera);
        }

        if (arbol.size() < n - 1) {
            throw new IllegalStateException("el grafo no es conexo: no se puede tender un solo árbol");
        }
        return arbol;
    }

    /** Mete un vértice al árbol y ofrece sus aristas hacia afuera como candidatas. */
    private static void agregar(Grafo grafo, int vertice, boolean[] enElArbol,
                                PriorityQueue<Tramo> frontera) {
        enElArbol[vertice] = true;
        for (Grafo.Arista arista : grafo.vecinos(vertice)) {
            if (!enElArbol[arista.destino()]) {
                frontera.add(new Tramo(vertice, arista.destino(), arista.peso()));
            }
        }
    }

    public static void main(String[] args) {
        // La misma red de fibra que en Kruskal: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        List<Tramo> arbol = arbolDeTendidoMinimo(red, 0);
        System.out.println(arbol);
        // [Tramo[origen=0, destino=1, peso=2], Tramo[origen=1, destino=2, peso=1],
        //  Tramo[origen=1, destino=3, peso=4], Tramo[origen=3, destino=4, peso=5],
        //  Tramo[origen=4, destino=5, peso=3]]  = A-B, B-C, B-D, D-E, E-F
        System.out.println(Tramo.costoTotal(arbol));   // 15 — el mismo costo que Kruskal
    }
}
