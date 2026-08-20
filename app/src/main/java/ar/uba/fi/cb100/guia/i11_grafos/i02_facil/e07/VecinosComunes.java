package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e07;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.ArrayList;
import java.util.List;

/**
 * MODELO: "amigos en comun" = vecinos comunes de dos vertices.
 *
 * Es la interseccion de las dos listas de adyacencia: recorremos los
 * vecinos de u y nos quedamos con los que TAMBIEN son vecinos de v.
 * Como {@code existeArista(v, w)} cuesta O(grado(v)), el total es
 * O(grado(u) * grado(v)) — perfecto para grafos chicos; en la unidad de
 * hashing (U7) vimos como bajarlo usando un conjunto.
 *
 * Es la base del clasico "personas que quizas conozcas" de las redes
 * sociales: muchos amigos en comun sugieren una amistad que falta.
 */
public final class VecinosComunes {

    private VecinosComunes() {}

    /** Los vertices vecinos de u Y de v a la vez, en el orden de la lista de u. */
    public static List<Integer> vecinosComunes(Grafo grafo, int u, int v) {
        List<Integer> comunes = new ArrayList<>();
        for (Grafo.Arista arista : grafo.vecinos(u)) {
            if (grafo.existeArista(v, arista.destino())) {   // vecino de u que v tambien toca
                comunes.add(arista.destino());
            }
        }
        return comunes;
    }

    public static void main(String[] args) {
        // Red de amistades: Ana=0, Beto=1, Carla=2, Dana=3, Eva=4
        Grafo red = new Grafo(5, false);
        red.agregarArista(0, 1);   // Ana-Beto
        red.agregarArista(0, 2);   // Ana-Carla
        red.agregarArista(1, 2);   // Beto-Carla
        red.agregarArista(1, 3);   // Beto-Dana
        red.agregarArista(2, 4);   // Carla-Eva
        red.agregarArista(3, 4);   // Dana-Eva

        System.out.println(vecinosComunes(red, 0, 3));   // [1] = Beto: amigo de Ana y de Dana
        System.out.println(vecinosComunes(red, 0, 1));   // [2] = Carla
        System.out.println(vecinosComunes(red, 0, 4));   // [2] = Carla
    }
}
