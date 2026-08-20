package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Prim;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;
import ar.uba.fi.cb100.material.i11_grafos.UnionFind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CostoDelTendidoTest {

    private Grafo red;

    @BeforeEach
    void armarRed() {
        red = CostoDelTendido.redDeFibra();
    }

    @Test
    @DisplayName("Kruskal tiende la red de fibra con costo total 15")
    void costoDeKruskal() {
        assertEquals(15, Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(red)));
    }

    @Test
    @DisplayName("El arbol tiene exactamente n-1 = 5 tramos")
    void cantidadDeTramos() {
        assertEquals(red.cantidadDeVertices() - 1,
                Kruskal.arbolDeTendidoMinimo(red).size());
    }

    @Test
    @DisplayName("Prim llega al mismo costo minimo que Kruskal desde cualquier inicio")
    void primDaElMismoCosto() {
        for (int inicial = 0; inicial < red.cantidadDeVertices(); inicial++) {
            List<Tramo> arbol = Prim.arbolDeTendidoMinimo(red, inicial);
            assertEquals(15, Tramo.costoTotal(arbol), "arrancando de " + inicial);
            assertEquals(5, arbol.size());
        }
    }

    @Test
    @DisplayName("Los 5 tramos de Kruskal conectan los 6 nodos (verificado con UnionFind)")
    void elArbolConectaTodo() {
        UnionFind grupos = new UnionFind(red.cantidadDeVertices());
        for (Tramo tramo : Kruskal.arbolDeTendidoMinimo(red)) {
            assertTrue(grupos.unir(tramo.origen(), tramo.destino()),
                    "un arbol no puede repetir grupo: " + tramo);   // sin ciclos
        }
        for (int v = 1; v < red.cantidadDeVertices(); v++) {
            assertTrue(grupos.estanConectados(0, v), "el nodo " + v + " quedo suelto");
        }
    }
}
