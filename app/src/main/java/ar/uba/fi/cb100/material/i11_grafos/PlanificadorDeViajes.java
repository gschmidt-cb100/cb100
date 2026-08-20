package ar.uba.fi.cb100.material.i11_grafos;

import java.util.List;
import java.util.StringJoiner;

/**
 * <b>Ejemplo integrador de la Unidad 11</b>: una red de rutas entre ciudades,
 * y tres preguntas distintas sobre el MISMO grafo:
 * <ol>
 *   <li>¿Con cuántas escalas llego? → <b>BFS</b> (menos aristas)</li>
 *   <li>¿Cuál es el viaje más corto en km? → <b>Dijkstra</b> (menos peso)</li>
 *   <li>¿Cómo tiendo fibra óptica entre todas al menor costo? → <b>Kruskal</b> (MST)</li>
 * </ol>
 * La moraleja: "camino mínimo" no significa nada hasta decir QUÉ se minimiza.
 */
public final class PlanificadorDeViajes {

    private final Grafo rutas;
    private final String[] nombres;

    public PlanificadorDeViajes(String[] nombres) {
        this.nombres = nombres.clone();
        this.rutas = new Grafo(nombres.length, false);   // las rutas van y vienen
    }

    public void agregarRuta(int unaCiudad, int otraCiudad, int kilometros) {
        rutas.agregarArista(unaCiudad, otraCiudad, kilometros);
    }

    /** Cantidad de escalas (ciudades intermedias) del viaje con MENOS TRAMOS. */
    public int escalasMinimas(int origen, int destino) {
        int tramos = Recorridos.distanciasDesde(rutas, origen)[destino];
        if (tramos == -1) {
            throw new IllegalArgumentException("no hay ruta entre esas ciudades");
        }
        return Math.max(0, tramos - 1);                  // n tramos = n−1 escalas
    }

    /** El viaje más corto EN KILÓMETROS, como texto legible. */
    public String viajeMasCorto(int origen, int destino) {
        Dijkstra.Resultado resultado = Dijkstra.caminosMinimos(rutas, origen);
        List<Integer> camino = resultado.caminoHasta(destino);
        if (camino.isEmpty()) {
            throw new IllegalArgumentException("no hay ruta entre esas ciudades");
        }
        StringJoiner texto = new StringJoiner(" -> ");
        for (int ciudad : camino) {
            texto.add(nombres[ciudad]);
        }
        return texto + " (" + resultado.distancia()[destino] + " km)";
    }

    /** El tendido de fibra que conecta TODAS las ciudades con el mínimo de cable. */
    public String tendidoDeFibraMinimo() {
        List<Tramo> arbol = Kruskal.arbolDeTendidoMinimo(rutas);
        StringJoiner texto = new StringJoiner(", ");
        for (Tramo tramo : arbol) {
            texto.add(nombres[tramo.origen()] + "-" + nombres[tramo.destino()]);
        }
        return texto + " (" + Tramo.costoTotal(arbol) + " km de cable)";
    }

    public static void main(String[] args) {
        String[] ciudades = {"Buenos Aires", "Rosario", "Córdoba", "Mendoza",
                             "Neuquén", "Bariloche", "Mar del Plata"};
        PlanificadorDeViajes planificador = new PlanificadorDeViajes(ciudades);
        planificador.agregarRuta(0, 1, 300);    // Buenos Aires - Rosario
        planificador.agregarRuta(0, 6, 400);    // Buenos Aires - Mar del Plata
        planificador.agregarRuta(1, 2, 400);    // Rosario - Córdoba
        planificador.agregarRuta(2, 3, 600);    // Córdoba - Mendoza
        planificador.agregarRuta(0, 4, 1150);   // Buenos Aires - Neuquén
        planificador.agregarRuta(3, 4, 800);    // Mendoza - Neuquén
        planificador.agregarRuta(4, 5, 430);    // Neuquén - Bariloche

        // La MISMA pregunta, dos métricas, dos respuestas distintas:
        System.out.println(planificador.escalasMinimas(0, 3));   // 1 (vía Neuquén: 2 tramos)
        System.out.println(planificador.viajeMasCorto(0, 3));
        // Buenos Aires -> Rosario -> Córdoba -> Mendoza (1300 km): más tramos, menos km

        System.out.println(planificador.viajeMasCorto(0, 5));
        // Buenos Aires -> Neuquén -> Bariloche (1580 km)

        System.out.println(planificador.tendidoDeFibraMinimo());
        // Buenos Aires-Rosario, Buenos Aires-Mar del Plata, Rosario-Córdoba,
        // Neuquén-Bariloche, Córdoba-Mendoza, Mendoza-Neuquén (2930 km de cable)
    }
}
