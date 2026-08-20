package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e05;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;

/**
 * MODELO: la red de ciudades como grafo no dirigido, donde cada ciudad es
 * un vertice y cada ruta una arista.
 *
 * Para contar ESCALAS los kilometros no importan: importa la CANTIDAD DE
 * TRAMOS del viaje, y el camino con menos tramos lo da BFS (es el camino
 * minimo cuando todas las aristas "valen lo mismo"). Si el viaje usa k
 * tramos, pasa por k-1 ciudades intermedias: escalas = distancia BFS - 1.
 *
 * Bariloche a Mar del Plata: Bariloche-Neuquen-Buenos Aires-Mar del Plata,
 * 3 tramos = 2 escalas (Neuquen y Buenos Aires).
 */
public final class ContadorDeEscalas {

    private ContadorDeEscalas() {}

    public static final int BUENOS_AIRES = 0;
    public static final int ROSARIO = 1;
    public static final int CORDOBA = 2;
    public static final int MENDOZA = 3;
    public static final int NEUQUEN = 4;
    public static final int BARILOCHE = 5;
    public static final int MAR_DEL_PLATA = 6;

    /** La red de ciudades de la unidad (los pesos en km no afectan al BFS). */
    public static Grafo redDeCiudades() {
        Grafo red = new Grafo(7, false);
        red.agregarArista(BUENOS_AIRES, ROSARIO, 300);
        red.agregarArista(BUENOS_AIRES, MAR_DEL_PLATA, 400);
        red.agregarArista(ROSARIO, CORDOBA, 400);
        red.agregarArista(CORDOBA, MENDOZA, 600);
        red.agregarArista(BUENOS_AIRES, NEUQUEN, 1150);
        red.agregarArista(MENDOZA, NEUQUEN, 800);
        red.agregarArista(NEUQUEN, BARILOCHE, 430);
        return red;
    }

    /**
     * Ciudades intermedias del viaje con menos tramos entre origen y
     * destino, o -1 si no hay camino.
     */
    public static int escalas(Grafo red, int origen, int destino) {
        int tramos = Recorridos.distanciasDesde(red, origen)[destino];
        if (tramos <= 0) {
            return tramos == 0 ? 0 : -1;      // mismo lugar: 0 escalas; -1: inalcanzable
        }
        return tramos - 1;                     // k tramos pasan por k-1 ciudades intermedias
    }

    public static void main(String[] args) {
        Grafo red = redDeCiudades();
        System.out.println("Tramos Bariloche -> MdP: "
                + Recorridos.distanciasDesde(red, BARILOCHE)[MAR_DEL_PLATA]);   // 3
        System.out.println("Escalas Bariloche -> MdP: "
                + escalas(red, BARILOCHE, MAR_DEL_PLATA));                      // 2
        System.out.println("Escalas Rosario -> Mendoza: "
                + escalas(red, ROSARIO, MENDOZA));                              // 1 (Cordoba)
    }
}
