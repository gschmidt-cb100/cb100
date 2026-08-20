package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.OrdenTopologico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class OrdenTopologicoPorDfsTest {

    /**
     * Validador generico: un orden es topologico si contiene cada vertice
     * exactamente una vez y toda arista u->v deja a u ANTES que v.
     */
    private static boolean esOrdenTopologicoValido(Grafo grafo, List<Integer> orden) {
        int n = grafo.cantidadDeVertices();
        if (orden.size() != n) {
            return false;
        }
        int[] posicion = new int[n];
        java.util.Arrays.fill(posicion, -1);
        for (int i = 0; i < orden.size(); i++) {
            if (posicion[orden.get(i)] != -1) {
                return false;                              // vertice repetido
            }
            posicion[orden.get(i)] = i;
        }
        for (int origen = 0; origen < n; origen++) {
            for (Grafo.Arista arista : grafo.vecinos(origen)) {
                if (posicion[origen] >= posicion[arista.destino()]) {
                    return false;                          // arista violada
                }
            }
        }
        return true;
    }

    private static Grafo planDeCorrelatividades() {
        Grafo plan = new Grafo(6, true);
        plan.agregarArista(1, 2);
        plan.agregarArista(3, 4);
        plan.agregarArista(2, 5);
        plan.agregarArista(0, 5);
        return plan;
    }

    @Test
    @DisplayName("Sobre un DAG el orden por DFS respeta todas las aristas")
    void ordenValidoEnDag() {
        Grafo plan = planDeCorrelatividades();
        assertTrue(esOrdenTopologicoValido(plan, OrdenTopologicoPorDfs.ordenar(plan)));
    }

    @Test
    @DisplayName("DFS y Kahn dan ordenes VALIDOS, no necesariamente iguales")
    void comparacionConKahn() {
        Grafo plan = planDeCorrelatividades();
        // No exigimos igualdad: un DAG suele tener varios ordenes correctos.
        // Ambos algoritmos deben pasar el MISMO validador de la propiedad.
        assertTrue(esOrdenTopologicoValido(plan, OrdenTopologicoPorDfs.ordenar(plan)));
        assertTrue(esOrdenTopologicoValido(plan, OrdenTopologico.ordenar(plan)));
    }

    @Test
    @DisplayName("En DAGs aleatorios el orden siempre es valido")
    void dagsAleatorios() {
        Random azar = new Random(31);
        for (int intento = 0; intento < 20; intento++) {
            int n = 2 + azar.nextInt(15);
            Grafo dag = new Grafo(n, true);
            // Truco para garantizar DAG: solo aristas de menor a mayor numero.
            for (int origen = 0; origen < n; origen++) {
                for (int destino = origen + 1; destino < n; destino++) {
                    if (azar.nextInt(3) == 0) {
                        dag.agregarArista(origen, destino);
                    }
                }
            }
            assertTrue(esOrdenTopologicoValido(dag, OrdenTopologicoPorDfs.ordenar(dag)),
                    "orden invalido en el intento " + intento);
        }
    }

    @Test
    @DisplayName("Con un ciclo lanza IllegalStateException (arista hacia un gris)")
    void detectaCiclo() {
        Grafo ciclo = new Grafo(4, true);
        ciclo.agregarArista(0, 1);
        ciclo.agregarArista(1, 2);
        ciclo.agregarArista(2, 3);
        ciclo.agregarArista(3, 1);      // vuelve a 1: ciclo 1->2->3->1
        assertThrows(IllegalStateException.class, () -> OrdenTopologicoPorDfs.ordenar(ciclo));
        assertTrue(OrdenTopologicoPorDfs.tieneCiclo(ciclo));
        assertTrue(OrdenTopologico.tieneCiclo(ciclo), "Kahn coincide en el diagnostico");
    }

    @Test
    @DisplayName("Un rulo (arista v->v) tambien es un ciclo")
    void rulo() {
        Grafo rulo = new Grafo(2, true);
        rulo.agregarArista(0, 0);
        assertTrue(OrdenTopologicoPorDfs.tieneCiclo(rulo));
    }

    @Test
    @DisplayName("Una arista hacia un vertice NEGRO (ya cerrado) no es ciclo")
    void diamanteSinCiclo() {
        // 0->1, 0->2, 1->3, 2->3: el DFS llega dos veces a 3, pero la
        // segunda vez 3 ya esta negro. No hay ciclo.
        Grafo diamante = new Grafo(4, true);
        diamante.agregarArista(0, 1);
        diamante.agregarArista(0, 2);
        diamante.agregarArista(1, 3);
        diamante.agregarArista(2, 3);
        assertFalse(OrdenTopologicoPorDfs.tieneCiclo(diamante));
        assertTrue(esOrdenTopologicoValido(diamante, OrdenTopologicoPorDfs.ordenar(diamante)));
    }

    @Test
    @DisplayName("Sobre un grafo no dirigido lanza IllegalArgumentException")
    void rechazaNoDirigido() {
        Grafo noDirigido = new Grafo(3, false);
        noDirigido.agregarArista(0, 1);
        assertThrows(IllegalArgumentException.class,
                () -> OrdenTopologicoPorDfs.ordenar(noDirigido));
    }
}
