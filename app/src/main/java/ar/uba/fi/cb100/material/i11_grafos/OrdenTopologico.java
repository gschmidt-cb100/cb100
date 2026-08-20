package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <b>Orden topológico</b> (algoritmo de Kahn): ordenar los vértices de un
 * grafo DIRIGIDO de modo que toda arista u→v deje a u ANTES que v.
 * <p>
 * Es exactamente el problema de las correlatividades: ¿en qué orden cursar
 * las materias para respetar todos los requisitos?
 * <p>
 * La idea: un vértice está "listo" cuando su <b>grado de entrada</b> (cuántas
 * aristas le llegan) es 0. Se lo saca, se descuentan sus aristas salientes,
 * y eso puede dejar "listos" a otros. Si al final quedaron vértices sin
 * sacar, el grafo tiene un <b>ciclo</b> y no existe orden posible. O(n + m).
 */
public final class OrdenTopologico {

    private OrdenTopologico() {}

    /**
     * Un orden topológico del grafo.
     *
     * @throws IllegalArgumentException si el grafo no es dirigido
     * @throws IllegalStateException    si el grafo tiene un ciclo
     */
    public static List<Integer> ordenar(Grafo grafo) {
        if (!grafo.esDirigido()) {
            throw new IllegalArgumentException("el orden topológico requiere un grafo dirigido");
        }
        int n = grafo.cantidadDeVertices();

        int[] gradoDeEntrada = new int[n];                 // cuántas aristas LLEGAN a cada uno
        for (int vertice = 0; vertice < n; vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                gradoDeEntrada[arista.destino()]++;
            }
        }

        Deque<Integer> listos = new ArrayDeque<>();        // los que ya no esperan a nadie
        for (int vertice = 0; vertice < n; vertice++) {
            if (gradoDeEntrada[vertice] == 0) {
                listos.addLast(vertice);
            }
        }

        List<Integer> orden = new ArrayList<>();
        while (!listos.isEmpty()) {
            int vertice = listos.removeFirst();
            orden.add(vertice);                            // "cursamos" esta materia
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                gradoDeEntrada[arista.destino()]--;        // un requisito menos
                if (gradoDeEntrada[arista.destino()] == 0) {
                    listos.addLast(arista.destino());      // quedó habilitada
                }
            }
        }

        if (orden.size() < n) {                            // alguien nunca llegó a 0...
            throw new IllegalStateException("el grafo tiene un ciclo: no hay orden posible");
        }
        return orden;
    }

    /** ¿El grafo dirigido tiene algún ciclo? (Kahn sin excepción) */
    public static boolean tieneCiclo(Grafo grafo) {
        try {
            ordenar(grafo);
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        // Correlatividades: 0=Álgebra, 1=Análisis, 2=Física I, 3=Prog. I,
        //                   4=CB100,   5=Física II
        Grafo plan = new Grafo(6, true);
        plan.agregarArista(1, 2);   // Análisis  → Física I
        plan.agregarArista(3, 4);   // Prog. I   → CB100
        plan.agregarArista(2, 5);   // Física I  → Física II
        plan.agregarArista(0, 5);   // Álgebra   → Física II

        System.out.println(ordenar(plan));   // [0, 1, 3, 2, 4, 5]
        // = Álgebra, Análisis, Prog. I, Física I, CB100, Física II

        // Un ciclo imposible: A→B→C→A
        Grafo ciclo = new Grafo(3, true);
        ciclo.agregarArista(0, 1);
        ciclo.agregarArista(1, 2);
        ciclo.agregarArista(2, 0);
        System.out.println(tieneCiclo(ciclo));   // true
    }
}
