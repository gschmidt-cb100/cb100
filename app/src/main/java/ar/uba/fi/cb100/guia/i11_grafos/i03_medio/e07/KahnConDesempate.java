package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e07;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e07: orden topológico con desempate — Kahn con cola de prioridad.
 *
 * <p><b>Modelo:</b> un DAG suele admitir MUCHOS órdenes topológicos válidos:
 * cuando hay varios vértices "listos" (grado de entrada 0), cualquiera puede
 * salir. El Kahn del apunte usa una cola común y el resultado depende del
 * orden de llegada; acá agregamos una regla de desempate: entre los listos
 * sale SIEMPRE el de menor número.</p>
 *
 * <p><b>Técnica:</b> el mismo algoritmo de Kahn (U11), pero cambiando la
 * {@code Deque} por una {@code PriorityQueue} (U9), que entrega el mínimo en
 * O(log n). El resultado queda único y predecible (el orden topológico
 * lexicográficamente menor). Costo: O((n + m) log n).</p>
 */
public final class KahnConDesempate {

    private KahnConDesempate() {
    }

    /**
     * El orden topológico que, ante el empate entre varios listos, elige
     * siempre el vértice de menor número.
     *
     * @throws IllegalArgumentException si el grafo no es dirigido
     * @throws IllegalStateException    si el grafo tiene un ciclo
     */
    public static List<Integer> ordenar(Grafo grafo) {
        if (!grafo.esDirigido()) {
            throw new IllegalArgumentException(
                    "el orden topológico requiere un grafo dirigido");
        }
        int n = grafo.cantidadDeVertices();

        int[] gradoDeEntrada = new int[n];
        for (int vertice = 0; vertice < n; vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                gradoDeEntrada[arista.destino()]++;
            }
        }

        // La ÚNICA diferencia con Kahn: un heap en lugar de una cola FIFO.
        PriorityQueue<Integer> listos = new PriorityQueue<>();
        for (int vertice = 0; vertice < n; vertice++) {
            if (gradoDeEntrada[vertice] == 0) {
                listos.add(vertice);
            }
        }

        List<Integer> orden = new ArrayList<>();
        while (!listos.isEmpty()) {
            int vertice = listos.poll();            // el MENOR de los listos
            orden.add(vertice);
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                gradoDeEntrada[arista.destino()]--;
                if (gradoDeEntrada[arista.destino()] == 0) {
                    listos.add(arista.destino());
                }
            }
        }

        if (orden.size() < n) {
            throw new IllegalStateException(
                    "el grafo tiene un ciclo: no hay orden posible");
        }
        return orden;
    }

    public static void main(String[] args) {
        // DAG con varios órdenes válidos: 0->2, 1->2, 2->3, 4->3, 1->5.
        Grafo dag = new Grafo(6, true);
        dag.agregarArista(0, 2);
        dag.agregarArista(1, 2);
        dag.agregarArista(2, 3);
        dag.agregarArista(4, 3);
        dag.agregarArista(1, 5);

        System.out.println(ordenar(dag));   // [0, 1, 2, 4, 3, 5]: único y reproducible
    }
}
