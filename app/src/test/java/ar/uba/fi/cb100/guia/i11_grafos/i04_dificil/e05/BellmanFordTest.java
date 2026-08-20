package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e05;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BellmanFordTest {

    /**
     * Dijkstra "de libro" para grafos dirigidos, escrito aca a proposito:
     * confirma DEFINITIVAMENTE al pendiente mas cercano y nunca lo revisa.
     * Con pesos no negativos es correcto; con negativos, no — y eso es
     * exactamente lo que queremos exhibir en el test.
     */
    private static int[] dijkstraDeLibro(Grafo grafo, int origen) {
        int n = grafo.cantidadDeVertices();
        int[] distancia = new int[n];
        boolean[] confirmado = new boolean[n];
        Arrays.fill(distancia, Integer.MAX_VALUE);
        distancia[origen] = 0;
        for (int vuelta = 0; vuelta < n; vuelta++) {
            int vertice = -1;
            for (int candidato = 0; candidato < n; candidato++) {
                if (!confirmado[candidato] && distancia[candidato] != Integer.MAX_VALUE
                        && (vertice == -1 || distancia[candidato] < distancia[vertice])) {
                    vertice = candidato;
                }
            }
            if (vertice == -1) {
                break;
            }
            confirmado[vertice] = true;              // decision golosa IRREVOCABLE
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (!confirmado[arista.destino()]) { // a un confirmado no lo toca mas
                    distancia[arista.destino()] = Math.min(distancia[arista.destino()],
                            distancia[vertice] + arista.peso());
                }
            }
        }
        return distancia;
    }

    /**
     * El ejemplo calculado a mano del javadoc de BellmanFord:
     * 0->1 (1), 0->2 (2), 2->1 (-2).
     * Camino directo 0->1: cuesta 1. Camino 0->2->1: cuesta 2 - 2 = 0.
     * Dijkstra confirma 1 con distancia 1 antes de procesar 2 y ya no lo
     * corrige: responde 1 (MAL). Bellman-Ford responde 0 (BIEN).
     */
    @Test
    @DisplayName("Con una arista negativa, Dijkstra de libro se equivoca y Bellman-Ford no")
    void aristaNegativa() {
        Grafo grafo = new Grafo(3, true);
        grafo.agregarArista(0, 1, 1);
        grafo.agregarArista(0, 2, 2);
        grafo.agregarArista(2, 1, -2);

        int[] segunDijkstra = dijkstraDeLibro(grafo, 0);
        int[] segunBellmanFord = BellmanFord.caminosMinimos(grafo, 0).distancia();

        assertEquals(1, segunDijkstra[1], "Dijkstra da la respuesta golosa (incorrecta)");
        assertEquals(0, segunBellmanFord[1], "Bellman-Ford encuentra 0->2->1 = 2 + (-2) = 0");
        assertEquals(2, segunBellmanFord[2]);
        assertNotEquals(segunDijkstra[1], segunBellmanFord[1]);
    }

    @Test
    @DisplayName("Con pesos no negativos coincide con Dijkstra")
    void sinNegativosCoincide() {
        Grafo grafo = new Grafo(5, true);
        grafo.agregarArista(0, 1, 4);
        grafo.agregarArista(0, 2, 1);
        grafo.agregarArista(2, 1, 2);
        grafo.agregarArista(1, 3, 1);
        grafo.agregarArista(2, 3, 5);
        grafo.agregarArista(3, 4, 3);

        assertArrayEquals(dijkstraDeLibro(grafo, 0),
                BellmanFord.caminosMinimos(grafo, 0).distancia());
    }

    @Test
    @DisplayName("Un ciclo negativo alcanzable lanza IllegalStateException")
    void cicloNegativo() {
        // 1->2->1 suma -1 por vuelta: dando vueltas se mejora sin limite.
        Grafo tramposo = new Grafo(3, true);
        tramposo.agregarArista(0, 1, 1);
        tramposo.agregarArista(1, 2, 2);
        tramposo.agregarArista(2, 1, -3);

        assertThrows(IllegalStateException.class,
                () -> BellmanFord.caminosMinimos(tramposo, 0));
    }

    @Test
    @DisplayName("Un ciclo negativo NO alcanzable desde el origen no molesta")
    void cicloNegativoInalcanzable() {
        Grafo grafo = new Grafo(4, true);
        grafo.agregarArista(0, 1, 5);
        grafo.agregarArista(2, 3, -2);           // ciclo negativo aparte,
        grafo.agregarArista(3, 2, -2);           // aislado del origen 0

        BellmanFord.Resultado resultado = BellmanFord.caminosMinimos(grafo, 0);
        assertEquals(5, resultado.distancia()[1]);
        assertEquals(BellmanFord.INFINITO, resultado.distancia()[2]);
    }

    @Test
    @DisplayName("Los inalcanzables quedan en INFINITO")
    void inalcanzables() {
        Grafo grafo = new Grafo(3, true);
        grafo.agregarArista(1, 2, -5);           // nada sale del 0
        int[] distancia = BellmanFord.caminosMinimos(grafo, 0).distancia();
        assertEquals(0, distancia[0]);
        assertEquals(BellmanFord.INFINITO, distancia[1]);
        assertEquals(BellmanFord.INFINITO, distancia[2]);
    }

    @Test
    @DisplayName("Sobre un grafo no dirigido lanza IllegalArgumentException")
    void rechazaNoDirigido() {
        Grafo noDirigido = new Grafo(2, false);
        noDirigido.agregarArista(0, 1, -1);
        assertThrows(IllegalArgumentException.class,
                () -> BellmanFord.caminosMinimos(noDirigido, 0));
    }
}
