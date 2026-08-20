package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e08;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.OrdenTopologico;

import java.util.List;

/**
 * MODELO: un plan de correlatividades como grafo DIRIGIDO SIN CICLOS.
 *
 * Cada materia es un vertice y la arista u &rarr; v dice "u es correlativa
 * de v": hay que aprobar u ANTES de cursar v. Que no haya ciclos no es un
 * detalle: si A pidiera B y B pidiera A, nadie podria cursar nunca.
 *
 * El ORDEN TOPOLOGICO ({@code OrdenTopologico.ordenar}, algoritmo de Kahn)
 * devuelve un orden de cursada que respeta TODAS las flechas a la vez.
 * Puede haber varios ordenes validos; lo unico exigible es que toda arista
 * u&rarr;v deje a u antes que v — y eso es exactamente lo que testeamos.
 */
public class PlanDeCorrelativas {

    /** El indice en el arreglo ES el numero de vertice en el grafo. */
    public static final String[] MATERIAS = {
            "Analisis I",     // 0
            "Algebra",        // 1
            "Fisica I",       // 2
            "Analisis II",    // 3
            "Fisica II",      // 4
            "Estabilidad I",  // 5
    };

    private final Grafo plan;

    /** Arma un plan verosimil de primer tramo de Ingenieria. */
    public PlanDeCorrelativas() {
        plan = new Grafo(MATERIAS.length, true);   // dirigido: la exigencia tiene sentido unico
        plan.agregarArista(0, 3);   // Analisis I  -> Analisis II
        plan.agregarArista(1, 3);   // Algebra     -> Analisis II
        plan.agregarArista(0, 2);   // Analisis I  -> Fisica I
        plan.agregarArista(2, 4);   // Fisica I    -> Fisica II
        plan.agregarArista(3, 4);   // Analisis II -> Fisica II
        plan.agregarArista(4, 5);   // Fisica II   -> Estabilidad I
    }

    public Grafo grafo() {
        return plan;
    }

    /** Un orden de cursada valido (indices de materias). */
    public List<Integer> ordenDeCursada() {
        return OrdenTopologico.ordenar(plan);
    }

    public static void main(String[] args) {
        PlanDeCorrelativas correlativas = new PlanDeCorrelativas();
        System.out.println("Orden de cursada:");
        int cuatrimestre = 1;
        for (int materia : correlativas.ordenDeCursada()) {
            System.out.println("  " + cuatrimestre++ + ". " + MATERIAS[materia]);
        }
        // Un orden posible: Analisis I, Algebra, Fisica I, Analisis II,
        // Fisica II, Estabilidad I.
    }
}
