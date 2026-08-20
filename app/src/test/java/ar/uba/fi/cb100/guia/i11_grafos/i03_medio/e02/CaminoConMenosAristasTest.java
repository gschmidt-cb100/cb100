package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e02;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CaminoConMenosAristasTest {

    private Grafo grafo;

    @BeforeEach
    void armarGrafoDelApunte() {
        // A=0, B=1, C=2, D=3, E=4, F=5
        grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F
    }

    @Test
    @DisplayName("El camino A->F tiene 4 vértices (3 aristas) y va de 0 a 5")
    void caminoDeAaF() {
        List<Integer> camino = CaminoConMenosAristas.caminoConMenosAristas(grafo, 0, 5);

        assertEquals(4, camino.size(), "distancia BFS de A a F = 3 aristas");
        assertEquals(0, camino.get(0), "empieza en el origen");
        assertEquals(5, camino.get(camino.size() - 1), "termina en el destino");
    }

    @Test
    @DisplayName("Cada par consecutivo del camino es una arista real del grafo")
    void pasosReales() {
        List<Integer> camino = CaminoConMenosAristas.caminoConMenosAristas(grafo, 0, 5);

        for (int paso = 0; paso + 1 < camino.size(); paso++) {
            assertTrue(grafo.existeArista(camino.get(paso), camino.get(paso + 1)),
                    "no existe la arista " + camino.get(paso) + "-" + camino.get(paso + 1));
        }
    }

    @Test
    @DisplayName("Camino de un vértice a sí mismo y camino inexistente")
    void casosBorde() {
        assertEquals(List.of(2), CaminoConMenosAristas.caminoConMenosAristas(grafo, 2, 2));

        Grafo partido = new Grafo(4, false);
        partido.agregarArista(0, 1);
        partido.agregarArista(2, 3);
        assertEquals(List.of(), CaminoConMenosAristas.caminoConMenosAristas(partido, 0, 3));
    }
}
