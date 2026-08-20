package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e04;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.Arrays;

/**
 * TECNICA: DIJKSTRA SIN HEAP — busqueda lineal del pendiente minimo, O(n^2).
 *
 * El Dijkstra del material usa una cola de prioridad (U9) para encontrar
 * "el vertice pendiente mas cercano" en O(log n). Esta version historica
 * (la original de 1959) lo busca RECORRIENDO el arreglo de distancias:
 * n iteraciones x busqueda O(n) = O(n^2) total.
 *
 * ¿Cuando puede GANARLE al heap? En grafos DENSOS y chicos: si m se acerca
 * a n^2, la version con heap cuesta O(m log n) = O(n^2 log n), o sea que el
 * heap paga un log n de mas por cada arista relajada, mientras que la
 * busqueda lineal paga O(n^2) pelado, sin estructuras auxiliares, con
 * recorridos secuenciales sobre un arreglo (muy amigables con la cache).
 * En grafos ralos (m ~ n), en cambio, el heap gana por paliza:
 * O(n log n) contra O(n^2).
 *
 * La logica de confirmacion y relajacion es EXACTAMENTE la misma que en la
 * version con heap: por eso el test exige que ambas den identico arreglo
 * de distancias sobre muchos grafos aleatorios.
 */
public final class DijkstraSinHeap {

    private DijkstraSinHeap() {}

    public static final int INFINITO = Integer.MAX_VALUE;

    /** Distancias minimas desde el origen y el anterior de cada vertice. */
    public record Resultado(int[] distancia, int[] anterior) {}

    /**
     * Caminos minimos desde el origen, con pesos no negativos.
     * Misma semantica que {@code Dijkstra.caminosMinimos}: INFINITO para
     * los inalcanzables, -1 en anterior para origen e inalcanzables.
     */
    public static Resultado caminosMinimos(Grafo grafo, int origen) {
        int n = grafo.cantidadDeVertices();
        int[] distancia = new int[n];
        int[] anterior = new int[n];
        boolean[] confirmado = new boolean[n];
        Arrays.fill(distancia, INFINITO);
        Arrays.fill(anterior, -1);
        distancia[origen] = 0;

        // n vueltas: en cada una se confirma exactamente un vertice.
        for (int vuelta = 0; vuelta < n; vuelta++) {

            // 1) Busqueda LINEAL del pendiente con menor distancia tentativa.
            //    Esto reemplaza al poll() del heap: O(n) en lugar de O(log n).
            int vertice = -1;
            int mejorDistancia = INFINITO;
            for (int candidato = 0; candidato < n; candidato++) {
                if (!confirmado[candidato] && distancia[candidato] < mejorDistancia) {
                    mejorDistancia = distancia[candidato];
                    vertice = candidato;
                }
            }
            if (vertice == -1) {
                break;                       // solo quedan inalcanzables
            }

            // 2) Confirmar: su distancia ya es definitiva (pesos no negativos).
            confirmado[vertice] = true;

            // 3) Relajar sus aristas, igual que en la version con heap.
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                int nuevaDistancia = distancia[vertice] + arista.peso();
                if (nuevaDistancia < distancia[arista.destino()]) {
                    distancia[arista.destino()] = nuevaDistancia;
                    anterior[arista.destino()] = vertice;
                }
            }
        }
        return new Resultado(distancia, anterior);
    }

    public static void main(String[] args) {
        // El mapa de rutas de la unidad: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo mapa = new Grafo(6, false);
        mapa.agregarArista(0, 1, 7);
        mapa.agregarArista(0, 2, 9);
        mapa.agregarArista(0, 5, 14);
        mapa.agregarArista(1, 2, 10);
        mapa.agregarArista(1, 3, 15);
        mapa.agregarArista(2, 3, 11);
        mapa.agregarArista(2, 5, 2);
        mapa.agregarArista(3, 4, 6);
        mapa.agregarArista(4, 5, 9);

        Resultado resultado = caminosMinimos(mapa, 0);
        System.out.println(Arrays.toString(resultado.distancia()));   // [0, 7, 9, 20, 20, 11]
    }
}
