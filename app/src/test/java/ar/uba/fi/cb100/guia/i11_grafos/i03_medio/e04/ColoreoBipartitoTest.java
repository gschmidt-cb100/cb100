package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e04;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColoreoBipartitoTest {

    /** Un ciclo de n vértices: 0-1-2-...-n-1-0. */
    private Grafo ciclo(int n) {
        Grafo grafo = new Grafo(n, false);
        for (int vertice = 0; vertice < n; vertice++) {
            grafo.agregarArista(vertice, (vertice + 1) % n);
        }
        return grafo;
    }

    @Test
    @DisplayName("Un ciclo de largo par es bipartito")
    void cicloParEsBipartito() {
        assertTrue(ColoreoBipartito.esBipartito(ciclo(4)));
        assertTrue(ColoreoBipartito.esBipartito(ciclo(6)));
    }

    @Test
    @DisplayName("Un ciclo de largo impar no es bipartito")
    void cicloImparNoEsBipartito() {
        assertFalse(ColoreoBipartito.esBipartito(ciclo(3)));
        assertFalse(ColoreoBipartito.esBipartito(ciclo(5)));
    }

    @Test
    @DisplayName("Cubre grafos no conexos: revisa todas las componentes")
    void noConexo() {
        // Componente 1: arista suelta (bipartita). Componente 2: triángulo (no).
        Grafo grafo = new Grafo(5, false);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(2, 3);
        grafo.agregarArista(3, 4);
        grafo.agregarArista(4, 2);
        assertFalse(ColoreoBipartito.esBipartito(grafo));

        // Dos componentes bipartitas -> bipartito.
        Grafo dosIslas = new Grafo(6, false);
        dosIslas.agregarArista(0, 1);
        dosIslas.agregarArista(2, 3);
        dosIslas.agregarArista(3, 4);
        dosIslas.agregarArista(4, 5);
        assertTrue(ColoreoBipartito.esBipartito(dosIslas));
    }

    @Test
    @DisplayName("Sin aristas todo grafo es bipartito")
    void sinAristas() {
        assertTrue(ColoreoBipartito.esBipartito(new Grafo(3, false)));
    }
}
