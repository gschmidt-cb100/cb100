package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * TECNICA: ORDEN TOPOLOGICO POR DFS (la alternativa al algoritmo de Kahn).
 *
 * Idea clave: en un DFS, un vertice TERMINA su exploracion recien cuando ya
 * terminaron TODOS los vertices alcanzables desde el. Entonces, si apilamos
 * cada vertice al terminar y al final leemos la pila al reves (del ultimo
 * en terminar al primero), toda arista u-&gt;v deja a u antes que v: u no
 * puede terminar hasta que termine v, asi que u queda arriba en la pila.
 *
 * Deteccion de ciclos con TRES marcas (los "colores" clasicos):
 * <ul>
 *   <li>BLANCO: todavia no lo visitamos;</li>
 *   <li>GRIS:   lo estamos explorando (esta en la pila de recursion);</li>
 *   <li>NEGRO:  ya termino su exploracion.</li>
 * </ul>
 * Si desde un gris sale una arista hacia OTRO gris, encontramos un camino
 * que vuelve a un vertice aun abierto: eso es exactamente un ciclo.
 * Una arista hacia un negro, en cambio, es inofensiva (ese ya cerro).
 *
 * Mismo costo que Kahn, O(n + m), y el mismo contrato: puede devolver OTRO
 * orden valido (en general hay varios), por eso el test valida la PROPIEDAD
 * (toda arista respetada) y no la igualdad con {@code OrdenTopologico.ordenar}.
 */
public final class OrdenTopologicoPorDfs {

    private OrdenTopologicoPorDfs() {}

    private enum Marca { BLANCO, GRIS, NEGRO }

    /**
     * Un orden topologico del grafo, calculado con DFS.
     *
     * @throws IllegalArgumentException si el grafo no es dirigido
     * @throws IllegalStateException    si el grafo tiene un ciclo
     */
    public static List<Integer> ordenar(Grafo grafo) {
        if (!grafo.esDirigido()) {
            throw new IllegalArgumentException("el orden topologico requiere un grafo dirigido");
        }
        int n = grafo.cantidadDeVertices();
        Marca[] marca = new Marca[n];
        java.util.Arrays.fill(marca, Marca.BLANCO);
        Deque<Integer> terminados = new ArrayDeque<>();    // pila de "ya cerro"

        // El grafo puede no ser conexo: lanzamos un DFS desde cada blanco.
        for (int vertice = 0; vertice < n; vertice++) {
            if (marca[vertice] == Marca.BLANCO) {
                explorar(grafo, vertice, marca, terminados);
            }
        }

        // Leer la pila al reves = desapilar: el ultimo en terminar sale primero.
        List<Integer> orden = new ArrayList<>(n);
        while (!terminados.isEmpty()) {
            orden.add(terminados.pop());
        }
        return orden;
    }

    private static void explorar(Grafo grafo, int vertice, Marca[] marca,
                                 Deque<Integer> terminados) {
        marca[vertice] = Marca.GRIS;                       // entra a la exploracion
        for (Grafo.Arista arista : grafo.vecinos(vertice)) {
            if (marca[arista.destino()] == Marca.GRIS) {
                // Arista hacia un vertice aun abierto: cerramos un circuito.
                throw new IllegalStateException(
                        "el grafo tiene un ciclo (arista " + vertice + "->" + arista.destino() + ")");
            }
            if (marca[arista.destino()] == Marca.BLANCO) {
                explorar(grafo, arista.destino(), marca, terminados);
            }
            // NEGRO: ya cerro por otro camino, no hay nada que hacer.
        }
        marca[vertice] = Marca.NEGRO;                      // termino su exploracion...
        terminados.push(vertice);                          // ...y RECIEN AHORA se apila
    }

    /** ¿El grafo dirigido tiene algun ciclo? (la misma pregunta, sin excepcion) */
    public static boolean tieneCiclo(Grafo grafo) {
        try {
            ordenar(grafo);
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        // Correlatividades: 0=Algebra, 1=Analisis, 2=Fisica I, 3=Prog. I,
        //                   4=CB100,   5=Fisica II
        Grafo plan = new Grafo(6, true);
        plan.agregarArista(1, 2);   // Analisis -> Fisica I
        plan.agregarArista(3, 4);   // Prog. I  -> CB100
        plan.agregarArista(2, 5);   // Fisica I -> Fisica II
        plan.agregarArista(0, 5);   // Algebra  -> Fisica II

        System.out.println("Orden por DFS: " + ordenar(plan));

        Grafo ciclo = new Grafo(3, true);
        ciclo.agregarArista(0, 1);
        ciclo.agregarArista(1, 2);
        ciclo.agregarArista(2, 0);
        System.out.println("¿A->B->C->A tiene ciclo? " + tieneCiclo(ciclo));   // true
    }
}
