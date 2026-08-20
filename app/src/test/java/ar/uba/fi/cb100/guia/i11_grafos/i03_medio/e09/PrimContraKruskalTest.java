package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e09;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Prim;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimContraKruskalTest {

    @Test
    @DisplayName("En 20 grafos conexos al azar (semilla 42), Prim y Kruskal empatan siempre")
    void veinteGrafosAlAzar() {
        Random azar = new Random(42);               // semilla fija: test determinista

        for (int experimento = 0; experimento < 20; experimento++) {
            int vertices = 5 + azar.nextInt(30);
            int extras = azar.nextInt(3 * vertices);
            Grafo grafo = PrimContraKruskal.grafoConexoAlAzar(azar, vertices, extras);

            int costoKruskal = Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(grafo));
            int costoPrim = Tramo.costoTotal(Prim.arbolDeTendidoMinimo(grafo, 0));
            assertEquals(costoKruskal, costoPrim,
                    "difieren en el experimento " + experimento
                            + " (n=" + vertices + ", extras=" + extras + ")");
        }
    }

    @Test
    @DisplayName("El generador siempre produce grafos conexos con n-1 o más aristas")
    void generadorConexo() {
        Random azar = new Random(42);
        for (int experimento = 0; experimento < 10; experimento++) {
            Grafo grafo = PrimContraKruskal.grafoConexoAlAzar(azar, 8, experimento);
            // Si no fuera conexo, Kruskal lanzaría IllegalStateException.
            assertEquals(7, Kruskal.arbolDeTendidoMinimo(grafo).size());
            assertTrue(grafo.cantidadDeAristas() >= 7, "al menos el árbol generador");
        }
    }

    @Test
    @DisplayName("En la red de fibra del apunte ambos dan costo 15")
    void redDeFibraDelApunte() {
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        assertTrue(PrimContraKruskal.coinciden(red));
        assertEquals(15, Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(red)));
    }
}
