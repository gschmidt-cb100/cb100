package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e04;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Recorridos;

/**
 * TECNICA: reusar BFS para responder "estan conectados?".
 *
 * {@code Recorridos.distanciasDesde(g, u)} ya calcula la distancia en
 * aristas desde u hasta TODOS los vertices, con la convencion -1 =
 * inalcanzable. Entonces "hay camino de u a v" es simplemente mirar si la
 * celda v quedo distinta de -1: no hace falta escribir otro recorrido.
 *
 * Moraleja de diseno: muchas preguntas sobre grafos se contestan
 * componiendo un recorrido ya hecho + una lectura del resultado.
 */
public final class Conectividad {

    private Conectividad() {}

    /** Hay camino de u a v? (implementacion propia sobre distanciasDesde) */
    public static boolean hayCamino(Grafo grafo, int u, int v) {
        int[] distancias = Recorridos.distanciasDesde(grafo, u);
        return distancias[v] != -1;                    // -1 = BFS nunca llego a v
    }

    public static void main(String[] args) {
        // Dos islas: {0, 1, 2} conectados entre si, y {3, 4} aparte.
        Grafo islas = new Grafo(5, false);
        islas.agregarArista(0, 1);
        islas.agregarArista(1, 2);
        islas.agregarArista(3, 4);

        System.out.println(hayCamino(islas, 0, 2));   // true  (misma isla)
        System.out.println(hayCamino(islas, 0, 4));   // false (islas distintas)
        System.out.println(hayCamino(islas, 4, 3));   // true  (misma isla)
    }
}
