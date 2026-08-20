package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e01;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ComponentesConexasTest {

    @Test
    @DisplayName("Un grafo conexo tiene una sola componente")
    void unaComponente() {
        // El grafo del apunte (A-F) es conexo.
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(2, 3);
        grafo.agregarArista(2, 4);
        grafo.agregarArista(3, 5);
        grafo.agregarArista(4, 5);

        assertEquals(1, ComponentesConexas.cantidadDeComponentes(grafo));
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0},
                ComponentesConexas.componenteDe(grafo));
    }

    @Test
    @DisplayName("Dos islas separadas dan dos componentes")
    void dosComponentes() {
        Grafo grafo = new Grafo(5, false);
        grafo.agregarArista(0, 1);      // isla {0, 1}
        grafo.agregarArista(2, 3);      // isla {2, 3, 4}
        grafo.agregarArista(3, 4);

        assertEquals(2, ComponentesConexas.cantidadDeComponentes(grafo));
        assertArrayEquals(new int[] {0, 0, 1, 1, 1},
                ComponentesConexas.componenteDe(grafo));
    }

    @Test
    @DisplayName("Tres componentes: dos triángulos y un vértice aislado")
    void tresComponentes() {
        Grafo grafo = new Grafo(7, false);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        grafo.agregarArista(2, 0);
        grafo.agregarArista(3, 4);
        grafo.agregarArista(4, 5);
        grafo.agregarArista(5, 3);
        // el 6 queda solo

        assertEquals(3, ComponentesConexas.cantidadDeComponentes(grafo));
        int[] componente = ComponentesConexas.componenteDe(grafo);
        assertArrayEquals(new int[] {0, 0, 0, 1, 1, 1, 2}, componente);
        // Mismo grupo <=> conectados:
        assertEquals(componente[0], componente[2]);
        assertNotEquals(componente[0], componente[3]);
        assertNotEquals(componente[3], componente[6]);
    }
}
