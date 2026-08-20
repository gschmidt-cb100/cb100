package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <b>Kruskal</b>: árbol de tendido mínimo (MST) en grafos NO dirigidos y
 * conexos. Conectar todos los vértices con el menor costo total posible.
 * <p>
 * Es un goloso (U10) que funciona: ordena TODAS las aristas de menor a mayor
 * peso y las va aceptando, salvo que la arista forme un ciclo (sus dos
 * extremos ya están conectados). Para saber eso al instante usa
 * {@link UnionFind}. Termina con n−1 aristas aceptadas.
 * <p>
 * Costo: O(m log m) — dominado por el ordenamiento de aristas.
 */
public final class Kruskal {

    private Kruskal() {}

    /**
     * Los n−1 tramos del árbol de tendido mínimo.
     *
     * @throws IllegalArgumentException si el grafo es dirigido
     * @throws IllegalStateException    si el grafo no es conexo
     */
    public static List<Tramo> arbolDeTendidoMinimo(Grafo grafo) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException("el árbol de tendido se define sobre grafos no dirigidos");
        }
        int n = grafo.cantidadDeVertices();

        // 1) Juntar cada arista UNA sola vez (en no dirigidos figura en ambos extremos).
        List<Tramo> aristas = new ArrayList<>();
        for (int vertice = 0; vertice < n; vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (vertice < arista.destino()) {              // evita la copia espejo
                    aristas.add(new Tramo(vertice, arista.destino(), arista.peso()));
                }
            }
        }

        // 2) Ordenarlas de menor a mayor peso: el paso goloso.
        aristas.sort(Comparator.comparingInt(Tramo::peso));

        // 3) Aceptar cada arista que NO forme ciclo.
        UnionFind grupos = new UnionFind(n);
        List<Tramo> arbol = new ArrayList<>();
        for (Tramo tramo : aristas) {
            if (grupos.unir(tramo.origen(), tramo.destino())) {  // ¿conecta grupos distintos?
                arbol.add(tramo);
                if (arbol.size() == n - 1) {
                    break;                                       // el árbol ya está completo
                }
            }
            // si unir devolvió false, la arista formaría un ciclo: se descarta
        }

        if (arbol.size() < n - 1) {
            throw new IllegalStateException("el grafo no es conexo: no se puede tender un solo árbol");
        }
        return arbol;
    }

    public static void main(String[] args) {
        // La red de fibra de la unidad: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        List<Tramo> arbol = arbolDeTendidoMinimo(red);
        System.out.println(arbol);
        // [Tramo[origen=1, destino=2, peso=1], Tramo[origen=0, destino=1, peso=2],
        //  Tramo[origen=4, destino=5, peso=3], Tramo[origen=1, destino=3, peso=4],
        //  Tramo[origen=3, destino=4, peso=5]]  = B-C, A-B, E-F, B-D, D-E
        System.out.println(Tramo.costoTotal(arbol));   // 15
    }
}
