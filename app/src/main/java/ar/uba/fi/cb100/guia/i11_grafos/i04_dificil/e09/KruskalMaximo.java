package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e09;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;
import ar.uba.fi.cb100.material.i11_grafos.UnionFind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TECNICA: KRUSKAL AL REVES — arbol de tendido MAXIMO.
 *
 * A veces el objetivo es el contrario al del MST: quedarse con las
 * conexiones mas VALIOSAS que mantengan todo conectado (enlaces con mas
 * ancho de banda, rutas con mas capacidad). Es el mismo problema con el
 * orden invertido: alcanza con que Kruskal recorra las aristas de MAYOR
 * a MENOR peso; el resto (union-find para descartar ciclos, parar en n-1
 * aristas) no cambia en una sola linea.
 *
 * ¿Por que la misma prueba de correctitud sirve? Maximizar sobre pesos p
 * es minimizar sobre pesos -p, y Kruskal minimo es correcto para pesos
 * cualesquiera (incluso negativos): ordenar descendente por p ES ordenar
 * ascendente por -p.
 *
 * VERIFICACION A MANO en la red de fibra (A=0..F=5), descendente:
 * D-F 7 acepta {D,F}; C-E 6 acepta {C,E}; D-E 5 acepta {C,D,E,F};
 * B-D 4 acepta {B,C,D,E,F}; E-F 3 ciclo; A-C 3 acepta {A,...}: 5 aristas.
 * Costo maximo = 7 + 6 + 5 + 4 + 3 = 25 (el minimo era 15).
 */
public final class KruskalMaximo {

    private KruskalMaximo() {}

    /**
     * Los n-1 tramos del arbol de tendido MAXIMO.
     *
     * @throws IllegalArgumentException si el grafo es dirigido
     * @throws IllegalStateException    si el grafo no es conexo
     */
    public static List<Tramo> arbolDeTendidoMaximo(Grafo grafo) {
        if (grafo.esDirigido()) {
            throw new IllegalArgumentException("el arbol de tendido se define sobre grafos no dirigidos");
        }
        int n = grafo.cantidadDeVertices();

        // 1) Cada arista una sola vez (en no dirigidos figura en ambos extremos).
        List<Tramo> aristas = new ArrayList<>();
        for (int vertice = 0; vertice < n; vertice++) {
            for (Grafo.Arista arista : grafo.vecinos(vertice)) {
                if (vertice < arista.destino()) {
                    aristas.add(new Tramo(vertice, arista.destino(), arista.peso()));
                }
            }
        }

        // 2) EL cambio respecto de Kruskal: ordenar de MAYOR a MENOR peso.
        aristas.sort(Comparator.comparingInt(Tramo::peso).reversed());

        // 3) Aceptar cada arista que no forme ciclo, hasta juntar n-1.
        UnionFind grupos = new UnionFind(n);
        List<Tramo> arbol = new ArrayList<>();
        for (Tramo tramo : aristas) {
            if (grupos.unir(tramo.origen(), tramo.destino())) {
                arbol.add(tramo);
                if (arbol.size() == n - 1) {
                    break;
                }
            }
        }

        if (arbol.size() < Math.max(0, n - 1)) {
            throw new IllegalStateException("el grafo no es conexo: no se puede tender un solo arbol");
        }
        return arbol;
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

        List<Tramo> maximo = arbolDeTendidoMaximo(red);
        System.out.println(maximo);
        System.out.println("Costo maximo: " + Tramo.costoTotal(maximo));   // 25
    }
}
