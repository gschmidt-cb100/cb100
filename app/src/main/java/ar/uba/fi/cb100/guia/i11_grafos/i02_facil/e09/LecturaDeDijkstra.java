package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e09;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.Arrays;

/**
 * TECNICA: leer el resultado de Dijkstra.
 *
 * {@code Dijkstra.caminosMinimos(g, origen)} devuelve DOS arreglos:
 * {@code distancia[v]} = costo del camino mas barato origen&rarr;v, y
 * {@code anterior[v]} = desde donde se llego a v en ese camino. Con
 * {@code anterior} se reconstruye la ruta completa caminando hacia atras,
 * que es lo que hace {@code caminoHasta(destino)}.
 *
 * Sobre el mapa de rutas de la unidad (A=0 ... F=5), desde A el camino a
 * E no es el directo A-F-E (14 + 9 = 23) sino A-C-F-E (9 + 2 + 9 = 20):
 * Dijkstra encuentra el desvio barato que a simple vista no se ve.
 */
public final class LecturaDeDijkstra {

    private LecturaDeDijkstra() {}

    /** El mapa de rutas de la unidad: A=0, B=1, C=2, D=3, E=4, F=5. */
    public static Grafo mapaDeRutas() {
        Grafo mapa = new Grafo(6, false);
        mapa.agregarArista(0, 1, 7);    // A-B  7
        mapa.agregarArista(0, 2, 9);    // A-C  9
        mapa.agregarArista(0, 5, 14);   // A-F 14
        mapa.agregarArista(1, 2, 10);   // B-C 10
        mapa.agregarArista(1, 3, 15);   // B-D 15
        mapa.agregarArista(2, 3, 11);   // C-D 11
        mapa.agregarArista(2, 5, 2);    // C-F  2
        mapa.agregarArista(3, 4, 6);    // D-E  6
        mapa.agregarArista(4, 5, 9);    // E-F  9
        return mapa;
    }

    public static void main(String[] args) {
        Dijkstra.Resultado resultado = Dijkstra.caminosMinimos(mapaDeRutas(), 0);
        System.out.println("Distancias desde A: "
                + Arrays.toString(resultado.distancia()));      // [0, 7, 9, 20, 20, 11]
        System.out.println("Camino minimo a E:  "
                + resultado.caminoHasta(4));                    // [0, 2, 5, 4] = A-C-F-E
        System.out.println("Camino minimo a D:  "
                + resultado.caminoHasta(3));                    // [0, 2, 3]    = A-C-D
    }
}
