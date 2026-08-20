package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DetectorDeCiclosTest {

    @Test
    @DisplayName("Un árbol no tiene ciclos: la arista al padre no cuenta")
    void arbolSinCiclos() {
        Grafo arbol = new Grafo(5, false);
        arbol.agregarArista(0, 1);
        arbol.agregarArista(0, 2);
        arbol.agregarArista(1, 3);
        arbol.agregarArista(1, 4);

        assertFalse(DetectorDeCiclos.tieneCiclo(arbol));
    }

    @Test
    @DisplayName("El grafo del apunte (A-F) tiene ciclos")
    void grafoDelApunteConCiclos() {
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F

        assertTrue(DetectorDeCiclos.tieneCiclo(grafo));   // por ejemplo A-B-D-C-A
    }

    @Test
    @DisplayName("Detecta el ciclo aunque esté en otra componente")
    void cicloEnOtraComponente() {
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);      // componente árbol: sin ciclo
        grafo.agregarArista(3, 4);      // componente triángulo: con ciclo
        grafo.agregarArista(4, 5);
        grafo.agregarArista(5, 3);

        assertTrue(DetectorDeCiclos.tieneCiclo(grafo));
    }

    @Test
    @DisplayName("Grafo sin aristas y con vértices sueltos: sin ciclos")
    void grafoVacio() {
        assertFalse(DetectorDeCiclos.tieneCiclo(new Grafo(4, false)));
    }
}
