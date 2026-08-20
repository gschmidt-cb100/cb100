package ar.uba.fi.cb100.material.i11_grafos;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Grafo con lista de adyacencia</b>: la representación más usada en la
 * práctica. Cada vértice (numerado de 0 a n−1) guarda la lista de sus
 * aristas salientes.
 * <p>
 * Sirve para grafos <b>dirigidos</b> (la arista va en un solo sentido) y
 * <b>no dirigidos</b> (se guarda en ambos sentidos), con o sin peso
 * (sin peso, usamos peso 1).
 * <p>
 * Costos con n vértices y m aristas:
 * <ul>
 *   <li>memoria: O(n + m) — sólo las aristas que existen</li>
 *   <li>recorrer los vecinos de v: O(grado(v))</li>
 *   <li>¿existe la arista u→v?: O(grado(u)) — el punto débil frente a la matriz</li>
 * </ul>
 */
public class Grafo {

    /** Una arista saliente: hacia qué vértice va y con qué peso. */
    public record Arista(int destino, int peso) {}

    private final List<List<Arista>> adyacentes;
    private final boolean dirigido;
    private int cantidadDeAristas;

    public Grafo(int cantidadDeVertices, boolean dirigido) {
        if (cantidadDeVertices < 0) {
            throw new IllegalArgumentException("la cantidad de vértices no puede ser negativa");
        }
        this.adyacentes = new ArrayList<>(cantidadDeVertices);
        for (int v = 0; v < cantidadDeVertices; v++) {
            adyacentes.add(new ArrayList<>());          // cada vértice arranca sin vecinos
        }
        this.dirigido = dirigido;
        this.cantidadDeAristas = 0;
    }

    /** Arista sin peso (peso 1). */
    public void agregarArista(int origen, int destino) {
        agregarArista(origen, destino, 1);
    }

    public void agregarArista(int origen, int destino, int peso) {
        validar(origen);
        validar(destino);
        adyacentes.get(origen).add(new Arista(destino, peso));
        if (!dirigido) {                                 // no dirigido: también al revés
            adyacentes.get(destino).add(new Arista(origen, peso));
        }
        cantidadDeAristas++;
    }

    /** Los vecinos de v, en el orden en que se agregaron las aristas. */
    public List<Arista> vecinos(int vertice) {
        validar(vertice);
        return List.copyOf(adyacentes.get(vertice));
    }

    /** ¿Existe la arista origen→destino? Cuesta O(grado(origen)). */
    public boolean existeArista(int origen, int destino) {
        validar(origen);
        validar(destino);
        for (Arista arista : adyacentes.get(origen)) {
            if (arista.destino() == destino) {
                return true;
            }
        }
        return false;
    }

    public int cantidadDeVertices() {
        return adyacentes.size();
    }

    public int cantidadDeAristas() {
        return cantidadDeAristas;
    }

    public boolean esDirigido() {
        return dirigido;
    }

    /** Cantidad de aristas salientes de v (en no dirigidos, su grado). */
    public int grado(int vertice) {
        validar(vertice);
        return adyacentes.get(vertice).size();
    }

    private void validar(int vertice) {
        if (vertice < 0 || vertice >= adyacentes.size()) {
            throw new IllegalArgumentException(
                    "vértice inválido: " + vertice + " (hay " + adyacentes.size() + ")");
        }
    }

    public static void main(String[] args) {
        // El grafo de ejemplo de la unidad: A=0, B=1, C=2, D=3, E=4, F=5
        Grafo grafo = new Grafo(6, false);
        grafo.agregarArista(0, 1);   // A-B
        grafo.agregarArista(0, 2);   // A-C
        grafo.agregarArista(1, 3);   // B-D
        grafo.agregarArista(2, 3);   // C-D
        grafo.agregarArista(2, 4);   // C-E
        grafo.agregarArista(3, 5);   // D-F
        grafo.agregarArista(4, 5);   // E-F

        System.out.println(grafo.cantidadDeVertices());  // 6
        System.out.println(grafo.cantidadDeAristas());   // 7
        System.out.println(grafo.vecinos(2));            // [Arista[destino=0, peso=1], Arista[destino=3, peso=1], Arista[destino=4, peso=1]]
        System.out.println(grafo.existeArista(0, 3));    // false (A y D no son vecinos directos)
        System.out.println(grafo.grado(3));              // 3 (D toca a B, C y F)
    }
}
