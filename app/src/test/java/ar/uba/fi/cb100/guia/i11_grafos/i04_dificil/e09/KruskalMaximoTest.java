package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e09;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;
import ar.uba.fi.cb100.material.i11_grafos.UnionFind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KruskalMaximoTest {

    private Grafo red;

    @BeforeEach
    void armarRedDeFibra() {
        // A=0, B=1, C=2, D=3, E=4, F=5
        red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);
        red.agregarArista(1, 2, 1);
        red.agregarArista(0, 2, 3);
        red.agregarArista(1, 3, 4);
        red.agregarArista(2, 4, 6);
        red.agregarArista(3, 4, 5);
        red.agregarArista(4, 5, 3);
        red.agregarArista(3, 5, 7);
    }

    @Test
    @DisplayName("El arbol maximo de la red de fibra cuesta 25 (verificado a mano)")
    void costoExacto() {
        // Descendente: 7 (D-F) si, 6 (C-E) si, 5 (D-E) si, 4 (B-D) si,
        // 3 (E-F) ciclo, 3 (A-C) si -> 7+6+5+4+3 = 25.
        assertEquals(25, Tramo.costoTotal(KruskalMaximo.arbolDeTendidoMaximo(red)));
    }

    @Test
    @DisplayName("Tiene exactamente n-1 aristas")
    void cantidadDeAristas() {
        assertEquals(5, KruskalMaximo.arbolDeTendidoMaximo(red).size());
    }

    @Test
    @DisplayName("El costo del arbol maximo es mayor o igual que el del minimo")
    void maximoContraMinimo() {
        int maximo = Tramo.costoTotal(KruskalMaximo.arbolDeTendidoMaximo(red));
        int minimo = Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(red));
        assertEquals(15, minimo, "control: el MST de la unidad cuesta 15");
        assertTrue(maximo >= minimo);
    }

    @Test
    @DisplayName("El resultado es un arbol: conecta todo y no tiene ciclos")
    void esUnArbol() {
        List<Tramo> arbol = KruskalMaximo.arbolDeTendidoMaximo(red);
        UnionFind grupos = new UnionFind(red.cantidadDeVertices());
        for (Tramo tramo : arbol) {
            assertTrue(grupos.unir(tramo.origen(), tramo.destino()),
                    "una arista del resultado formaba ciclo: " + tramo);
        }
        assertEquals(1, grupos.cantidadDeGrupos(), "debe quedar todo conectado");
    }

    @Test
    @DisplayName("Toda arista del resultado existe en el grafo original")
    void aristasReales() {
        for (Tramo tramo : KruskalMaximo.arbolDeTendidoMaximo(red)) {
            assertTrue(red.existeArista(tramo.origen(), tramo.destino()));
        }
    }

    @Test
    @DisplayName("Un grafo no conexo lanza IllegalStateException")
    void noConexo() {
        Grafo partido = new Grafo(4, false);
        partido.agregarArista(0, 1, 1);
        partido.agregarArista(2, 3, 1);
        assertThrows(IllegalStateException.class,
                () -> KruskalMaximo.arbolDeTendidoMaximo(partido));
    }

    @Test
    @DisplayName("Un grafo dirigido lanza IllegalArgumentException")
    void rechazaDirigidos() {
        Grafo dirigido = new Grafo(3, true);
        dirigido.agregarArista(0, 1, 1);
        assertThrows(IllegalArgumentException.class,
                () -> KruskalMaximo.arbolDeTendidoMaximo(dirigido));
    }
}
