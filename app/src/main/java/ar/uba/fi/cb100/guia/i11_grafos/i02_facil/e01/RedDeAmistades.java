package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e01;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.List;

/**
 * MODELO: una red social como grafo NO dirigido.
 *
 * Cada persona es un VERTICE y cada amistad es una ARISTA (la amistad es
 * mutua, por eso el grafo no es dirigido). Como {@code Grafo} numera los
 * vertices de 0 a n-1, mantenemos aparte la tabla nombre &rarr; indice: el
 * arreglo {@code NOMBRES} da el nombre de cada indice, y {@code indiceDe}
 * hace la busqueda inversa.
 *
 * Con ese modelo, las preguntas de la vida real se traducen solas:
 * "cuantos amigos tiene X" es el GRADO de su vertice, "son amigos X e Y"
 * es EXISTE LA ARISTA, y "cuantas amistades hay" es la cantidad de aristas.
 */
public class RedDeAmistades {

    /** El indice en el arreglo ES el numero de vertice en el grafo. */
    private static final String[] NOMBRES = {"Ana", "Beto", "Carla", "Dana", "Eva"};

    private final Grafo grafo;

    /** Arma la red de amistades de la unidad. */
    public RedDeAmistades() {
        grafo = new Grafo(NOMBRES.length, false);      // no dirigido: la amistad es mutua
        conectar("Ana", "Beto");
        conectar("Ana", "Carla");
        conectar("Beto", "Carla");
        conectar("Beto", "Dana");
        conectar("Carla", "Eva");
        conectar("Dana", "Eva");
    }

    private void conectar(String una, String otra) {
        grafo.agregarArista(indiceDe(una), indiceDe(otra));
    }

    /** Traduce un nombre a su numero de vertice (busqueda lineal: n es chico). */
    public int indiceDe(String nombre) {
        for (int i = 0; i < NOMBRES.length; i++) {
            if (NOMBRES[i].equals(nombre)) {
                return i;
            }
        }
        throw new IllegalArgumentException("no conozco a " + nombre);
    }

    /** Cuantos amigos tiene una persona = el grado de su vertice. */
    public int cantidadDeAmigos(String nombre) {
        return grafo.grado(indiceDe(nombre));
    }

    /** Son amigos directos = existe la arista entre sus vertices. */
    public boolean sonAmigos(String una, String otra) {
        return grafo.existeArista(indiceDe(una), indiceDe(otra));
    }

    /** Cuantas amistades hay en total = cantidad de aristas del grafo. */
    public int cantidadDeAmistades() {
        return grafo.cantidadDeAristas();
    }

    /** Los nombres de todas las personas de la red, en orden de indice. */
    public List<String> personas() {
        return List.of(NOMBRES);
    }

    public static void main(String[] args) {
        RedDeAmistades red = new RedDeAmistades();
        for (String persona : red.personas()) {
            System.out.println(persona + " tiene " + red.cantidadDeAmigos(persona) + " amigos");
        }
        System.out.println("Ana y Dana son amigas? " + red.sonAmigos("Ana", "Dana"));  // false
        System.out.println("Amistades en total: " + red.cantidadDeAmistades());        // 6
    }
}
