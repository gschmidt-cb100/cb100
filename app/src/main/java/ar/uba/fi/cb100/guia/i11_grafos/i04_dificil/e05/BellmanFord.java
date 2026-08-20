package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e05;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TECNICA: BELLMAN-FORD — caminos minimos CON pesos negativos.
 *
 * Dijkstra es goloso: confirma al pendiente mas cercano suponiendo que
 * ningun camino futuro podra mejorarlo. Con una arista NEGATIVA esa
 * suposicion se cae: un desvio "caro" puede terminar siendo mas barato
 * despues de un descuento.
 *
 * EJEMPLO A MANO (el del test): grafo dirigido con
 *   0->1 peso 1,  0->2 peso 2,  2->1 peso -2.
 * Dijkstra de libro confirma 1 con distancia 1 (era el pendiente mas
 * cercano) y no lo revisa nunca mas. Pero el camino 0->2->1 cuesta
 * 2 + (-2) = 0 &lt; 1: Dijkstra se equivoca. Bellman-Ford responde 0.
 *
 * La idea de Bellman-Ford es abandonar la golosina y ser paciente:
 * relajar TODAS las aristas, n-1 veces. Tras la ronda k estan correctas
 * todas las distancias alcanzables con caminos de hasta k aristas; como un
 * camino simple tiene a lo sumo n-1 aristas, n-1 rondas alcanzan.
 *
 * Si en una ronda EXTRA alguna distancia todavia mejora, es que hay un
 * CICLO NEGATIVO alcanzable (dando vueltas se "gana plata" sin limite):
 * el problema no tiene solucion y lo señalamos con una excepcion.
 *
 * Costo: O(n * m) — mas caro que Dijkstra, pero es el precio de admitir
 * pesos negativos.
 */
public final class BellmanFord {

    private BellmanFord() {}

    public static final int INFINITO = Integer.MAX_VALUE;

    /** Distancias minimas desde el origen y el anterior de cada vertice. */
    public record Resultado(int[] distancia, int[] anterior) {}

    /**
     * Caminos minimos desde el origen en un grafo DIRIGIDO, admitiendo
     * pesos negativos.
     *
     * @throws IllegalArgumentException si el grafo no es dirigido
     * @throws IllegalStateException    si hay un ciclo negativo alcanzable desde el origen
     */
    public static Resultado caminosMinimos(Grafo grafo, int origen) {
        if (!grafo.esDirigido()) {
            // En un no dirigido, una sola arista negativa ya es un "ciclo"
            // de ida y vuelta: restringimos a dirigidos, como el enunciado.
            throw new IllegalArgumentException("esta version de Bellman-Ford requiere un grafo dirigido");
        }
        int n = grafo.cantidadDeVertices();

        // Lista plana de aristas: Bellman-Ford no recorre vecinos, recorre ARISTAS.
        List<Tramo> aristas = new ArrayList<>();
        for (int vertice = 0; vertice < n; vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                aristas.add(new Tramo(vertice, arista.destino(), arista.peso()));
            }
        }

        int[] distancia = new int[n];
        int[] anterior = new int[n];
        Arrays.fill(distancia, INFINITO);
        Arrays.fill(anterior, -1);
        distancia[origen] = 0;

        // n-1 rondas de relajacion de TODAS las aristas.
        for (int ronda = 1; ronda <= n - 1; ronda++) {
            boolean huboCambios = false;
            for (Tramo tramo : aristas) {
                if (relajar(tramo, distancia, anterior)) {
                    huboCambios = true;
                }
            }
            if (!huboCambios) {
                break;                  // ya se estabilizo: las rondas restantes no cambian nada
            }
        }

        // Ronda EXTRA: si algo todavia mejora, hay ciclo negativo alcanzable.
        for (Tramo tramo : aristas) {
            if (distancia[tramo.origen()] != INFINITO
                    && distancia[tramo.origen()] + tramo.peso() < distancia[tramo.destino()]) {
                throw new IllegalStateException(
                        "hay un ciclo negativo alcanzable: el camino minimo no esta definido");
            }
        }
        return new Resultado(distancia, anterior);
    }

    /** Relaja origen->destino; devuelve true si mejoro la distancia al destino. */
    private static boolean relajar(Tramo tramo, int[] distancia, int[] anterior) {
        if (distancia[tramo.origen()] == INFINITO) {
            return false;               // desde un inalcanzable no se relaja nada
        }
        int nuevaDistancia = distancia[tramo.origen()] + tramo.peso();
        if (nuevaDistancia < distancia[tramo.destino()]) {
            distancia[tramo.destino()] = nuevaDistancia;
            anterior[tramo.destino()] = tramo.origen();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // El ejemplo del javadoc: el descuento 2->1 hace fallar a Dijkstra.
        Grafo grafo = new Grafo(3, true);
        grafo.agregarArista(0, 1, 1);
        grafo.agregarArista(0, 2, 2);
        grafo.agregarArista(2, 1, -2);

        Resultado resultado = caminosMinimos(grafo, 0);
        System.out.println(Arrays.toString(resultado.distancia()));   // [0, 0, 2]

        // Un ciclo negativo: 1->2->1 suma -1 por vuelta.
        Grafo tramposo = new Grafo(3, true);
        tramposo.agregarArista(0, 1, 1);
        tramposo.agregarArista(1, 2, 2);
        tramposo.agregarArista(2, 1, -3);
        try {
            caminosMinimos(tramposo, 0);
        } catch (IllegalStateException e) {
            System.out.println("Detectado: " + e.getMessage());
        }
    }
}
