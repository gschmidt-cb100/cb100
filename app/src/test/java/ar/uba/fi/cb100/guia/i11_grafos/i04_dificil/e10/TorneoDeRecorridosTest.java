package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e10;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Prim;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TorneoDeRecorridosTest {

    private static final int CANTIDAD_DE_GRAFOS = 50;

    /** El lote de grafos del torneo, reproducible por la semilla fija. */
    private interface Prueba {
        void verificar(int n, List<TorneoDeRecorridos.AristaCruda> aristas, int numero);
    }

    private void torneo(Prueba prueba) {
        Random azar = new Random(123);
        for (int numero = 0; numero < CANTIDAD_DE_GRAFOS; numero++) {
            int n = 2 + azar.nextInt(20);
            int extras = azar.nextInt(n);
            List<TorneoDeRecorridos.AristaCruda> aristas =
                    TorneoDeRecorridos.aristasConexasAlAzar(azar, n, extras, 9);
            prueba.verificar(n, aristas, numero);
        }
    }

    @Test
    @DisplayName("(a) BFS y DFS visitan el mismo conjunto de vertices en 50 grafos")
    void bfsYDfsMismoConjunto() {
        torneo((n, aristas, numero) -> {
            Grafo grafo = TorneoDeRecorridos.armar(n, aristas, false);
            Set<Integer> porBfs = new HashSet<>(Recorridos.bfs(grafo, 0));
            Set<Integer> porDfs = new HashSet<>(Recorridos.dfs(grafo, 0));
            assertEquals(porBfs, porDfs, "difieren en el grafo " + numero);
            // Como el generador garantiza conexion, ademas deben estar todos:
            assertEquals(n, porBfs.size(), "el grafo " + numero + " deberia ser conexo");
        });
    }

    @Test
    @DisplayName("(b) Con pesos unitarios, BFS y Dijkstra dan las mismas distancias")
    void bfsContraDijkstra() {
        torneo((n, aristas, numero) -> {
            Grafo unitario = TorneoDeRecorridos.armar(n, aristas, true);
            int[] porBfs = Recorridos.distanciasDesde(unitario, 0);
            int[] porDijkstra = Dijkstra.caminosMinimos(unitario, 0).distancia();
            assertArrayEquals(porBfs, porDijkstra);
        });
    }

    @Test
    @DisplayName("(c) Prim y Kruskal calculan arboles del mismo costo total")
    void primContraKruskal() {
        torneo((n, aristas, numero) -> {
            Grafo grafo = TorneoDeRecorridos.armar(n, aristas, false);
            int costoPrim = Tramo.costoTotal(Prim.arbolDeTendidoMinimo(grafo, 0));
            int costoKruskal = Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(grafo));
            assertEquals(costoKruskal, costoPrim, "difieren en el grafo " + numero);
        });
    }

    @Test
    @DisplayName("El generador produce grafos conexos con la cantidad esperada de aristas")
    void generadorSano() {
        Random azar = new Random(9);
        List<TorneoDeRecorridos.AristaCruda> aristas =
                TorneoDeRecorridos.aristasConexasAlAzar(azar, 10, 0, 9);
        assertEquals(9, aristas.size(), "un arbol de 10 vertices tiene 9 aristas");
        Grafo arbol = TorneoDeRecorridos.armar(10, aristas, false);
        assertEquals(10, Recorridos.bfs(arbol, 0).size(), "el arbol debe ser conexo");
        for (TorneoDeRecorridos.AristaCruda arista : aristas) {
            assertTrue(arista.peso() >= 1 && arista.peso() <= 9);
        }
    }
}
