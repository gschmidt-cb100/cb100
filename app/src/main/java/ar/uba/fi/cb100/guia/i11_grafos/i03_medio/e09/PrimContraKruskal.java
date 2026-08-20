package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e09;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Prim;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import java.util.Random;

/**
 * e09: Prim contra Kruskal — verificación experimental con grafos al azar.
 *
 * <p><b>Modelo:</b> Prim y Kruskal atacan el MISMO problema (el árbol de
 * tendido mínimo) con estrategias golosas distintas. La teoría dice que el
 * costo total del MST es único, así que ambos deben coincidir SIEMPRE.
 * En lugar de creerlo, lo verificamos empíricamente sobre muchos grafos
 * generados al azar (con semilla fija, para que el experimento sea
 * reproducible).</p>
 *
 * <p><b>Técnica de generación:</b> para garantizar que el grafo sea conexo,
 * primero armamos un árbol al azar (cada vértice nuevo se cuelga de uno
 * anterior elegido al azar: n−1 aristas, todo conectado) y después agregamos
 * aristas extra al azar. Pesos entre 1 y 100.</p>
 */
public final class PrimContraKruskal {

    private PrimContraKruskal() {
    }

    /**
     * Un grafo no dirigido conexo al azar: árbol aleatorio + aristas extra.
     *
     * @param azar     generador (con semilla fija para reproducir)
     * @param vertices cantidad de vértices (al menos 2)
     * @param extras   aristas adicionales al árbol
     */
    public static Grafo grafoConexoAlAzar(Random azar, int vertices, int extras) {
        Grafo grafo = new Grafo(vertices, false);

        // 1) El esqueleto: cada vértice nuevo se cuelga de uno ya existente.
        for (int vertice = 1; vertice < vertices; vertice++) {
            int existente = azar.nextInt(vertice);            // alguno de 0..vertice-1
            grafo.agregarArista(existente, vertice, 1 + azar.nextInt(100));
        }

        // 2) Aristas extra para que aparezcan ciclos y decisiones golosas.
        for (int i = 0; i < extras; i++) {
            int unVertice = azar.nextInt(vertices);
            int otroVertice = azar.nextInt(vertices);
            if (unVertice != otroVertice) {                   // sin lazos
                grafo.agregarArista(unVertice, otroVertice, 1 + azar.nextInt(100));
            }
        }
        return grafo;
    }

    /** ¿Prim y Kruskal dan el mismo costo total para este grafo? */
    public static boolean coinciden(Grafo grafo) {
        int costoKruskal = Tramo.costoTotal(Kruskal.arbolDeTendidoMinimo(grafo));
        int costoPrim = Tramo.costoTotal(Prim.arbolDeTendidoMinimo(grafo, 0));
        return costoKruskal == costoPrim;
    }

    public static void main(String[] args) {
        Random azar = new Random(42);                         // semilla fija: reproducible
        int acuerdos = 0;
        for (int experimento = 0; experimento < 20; experimento++) {
            int vertices = 5 + azar.nextInt(30);              // entre 5 y 34 vértices
            int extras = azar.nextInt(3 * vertices);
            Grafo grafo = grafoConexoAlAzar(azar, vertices, extras);
            if (coinciden(grafo)) {
                acuerdos++;
            }
        }
        System.out.println("Coincidieron en " + acuerdos + " de 20 grafos");  // 20 de 20
    }
}
