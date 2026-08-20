package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.Kruskal;
import ar.uba.fi.cb100.material.i11_grafos.Prim;
import ar.uba.fi.cb100.material.i11_grafos.Tramo;

import java.util.List;

/**
 * MODELO: tender fibra optica entre 6 nodos al menor costo total.
 *
 * Cada nodo es un vertice y cada tendido posible una arista con su costo.
 * El ARBOL DE TENDIDO MINIMO (MST) elige las aristas que conectan TODO al
 * menor costo total: siempre son exactamente n-1 tramos, porque un tramo
 * mas formaria un ciclo (cable de mas) y uno menos dejaria algo suelto.
 *
 * Kruskal y Prim son dos golosos distintos (uno ordena aristas, el otro
 * hace crecer el arbol), pero llegan al MISMO costo minimo: comparar los
 * dos resultados es un chequeo cruzado gratis.
 */
public final class CostoDelTendido {

    private CostoDelTendido() {}

    /** La red de fibra de la unidad: A=0, B=1, C=2, D=3, E=4, F=5. */
    public static Grafo redDeFibra() {
        Grafo red = new Grafo(6, false);
        red.agregarArista(0, 1, 2);   // A-B 2
        red.agregarArista(1, 2, 1);   // B-C 1
        red.agregarArista(0, 2, 3);   // A-C 3
        red.agregarArista(1, 3, 4);   // B-D 4
        red.agregarArista(2, 4, 6);   // C-E 6
        red.agregarArista(3, 4, 5);   // D-E 5
        red.agregarArista(4, 5, 3);   // E-F 3
        red.agregarArista(3, 5, 7);   // D-F 7
        return red;
    }

    public static void main(String[] args) {
        Grafo red = redDeFibra();

        List<Tramo> porKruskal = Kruskal.arbolDeTendidoMinimo(red);
        List<Tramo> porPrim = Prim.arbolDeTendidoMinimo(red, 0);

        System.out.println("Kruskal: " + porKruskal);
        System.out.println("  costo " + Tramo.costoTotal(porKruskal)
                + " con " + porKruskal.size() + " tramos");          // costo 15, 5 tramos
        System.out.println("Prim:    costo " + Tramo.costoTotal(porPrim)
                + " con " + porPrim.size() + " tramos");             // el mismo costo 15
    }
}
