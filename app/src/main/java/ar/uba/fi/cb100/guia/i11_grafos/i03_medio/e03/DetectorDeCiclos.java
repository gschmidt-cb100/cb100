package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

/**
 * e03: detección de ciclos en grafos NO dirigidos — DFS con padre.
 *
 * <p><b>Modelo:</b> en un grafo no dirigido, si el DFS se topa con un vértice
 * YA visitado, encontró dos caminos distintos hasta él: hay un ciclo. Con una
 * trampa: como cada arista figura en los dos sentidos, el vecino "ya visitado"
 * puede ser simplemente el PADRE del que venimos (la misma arista de ida,
 * vista de vuelta), y eso NO es un ciclo.</p>
 *
 * <p><b>Técnica:</b> DFS recursivo que arrastra desde qué vértice llegó
 * (el padre) y lo saltea una vez. Se repite desde cada vértice sin visitar
 * para cubrir grafos no conexos. Costo: O(n + m).</p>
 */
public final class DetectorDeCiclos {

    private DetectorDeCiclos() {
    }

    /**
     * ¿El grafo no dirigido tiene algún ciclo?
     *
     * @throws IllegalArgumentException si el grafo es dirigido (para esos
     *                                  está {@code OrdenTopologico.tieneCiclo})
     */
    public static boolean tieneCiclo(Grafo grafo) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException(
                    "este detector es para grafos no dirigidos");
        }
        boolean[] visitado = new boolean[grafo.cantidadDeVertices()];
        for (int vertice = 0; vertice < grafo.cantidadDeVertices(); vertice++) {
            if (!visitado[vertice]
                    && hayCicloDesde(grafo, vertice, -1, visitado)) {
                return true;
            }
        }
        return false;
    }

    /** DFS que devuelve true apenas ve un visitado que no es el padre. */
    private static boolean hayCicloDesde(Grafo grafo, int vertice, int padre,
                                         boolean[] visitado) {
        visitado[vertice] = true;
        boolean padreSalteado = false;              // la arista al padre se saltea UNA vez
        for (Grafo.Arista arista : grafo.vecinos(vertice)) {
            int vecino = arista.destino();
            if (vecino == padre && !padreSalteado) {
                padreSalteado = true;               // la ida y vuelta de la misma arista
                continue;
            }
            if (visitado[vecino]) {
                return true;                        // segundo camino hasta un conocido: ¡ciclo!
            }
            if (hayCicloDesde(grafo, vecino, vertice, visitado)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Un árbol (sin ciclos)...
        Grafo arbol = new Grafo(5, false);
        arbol.agregarArista(0, 1);
        arbol.agregarArista(0, 2);
        arbol.agregarArista(1, 3);
        arbol.agregarArista(1, 4);
        System.out.println(tieneCiclo(arbol));   // false

        // ...y el grafo del apunte, que tiene ciclos (A-B-D-C-A, por ejemplo).
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F
        System.out.println(tieneCiclo(grafo));   // true
    }
}
