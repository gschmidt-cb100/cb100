package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e02;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.Arrays;

/**
 * MODELO: en un grafo DIRIGIDO cada vertice tiene dos grados distintos.
 *
 * El grado de SALIDA (cuantas aristas salen de v) lo da directo
 * {@code grafo.grado(v)}, porque la lista de adyacencia guarda las aristas
 * salientes. El grado de ENTRADA (cuantas aristas LLEGAN a v) no esta a
 * mano: hay que recorrer TODAS las aristas del grafo y contar, para cada
 * destino, cuantas veces aparece. Un solo barrido: O(n + m).
 *
 * En la red de seguidores, el grado de entrada es "cuantos seguidores
 * tengo" y el de salida "a cuantos sigo".
 */
public final class GradosDeEntrada {

    private GradosDeEntrada() {}

    /**
     * El grado de entrada de cada vertice de un grafo dirigido.
     * La celda v del resultado dice cuantas aristas llegan a v.
     */
    public static int[] gradosDeEntrada(Grafo grafo) {
        if (!grafo.esDirigido()) {
            throw new IllegalArgumentException("en un no dirigido alcanza con grado(v)");
        }
        int[] entradas = new int[grafo.cantidadDeVertices()];
        for (int vertice = 0; vertice < grafo.cantidadDeVertices(); vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                entradas[arista.destino()]++;          // una arista mas que llega al destino
            }
        }
        return entradas;
    }

    public static void main(String[] args) {
        // Red de seguidores: P=0, Q=1, R=2, S=3 ("P sigue a Q" = arista P->Q)
        Grafo seguidores = new Grafo(4, true);
        seguidores.agregarArista(0, 1);   // P -> Q
        seguidores.agregarArista(1, 2);   // Q -> R
        seguidores.agregarArista(2, 0);   // R -> P
        seguidores.agregarArista(1, 3);   // Q -> S
        seguidores.agregarArista(2, 3);   // R -> S

        System.out.println(Arrays.toString(gradosDeEntrada(seguidores)));  // [1, 1, 1, 2]
        // S es el mas seguido (2 seguidores) y no sigue a nadie: grado de salida 0.
    }
}
