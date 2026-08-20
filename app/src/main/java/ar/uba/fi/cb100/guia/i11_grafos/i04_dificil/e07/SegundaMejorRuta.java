package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e07;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.List;

/**
 * TECNICA: SEGUNDO CAMINO MAS CORTO POR REMOCION DE ARISTAS.
 *
 * Pregunta muy real: si la mejor ruta se corta (obra, derrumbe, paro),
 * ¿cual es la siguiente? Observacion clave: el segundo mejor camino tiene
 * que EVITAR al menos una arista del camino optimo (si las usara todas,
 * seria el optimo). Entonces alcanza con:
 * <ol>
 *   <li>calcular el camino optimo con Dijkstra;</li>
 *   <li>por cada una de sus aristas, recalcular Dijkstra en el grafo SIN
 *       esa arista;</li>
 *   <li>quedarse con el mejor de esos resultados: ese es el segundo camino
 *       (distinto del optimo por construccion, porque le falta una arista).</li>
 * </ol>
 *
 * Costo: el camino optimo tiene a lo sumo n-1 aristas, asi que son O(n)
 * corridas de Dijkstra: O(n * m log n). Para redes de rutas reales
 * (miles de nodos) es perfectamente pagable.
 *
 * VERIFICACION A MANO en la red de ciudades, BA -&gt; Bariloche:
 * optimo = BA-Neuquen-Bariloche = 1150 + 430 = 1580.
 * <ul>
 *   <li>quitando BA-Neuquen: BA-Rosario-Cordoba-Mendoza-Neuquen-Bariloche
 *       = 300 + 400 + 600 + 800 + 430 = 2530;</li>
 *   <li>quitando Neuquen-Bariloche: Bariloche queda inalcanzable
 *       (era su unica ruta).</li>
 * </ul>
 * Segundo camino: 2530.
 */
public final class SegundaMejorRuta {

    private SegundaMejorRuta() {}

    public static final int INFINITO = Dijkstra.INFINITO;

    /** La segunda mejor ruta: su longitud total y sus vertices. */
    public record Resultado(int distancia, List<Integer> camino) {}

    /**
     * El segundo camino mas corto entre origen y destino en un grafo NO
     * dirigido. Si no existe (no hay ruta alternativa), devuelve un
     * Resultado con distancia INFINITO y camino vacio.
     *
     * @throws IllegalArgumentException si el grafo es dirigido o no hay
     *                                  siquiera un primer camino
     */
    public static Resultado calcular(Grafo grafo, int origen, int destino) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException("esta version trabaja sobre grafos no dirigidos");
        }
        Dijkstra.Resultado optimo = Dijkstra.caminosMinimos(grafo, origen);
        List<Integer> caminoOptimo = optimo.caminoHasta(destino);
        if (caminoOptimo.isEmpty()) {
            throw new IllegalArgumentException("no hay ningun camino de " + origen + " a " + destino);
        }

        int mejorDistancia = INFINITO;
        List<Integer> mejorCamino = List.of();

        // Quitar una a una las aristas del camino optimo y recalcular.
        for (int i = 0; i + 1 < caminoOptimo.size(); i++) {
            Grafo recortado = sinLaArista(grafo, caminoOptimo.get(i), caminoOptimo.get(i + 1));
            Dijkstra.Resultado alternativo = Dijkstra.caminosMinimos(recortado, origen);
            if (alternativo.distancia()[destino] < mejorDistancia) {
                mejorDistancia = alternativo.distancia()[destino];
                mejorCamino = alternativo.caminoHasta(destino);
            }
        }
        return new Resultado(mejorDistancia, mejorCamino);
    }

    /**
     * Copia del grafo sin UNA ocurrencia de la arista u-v (en ambos
     * sentidos, porque el grafo es no dirigido).
     */
    private static Grafo sinLaArista(Grafo grafo, int u, int v) {
        Grafo copia = new Grafo(grafo.cantidadDeVertices(), false);
        boolean saltada = false;                   // por si hubiera aristas paralelas
        for (int vertice = 0; vertice < grafo.cantidadDeVertices(); vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (vertice < arista.destino()) {  // cada arista una sola vez
                    boolean esLaQuitada = !saltada
                            && ((vertice == u && arista.destino() == v)
                             || (vertice == v && arista.destino() == u));
                    if (esLaQuitada) {
                        saltada = true;            // esta no se copia
                    } else {
                        copia.agregarArista(vertice, arista.destino(), arista.peso());
                    }
                }
            }
        }
        return copia;
    }

    /** La red de ciudades de la unidad (distancias aproximadas en km). */
    public static Grafo redDeCiudades() {
        // 0=Buenos Aires, 1=Rosario, 2=Cordoba, 3=Mendoza, 4=Neuquen,
        // 5=Bariloche, 6=Mar del Plata
        Grafo red = new Grafo(7, false);
        red.agregarArista(0, 1, 300);    // BA - Rosario
        red.agregarArista(0, 6, 400);    // BA - Mar del Plata
        red.agregarArista(1, 2, 400);    // Rosario - Cordoba
        red.agregarArista(2, 3, 600);    // Cordoba - Mendoza
        red.agregarArista(0, 4, 1150);   // BA - Neuquen
        red.agregarArista(3, 4, 800);    // Mendoza - Neuquen
        red.agregarArista(4, 5, 430);    // Neuquen - Bariloche
        return red;
    }

    public static void main(String[] args) {
        Grafo red = redDeCiudades();
        Dijkstra.Resultado optimo = Dijkstra.caminosMinimos(red, 0);
        System.out.println("Optimo BA->Bariloche: " + optimo.distancia()[5]
                + " km, camino " + optimo.caminoHasta(5));

        Resultado segunda = calcular(red, 0, 5);
        System.out.println("Segunda mejor: " + segunda.distancia()
                + " km, camino " + segunda.camino());
    }
}
