package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e01;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TECNICA: GRAFO GENERICO SOBRE UN MAP (lista de adyacencia con nombres).
 *
 * El {@code Grafo} del material numera los vertices de 0 a n-1: es rapido y
 * compacto, pero obliga a mantener aparte la tabla "nombre -&gt; numero".
 * Aca damos el paso natural con lo visto en la unidad de hashing (U7):
 * la lista de adyacencia vive en un {@code Map<T, List<T>>}, asi los
 * vertices SON los objetos que nos importan (ciudades, materias, personas).
 *
 * Por que {@link LinkedHashMap} y no {@code HashMap}: ademas del acceso
 * O(1) esperado, recuerda el ORDEN DE INSERCION. Eso hace que los
 * recorridos sean deterministas (siempre el mismo resultado para el mismo
 * grafo), algo clave para testear y para razonar en clase.
 *
 * BFS y DFS son los mismos de siempre: lo unico que cambia es que el
 * conjunto de visitados pasa de un {@code boolean[]} a un {@code Set<T>}.
 * Ambos siguen costando O(n + m).
 *
 * @param <T> el tipo de los vertices (necesita equals/hashCode coherentes)
 */
public class GrafoConNombres<T> {

    private final Map<T, List<T>> adyacentes = new LinkedHashMap<>();
    private final boolean dirigido;

    public GrafoConNombres(boolean dirigido) {
        this.dirigido = dirigido;
    }

    /** Registra el vertice si no existia (idempotente). */
    public void agregarVertice(T vertice) {
        adyacentes.computeIfAbsent(vertice, v -> new ArrayList<>());
    }

    /**
     * Agrega la arista origen-&gt;destino (y la vuelta si el grafo no es
     * dirigido). Si algun extremo no estaba registrado, lo registra:
     * asi el grafo se puede armar declarando solo las aristas.
     */
    public void agregarArista(T origen, T destino) {
        agregarVertice(origen);
        agregarVertice(destino);
        adyacentes.get(origen).add(destino);
        if (!dirigido) {
            adyacentes.get(destino).add(origen);
        }
    }

    /** Los vecinos de un vertice, en el orden en que se agregaron las aristas. */
    public List<T> vecinos(T vertice) {
        validar(vertice);
        return List.copyOf(adyacentes.get(vertice));
    }

    public boolean esDirigido() {
        return dirigido;
    }

    public int cantidadDeVertices() {
        return adyacentes.size();
    }

    /** Los vertices en orden de insercion (gracias al LinkedHashMap). */
    public Set<T> vertices() {
        return new LinkedHashSet<>(adyacentes.keySet());
    }

    /**
     * Recorrido a lo ancho desde el origen: identico al del material,
     * pero el "visitado" es un Set en lugar de un arreglo de booleanos.
     */
    public List<T> bfs(T origen) {
        validar(origen);
        List<T> orden = new ArrayList<>();
        Set<T> visitados = new LinkedHashSet<>();
        Deque<T> cola = new ArrayDeque<>();

        visitados.add(origen);                       // se marca al ENCOLAR
        cola.addLast(origen);
        while (!cola.isEmpty()) {
            T vertice = cola.removeFirst();
            orden.add(vertice);
            for (T vecino : adyacentes.get(vertice)) {
                if (visitados.add(vecino)) {         // add devuelve false si ya estaba
                    cola.addLast(vecino);
                }
            }
        }
        return orden;
    }

    /** Recorrido en profundidad (recursivo) desde el origen. */
    public List<T> dfs(T origen) {
        validar(origen);
        List<T> orden = new ArrayList<>();
        dfsDesde(origen, new LinkedHashSet<>(), orden);
        return orden;
    }

    private void dfsDesde(T vertice, Set<T> visitados, List<T> orden) {
        visitados.add(vertice);
        orden.add(vertice);
        for (T vecino : adyacentes.get(vertice)) {
            if (!visitados.contains(vecino)) {
                dfsDesde(vecino, visitados, orden);  // a fondo por cada vecino nuevo
            }
        }
    }

    private void validar(T vertice) {
        if (!adyacentes.containsKey(vertice)) {
            throw new IllegalArgumentException("vertice desconocido: " + vertice);
        }
    }

    public static void main(String[] args) {
        // La red de ciudades de la unidad, ahora con sus nombres de verdad.
        GrafoConNombres<String> rutas = new GrafoConNombres<>(false);
        rutas.agregarArista("Buenos Aires", "Rosario");
        rutas.agregarArista("Buenos Aires", "Mar del Plata");
        rutas.agregarArista("Rosario", "Cordoba");
        rutas.agregarArista("Cordoba", "Mendoza");
        rutas.agregarArista("Buenos Aires", "Neuquen");
        rutas.agregarArista("Mendoza", "Neuquen");
        rutas.agregarArista("Neuquen", "Bariloche");

        System.out.println("Vecinos de BA: " + rutas.vecinos("Buenos Aires"));
        System.out.println("BFS: " + rutas.bfs("Buenos Aires"));
        System.out.println("DFS: " + rutas.dfs("Buenos Aires"));
    }
}
