package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e08;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;
import ar.uba.fi.cb100.material.i11_grafos.UnionFind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TECNICA: CAMINO MINIMAX (cuello de botella) CON LA VARIANTE DE KRUSKAL.
 *
 * Problema: entre u y v puede haber muchos caminos; de cada uno nos
 * importa su arista MAS PESADA (el "cuello de botella": el tramo de ruta
 * mas angosto, el enlace mas lento de la red, el peaje mas caro). Queremos
 * el camino que MINIMIZA ese maximo.
 *
 * En lugar de explorar caminos, reciclamos la idea de Kruskal: ordenar
 * las aristas de MENOR a MAYOR peso e irlas agregando a un union-find.
 * En el momento en que u y v quedan en el mismo grupo, la ultima arista
 * agregada es la respuesta. ¿Por que funciona?
 * <ul>
 *   <li>con las aristas de peso &lt;= p conectando u con v, existe un camino
 *       cuyo maximo es a lo sumo p;</li>
 *   <li>y como con las aristas de peso &lt; p u y v seguian separados, TODO
 *       camino entre ellos necesita alguna arista de peso &gt;= p.</li>
 * </ul>
 * O sea: p es alcanzable e inevitable a la vez -&gt; es el optimo. Es el
 * mismo argumento por el que el camino minimax siempre puede tomarse
 * DENTRO del arbol de tendido minimo.
 *
 * Costo: O(m log m) por el ordenamiento, igual que Kruskal.
 *
 * VERIFICACION A MANO en la red de fibra (A=0..F=5), A -&gt; F:
 * aristas ordenadas: B-C 1, A-B 2, A-C 3, E-F 3, B-D 4, D-E 5, C-E 6, D-F 7.
 * Agregando en orden: {B,C}; {A,B,C}; A-C no une; {E,F}; {A,B,C,D};
 * con D-E 5 se juntan {A,B,C,D} y {E,F} -&gt; A y F conectados.
 * Cuello de botella = 5 (el camino A-B-D-E-F tiene maximo 5 y no hay
 * forma de llegar a F sin una arista &gt;= 5).
 */
public final class CuelloDeBotella {

    private CuelloDeBotella() {}

    /**
     * El peso de la arista mas pesada del mejor camino (minimax) entre
     * origen y destino, en un grafo NO dirigido.
     *
     * @throws IllegalArgumentException si el grafo es dirigido
     * @throws IllegalStateException    si origen y destino no estan conectados
     */
    public static int entre(Grafo grafo, int origen, int destino) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException("el camino minimax se define sobre grafos no dirigidos");
        }
        if (origen == destino) {
            return 0;                              // sin moverse no hay cuello de botella
        }

        // 1) Juntar cada arista una sola vez y ordenarlas de menor a mayor.
        List<Tramo> aristas = new ArrayList<>();
        for (int vertice = 0; vertice < grafo.cantidadDeVertices(); vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (vertice < arista.destino()) {
                    aristas.add(new Tramo(vertice, arista.destino(), arista.peso()));
                }
            }
        }
        aristas.sort(Comparator.comparingInt(Tramo::peso));

        // 2) Agregarlas en orden hasta que origen y destino se conecten.
        UnionFind grupos = new UnionFind(grafo.cantidadDeVertices());
        for (Tramo tramo : aristas) {
            grupos.unir(tramo.origen(), tramo.destino());
            if (grupos.estanConectados(origen, destino)) {
                return tramo.peso();               // la ultima agregada es la respuesta
            }
        }
        throw new IllegalStateException(
                "no hay camino entre " + origen + " y " + destino);
    }

    public static void main(String[] args) {
        // La red de fibra de la unidad: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7

        System.out.println("Cuello de botella A->F: " + entre(red, 0, 5));   // 5
        System.out.println("Cuello de botella A->C: " + entre(red, 0, 2));   // 2 (via B)
    }
}
