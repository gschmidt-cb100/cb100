package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e04;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraSinHeapTest {

    /**
     * Grafo conexo aleatorio: primero un arbol al azar (cada vertice nuevo
     * se cuelga de uno anterior, lo que GARANTIZA conexion) y despues
     * algunas aristas extra. Pesos entre 1 y 50.
     */
    private static Grafo grafoConexoAleatorio(Random azar) {
        int n = 2 + azar.nextInt(25);
        Grafo grafo = new Grafo(n, false);
        for (int vertice = 1; vertice < n; vertice++) {
            grafo.agregarArista(vertice, azar.nextInt(vertice), 1 + azar.nextInt(50));
        }
        int extras = azar.nextInt(2 * n);
        for (int i = 0; i < extras; i++) {
            int origen = azar.nextInt(n);
            int destino = azar.nextInt(n);
            if (origen != destino) {
                grafo.agregarArista(origen, destino, 1 + azar.nextInt(50));
            }
        }
        return grafo;
    }

    @Test
    @DisplayName("En 30 grafos conexos aleatorios da las mismas distancias que la version con heap")
    void coincideConLaVersionConHeap() {
        Random azar = new Random(7);
        for (int intento = 0; intento < 30; intento++) {
            Grafo grafo = grafoConexoAleatorio(azar);
            int origen = azar.nextInt(grafo.cantidadDeVertices());

            int[] sinHeap = DijkstraSinHeap.caminosMinimos(grafo, origen).distancia();
            int[] conHeap = Dijkstra.caminosMinimos(grafo, origen).distancia();

            assertArrayEquals(conHeap, sinHeap);
        }
    }

    @Test
    @DisplayName("En el mapa de la unidad da las distancias conocidas")
    void mapaDeLaUnidad() {
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

        assertArrayEquals(new int[] {0, 7, 9, 20, 20, 11},
                DijkstraSinHeap.caminosMinimos(mapa, 0).distancia());
    }

    @Test
    @DisplayName("Los inalcanzables quedan en INFINITO y con anterior -1")
    void inalcanzables() {
        Grafo grafo = new Grafo(4, false);
        grafo.agregarArista(0, 1, 5);            // 2 y 3 quedan aislados de 0
        grafo.agregarArista(2, 3, 1);

        DijkstraSinHeap.Resultado resultado = DijkstraSinHeap.caminosMinimos(grafo, 0);
        assertEquals(DijkstraSinHeap.INFINITO, resultado.distancia()[2]);
        assertEquals(DijkstraSinHeap.INFINITO, resultado.distancia()[3]);
        assertEquals(-1, resultado.anterior()[2]);
        assertEquals(0, resultado.distancia()[0]);
        assertEquals(5, resultado.distancia()[1]);
    }

    @Test
    @DisplayName("El arreglo anterior permite reconstruir un camino optimo")
    void anteriorCoherente() {
        Grafo grafo = new Grafo(4, false);
        grafo.agregarArista(0, 1, 1);
        grafo.agregarArista(1, 2, 1);
        grafo.agregarArista(0, 2, 5);            // directo, pero mas caro
        grafo.agregarArista(2, 3, 1);

        DijkstraSinHeap.Resultado resultado = DijkstraSinHeap.caminosMinimos(grafo, 0);
        assertEquals(2, resultado.distancia()[2]);   // 0-1-2, no el directo de 5
        assertEquals(1, resultado.anterior()[2]);
        assertEquals(2, resultado.anterior()[3]);
        assertEquals(3, resultado.distancia()[3]);
    }
}
