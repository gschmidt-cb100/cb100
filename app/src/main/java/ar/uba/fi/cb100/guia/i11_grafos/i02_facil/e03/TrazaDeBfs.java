package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e03;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;

import java.util.List;

/**
 * TECNICA: trazar BFS a mano y despues verificarlo por codigo.
 *
 * Grafo de la unidad (A=0, B=1, C=2, D=3, E=4, F=5, no dirigido), con las
 * aristas agregadas en este orden: A-B, A-C, B-D, C-D, C-E, D-F, E-F.
 * Los vecinos de cada vertice quedan en ese mismo orden de insercion.
 *
 * TRAZA de BFS desde C (recordar: se marca visitado al ENCOLAR):
 * <pre>
 * paso | atiendo | vecinos          | encolo      | cola despues
 * -----+---------+------------------+-------------+--------------
 *   1  |   C     | A, D, E          | A, D, E     | [A, D, E]
 *   2  |   A     | B, C             | B           | [D, E, B]
 *   3  |   D     | B, C, F          | F           | [E, B, F]
 *   4  |   E     | C, F             | (nada)      | [B, F]
 *   5  |   B     | A, D             | (nada)      | [F]
 *   6  |   F     | D, E             | (nada)      | []
 * </pre>
 * Orden final: C, A, D, E, B, F = [2, 0, 3, 4, 1, 5]. Notar los NIVELES:
 * C (distancia 0), A/D/E (distancia 1), B/F (distancia 2) — BFS siempre
 * agota un nivel antes de pasar al siguiente.
 */
public final class TrazaDeBfs {

    private TrazaDeBfs() {}

    /** El orden que dio la traza a mano, para comparar contra el codigo. */
    public static final List<Integer> ORDEN_A_MANO = List.of(2, 0, 3, 4, 1, 5);

    /** Arma el grafo A-F de la unidad, con las aristas en el orden del apunte. */
    public static Grafo grafoDelApunte() {
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F
        return grafo;
    }

    public static void main(String[] args) {
        List<Integer> porCodigo = Recorridos.bfs(grafoDelApunte(), 2);   // desde C
        System.out.println("A mano:     " + ORDEN_A_MANO);
        System.out.println("Por codigo: " + porCodigo);
        System.out.println("Coinciden?  " + ORDEN_A_MANO.equals(porCodigo));   // true
    }
}
