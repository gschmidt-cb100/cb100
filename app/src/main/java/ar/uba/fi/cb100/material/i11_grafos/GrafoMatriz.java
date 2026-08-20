package ar.uba.fi.cb100.material.i11_grafos;

/**
 * <b>Grafo con matriz de adyacencia</b>: una tabla de n×n donde la celda
 * [origen][destino] guarda el peso de la arista (o {@code SIN_ARISTA} si no
 * existe).
 * <p>
 * Costos con n vértices:
 * <ul>
 *   <li>memoria: O(n²) — SIEMPRE, aunque haya pocas aristas</li>
 *   <li>¿existe la arista u→v?: O(1) — el punto fuerte frente a la lista</li>
 *   <li>recorrer los vecinos de v: O(n) — hay que mirar toda la fila</li>
 * </ul>
 * Conviene sólo en grafos <b>densos</b> (muchas aristas respecto de n²) o
 * cuando la consulta "¿son vecinos?" es la operación dominante.
 */
public class GrafoMatriz {

    public static final int SIN_ARISTA = -1;

    private final int[][] peso;
    private final boolean dirigido;

    public GrafoMatriz(int cantidadDeVertices, boolean dirigido) {
        this.peso = new int[cantidadDeVertices][cantidadDeVertices];
        for (int origen = 0; origen < cantidadDeVertices; origen++) {
            for (int destino = 0; destino < cantidadDeVertices; destino++) {
                peso[origen][destino] = SIN_ARISTA;      // arranca sin aristas
            }
        }
        this.dirigido = dirigido;
    }

    public void agregarArista(int origen, int destino, int pesoDeLaArista) {
        peso[origen][destino] = pesoDeLaArista;
        if (!dirigido) {
            peso[destino][origen] = pesoDeLaArista;      // simétrica en no dirigidos
        }
    }

    /** O(1): una sola consulta a la tabla. */
    public boolean existeArista(int origen, int destino) {
        return peso[origen][destino] != SIN_ARISTA;
    }

    public int peso(int origen, int destino) {
        return peso[origen][destino];
    }

    public int cantidadDeVertices() {
        return peso.length;
    }

    public static void main(String[] args) {
        // El mismo grafo de ejemplo: A=0, B=1, C=2, D=3, E=4, F=5 (sin pesos → 1)
        GrafoMatriz grafo = new GrafoMatriz(6, false);
        grafo.agregarArista(0, 1, 1);   // A-B
        grafo.agregarArista(0, 2, 1);   // A-C
        grafo.agregarArista(1, 3, 1);   // B-D
        grafo.agregarArista(2, 3, 1);   // C-D
        grafo.agregarArista(2, 4, 1);   // C-E
        grafo.agregarArista(3, 5, 1);   // D-F
        grafo.agregarArista(4, 5, 1);   // E-F

        System.out.println(grafo.existeArista(0, 1));   // true  (en O(1))
        System.out.println(grafo.existeArista(0, 3));   // false (también en O(1))
    }
}
