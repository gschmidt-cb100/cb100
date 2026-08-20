package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e06;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;

import java.util.ArrayList;
import java.util.List;

/**
 * e06: red de vuelos con escalas máximas — BFS por niveles.
 *
 * <p><b>Modelo:</b> "a lo sumo k escalas" quiere decir a lo sumo k ciudades
 * INTERMEDIAS, o sea viajes de hasta k+1 tramos (aristas). Y la cantidad
 * mínima de tramos hasta cada ciudad es exactamente lo que calcula BFS:
 * la distancia en aristas.</p>
 *
 * <p><b>Técnica:</b> reusar {@code Recorridos.distanciasDesde} (BFS) y
 * filtrar los vértices con {@code 1 <= distancia <= escalas + 1}. El origen
 * (distancia 0) no cuenta como destino, y los inalcanzables (-1) quedan
 * afuera. Costo: O(n + m).</p>
 */
public final class RedDeVuelos {

    private RedDeVuelos() {
    }

    /**
     * Los vértices alcanzables desde {@code origen} con a lo sumo
     * {@code escalas} ciudades intermedias, en orden creciente de número.
     * No incluye al origen.
     */
    public static List<Integer> destinosConHasta(Grafo grafo, int origen,
                                                 int escalas) {
        int maximoDeTramos = escalas + 1;           // k escalas = hasta k+1 vuelos
        int[] distancia = Recorridos.distanciasDesde(grafo, origen);

        List<Integer> destinos = new ArrayList<>();
        for (int vertice = 0; vertice < distancia.length; vertice++) {
            if (distancia[vertice] >= 1 && distancia[vertice] <= maximoDeTramos) {
                destinos.add(vertice);              // alcanzable y no es el origen
            }
        }
        return destinos;
    }

    public static void main(String[] args) {
        // La red de ciudades del apunte: 0=Buenos Aires, 1=Rosario, 2=Córdoba,
        // 3=Mendoza, 4=Neuquén, 5=Bariloche, 6=Mar del Plata
        Grafo red = new Grafo(7, false);
        red.agregarArista(0, 1, 300);
        red.agregarArista(0, 6, 400);
        red.agregarArista(1, 2, 400);
        red.agregarArista(2, 3, 600);
        red.agregarArista(0, 4, 1150);
        red.agregarArista(3, 4, 800);
        red.agregarArista(4, 5, 430);

        System.out.println(destinosConHasta(red, 0, 0));  // [1, 4, 6]: vuelos directos
        System.out.println(destinosConHasta(red, 0, 1));  // [1, 2, 3, 4, 5, 6]: con una escala llega a todas
    }
}
