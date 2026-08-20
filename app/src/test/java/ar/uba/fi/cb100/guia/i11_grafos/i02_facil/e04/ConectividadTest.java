package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e04;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConectividadTest {

    private Grafo islas;   // dos componentes: {0, 1, 2} y {3, 4}

    @BeforeEach
    void armarIslas() {
        islas = new Grafo(5, false);
        islas.agregarArista(0, 1);
        islas.agregarArista(1, 2);
        islas.agregarArista(3, 4);
    }

    @Test
    @DisplayName("Dentro de la misma componente siempre hay camino")
    void dentroDeLaMismaComponente() {
        assertTrue(Conectividad.hayCamino(islas, 0, 2));   // 0-1-2
        assertTrue(Conectividad.hayCamino(islas, 2, 0));   // y la vuelta
        assertTrue(Conectividad.hayCamino(islas, 3, 4));
    }

    @Test
    @DisplayName("Entre componentes distintas no hay camino")
    void entreComponentesDistintas() {
        assertFalse(Conectividad.hayCamino(islas, 0, 3));
        assertFalse(Conectividad.hayCamino(islas, 4, 1));
    }

    @Test
    @DisplayName("Todo vertice esta conectado consigo mismo (camino de largo 0)")
    void consigoMismo() {
        assertTrue(Conectividad.hayCamino(islas, 0, 0));
        assertTrue(Conectividad.hayCamino(islas, 3, 3));
    }

    @Test
    @DisplayName("Coincide con Recorridos.hayCamino del material en todos los pares")
    void coincideConElMaterial() {
        for (int u = 0; u < islas.cantidadDeVertices(); u++) {
            for (int v = 0; v < islas.cantidadDeVertices(); v++) {
                assertEquals(Recorridos.hayCamino(islas, u, v),
                        Conectividad.hayCamino(islas, u, v),
                        "difieren en el par " + u + "->" + v);
            }
        }
    }
}
