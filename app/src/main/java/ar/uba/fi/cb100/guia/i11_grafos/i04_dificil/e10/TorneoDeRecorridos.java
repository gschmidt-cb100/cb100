package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TECNICA: TESTS DE PROPIEDAD (property-based testing) SOBRE GRAFOS.
 *
 * En vez de verificar resultados puntuales calculados a mano, generamos
 * MUCHOS grafos al azar (con semilla fija, para que el test sea
 * reproducible) y verificamos PROPIEDADES que deben valer siempre:
 * <ul>
 *   <li>BFS y DFS visitan el mismo CONJUNTO de vertices: los alcanzables
 *       desde el origen no dependen del orden en que se los explore;</li>
 *   <li>si todas las aristas pesan 1, "menos aristas" y "menor peso total"
 *       son lo mismo: BFS ({@code distanciasDesde}) y Dijkstra deben
 *       coincidir distancia por distancia;</li>
 *   <li>Prim y Kruskal construyen el arbol de tendido minimo por caminos
 *       distintos, pero el costo total optimo es unico: debe dar igual.</li>
 * </ul>
 *
 * Este estilo de test es un arma profesional seria: cada algoritmo actua
 * de CONTROL del otro (si BFS y Dijkstra discreparan, alguno esta roto),
 * y los grafos aleatorios pisan esquinas que a nadie se le ocurren a mano.
 *
 * El generador garantiza CONEXION armando primero un arbol al azar (el
 * vertice v se cuelga de un vertice anterior elegido al azar: v aristas
 * nunca forman ciclo y tocan a todos) y agregando despues aristas extra.
 */
public final class TorneoDeRecorridos {

    private TorneoDeRecorridos() {}

    /** Una arista cruda: extremos y peso. Sirve para armar variantes del mismo grafo. */
    public record AristaCruda(int origen, int destino, int peso) {}

    /**
     * Las aristas de un grafo conexo aleatorio de n vertices: un arbol
     * aleatorio (garantiza conexion) mas {@code extras} aristas al azar.
     * Pesos entre 1 y {@code pesoMaximo}.
     */
    public static List<AristaCruda> aristasConexasAlAzar(Random azar, int n,
                                                         int extras, int pesoMaximo) {
        List<AristaCruda> aristas = new ArrayList<>();
        for (int vertice = 1; vertice < n; vertice++) {
            // Colgarse de un vertice ANTERIOR garantiza arbol (y por ende conexion).
            aristas.add(new AristaCruda(vertice, azar.nextInt(vertice),
                    1 + azar.nextInt(pesoMaximo)));
        }
        for (int i = 0; i < extras; i++) {
            int origen = azar.nextInt(n);
            int destino = azar.nextInt(n);
            if (origen != destino) {
                aristas.add(new AristaCruda(origen, destino, 1 + azar.nextInt(pesoMaximo)));
            }
        }
        return aristas;
    }

    /**
     * Materializa las aristas en un Grafo no dirigido. Con
     * {@code pesoUnitario} en true, ignora los pesos y pone 1 en todas:
     * el MISMO grafo en topologia, listo para comparar BFS contra Dijkstra.
     */
    public static Grafo armar(int n, List<AristaCruda> aristas, boolean pesoUnitario) {
        Grafo grafo = new Grafo(n, false);
        for (AristaCruda arista : aristas) {
            grafo.agregarArista(arista.origen(), arista.destino(),
                    pesoUnitario ? 1 : arista.peso());
        }
        return grafo;
    }

    public static void main(String[] args) {
        Random azar = new Random(123);
        List<AristaCruda> aristas = aristasConexasAlAzar(azar, 8, 5, 9);
        Grafo grafo = armar(8, aristas, false);
        System.out.println("Grafo de " + grafo.cantidadDeVertices() + " vertices y "
                + grafo.cantidadDeAristas() + " aristas");
        System.out.println("BFS: " + ar.uba.fi.cb100.material.i11_grafos.Recorridos.bfs(grafo, 0));
        System.out.println("DFS: " + ar.uba.fi.cb100.material.i11_grafos.Recorridos.dfs(grafo, 0));
    }
}
