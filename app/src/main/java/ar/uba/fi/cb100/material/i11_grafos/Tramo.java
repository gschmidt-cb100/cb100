package ar.uba.fi.cb100.material.i11_grafos;

import java.util.List;

/**
 * Una arista "completa" (con sus dos extremos y el peso). Es lo que devuelven
 * Prim y Kruskal: los tramos que forman el árbol de tendido mínimo.
 */
public record Tramo(int origen, int destino, int peso) {

    /** La suma de los pesos de una lista de tramos. */
    public static int costoTotal(List<Tramo> tramos) {
        int total = 0;
        for (Tramo tramo : tramos) {
            total += tramo.peso();
        }
        return total;
    }
}
