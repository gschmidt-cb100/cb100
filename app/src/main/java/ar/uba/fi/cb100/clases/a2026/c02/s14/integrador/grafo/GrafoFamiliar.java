package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Grafo <b>dirigido</b> de parentesco: los vértices son personas (por DNI) y
 * hay una arista de cada padre o madre a cada hijo. Representación: listas
 * de adyacencia en un {@code HashMap}, una para los hijos y otra para los
 * padres, así se puede caminar en los dos sentidos.
 * <p>
 * Como nadie es ancestro de sí mismo, el grafo no tiene ciclos (es un DAG) y
 * admite orden topológico: una lista donde todo padre aparece antes que sus
 * hijos. Si los datos trajeran un ciclo, el orden topológico lo detecta.
 */
public class GrafoFamiliar {

    private final Map<Long, Persona> personas = new HashMap<>();
    private final Map<Long, List<Long>> hijos = new HashMap<>();
    private final Map<Long, List<Long>> padres = new HashMap<>();
    private int cantidadDeAristas;

    /**
     * pre: {@code personas} no es null ni tiene DNI repetidos.
     * post: arma el grafo. Un DNI de padre o madre que no corresponde a
     *       ninguna persona del conjunto se ignora (ese padre no está en el archivo).
     */
    public GrafoFamiliar(Collection<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        for (Persona persona : personas) {
            ValidacionesUtiles.validarFalso(this.personas.containsKey(persona.dni()), "DNI repetido: " + persona.dni());
            this.personas.put(persona.dni(), persona);
            hijos.put(persona.dni(), new ArrayList<>());
            padres.put(persona.dni(), new ArrayList<>());
        }
        for (Persona persona : personas) {
            agregarArista(persona.dniDelPadre(), persona.dni());
            agregarArista(persona.dniDeLaMadre(), persona.dni());
        }
    }

    private void agregarArista(Long dniDelProgenitor, long dniDelHijo) {
        if (dniDelProgenitor != null && personas.containsKey(dniDelProgenitor)) {
            hijos.get(dniDelProgenitor).add(dniDelHijo);
            padres.get(dniDelHijo).add(dniDelProgenitor);
            cantidadDeAristas++;
        }
    }

    // ---------------------------------------------------------------- recorridos

    /**
     * pre: {@code dniDeOrigen} es un vértice del grafo.
     * post: devuelve los DNI en el orden en que los visita un recorrido en
     *       <b>profundidad</b> (DFS) que sale del origen y baja por los hijos:
     *       se agota una rama antes de pasar a la siguiente. Usa una pila.
     */
    public List<Long> recorrerEnProfundidad(long dniDeOrigen) {
        validarVertice(dniDeOrigen);
        List<Long> visita = new ArrayList<>();
        Set<Long> visitados = new HashSet<>();
        Deque<Long> pila = new ArrayDeque<>();
        pila.push(dniDeOrigen);
        while (!pila.isEmpty()) {
            long actual = pila.pop();
            if (visitados.add(actual)) {
                visita.add(actual);
                List<Long> susHijos = hijos.get(actual);
                for (int i = susHijos.size() - 1; i >= 0; i--) {   // al revés, para que salgan en orden
                    pila.push(susHijos.get(i));
                }
            }
        }
        return visita;
    }

    /**
     * pre: {@code dniDeOrigen} es un vértice del grafo.
     * post: devuelve los DNI en el orden de un recorrido en <b>anchura</b>
     *       (BFS): primero el origen, después todos sus hijos, después todos
     *       sus nietos. Usa una cola.
     */
    public List<Long> recorrerEnAnchura(long dniDeOrigen) {
        validarVertice(dniDeOrigen);
        List<Long> visita = new ArrayList<>();
        Set<Long> visitados = new HashSet<>();
        Deque<Long> cola = new ArrayDeque<>();
        cola.add(dniDeOrigen);
        visitados.add(dniDeOrigen);
        while (!cola.isEmpty()) {
            long actual = cola.remove();
            visita.add(actual);
            for (long hijo : hijos.get(actual)) {
                if (visitados.add(hijo)) {
                    cola.add(hijo);
                }
            }
        }
        return visita;
    }

    /**
     * post: devuelve todos los vértices en <b>orden topológico</b>: cada
     *       persona aparece después de sus padres. A igualdad de condiciones
     *       sale primero el menor DNI, para que el resultado sea siempre el mismo.
     */
    public List<Long> ordenarTopologicamente() {
        return ordenarTopologicamente(personas.keySet());
    }

    /**
     * pre: todos los DNI de {@code vertices} son vértices del grafo.
     * post: devuelve esos vértices en orden topológico, mirando sólo las
     *       aristas entre ellos (algoritmo de Kahn). Si hay un ciclo, lanza
     *       {@link IllegalStateException}.
     */
    public List<Long> ordenarTopologicamente(Collection<Long> vertices) {
        ValidacionesUtiles.validarDistintoDeNull(vertices, "vértices");
        Set<Long> incluidos = new HashSet<>(vertices);
        Map<Long, Integer> padresPendientes = new HashMap<>();
        PriorityQueue<Long> listos = new PriorityQueue<>();
        for (long dni : incluidos) {
            validarVertice(dni);
            int pendientes = 0;
            for (long padre : padres.get(dni)) {
                if (incluidos.contains(padre)) {
                    pendientes++;
                }
            }
            padresPendientes.put(dni, pendientes);
            if (pendientes == 0) {
                listos.add(dni);                 // sin padres en el conjunto: puede ir primero
            }
        }
        List<Long> orden = new ArrayList<>(incluidos.size());
        while (!listos.isEmpty()) {
            long actual = listos.remove();
            orden.add(actual);
            for (long hijo : hijos.get(actual)) {
                if (incluidos.contains(hijo) && padresPendientes.merge(hijo, -1, Integer::sum) == 0) {
                    listos.add(hijo);            // ya salieron todos sus padres
                }
            }
        }
        ValidacionesUtiles.validarEstadoFalso(orden.size() != incluidos.size(),
                "El parentesco tiene un ciclo: alguien figura como ancestro de sí mismo");
        return orden;
    }

    // ---------------------------------------------------------------- familia

    /**
     * pre: {@code dni} es un vértice; {@code niveles} y {@code maximo} son mayores que cero.
     * post: devuelve la familia cercana de la persona: ella, sus ancestros y
     *       sus descendientes hasta esa cantidad de niveles, con un tope de
     *       personas. La persona siempre es la primera.
     */
    public Set<Long> getFamiliaDe(long dni, int niveles, int maximo) {
        validarVertice(dni);
        ValidacionesUtiles.esMayorQueCero(niveles, "niveles");
        ValidacionesUtiles.esMayorQueCero(maximo, "máximo de personas");
        Set<Long> familia = new LinkedHashSet<>();
        familia.add(dni);
        expandir(dni, padres, niveles, maximo, familia);
        expandir(dni, hijos, niveles, maximo, familia);
        return familia;
    }

    /** Recorrido en anchura limitado en niveles y en cantidad, hacia arriba o hacia abajo. */
    private static void expandir(long origen, Map<Long, List<Long>> vecinos, int niveles, int maximo, Set<Long> familia) {
        List<Long> nivelActual = List.of(origen);
        for (int nivel = 0; nivel < niveles && !nivelActual.isEmpty(); nivel++) {
            List<Long> siguiente = new ArrayList<>();
            for (long dni : nivelActual) {
                for (long vecino : vecinos.get(dni)) {
                    if (familia.size() < maximo && familia.add(vecino)) {
                        siguiente.add(vecino);
                    }
                }
            }
            nivelActual = siguiente;
        }
    }

    // ---------------------------------------------------------------- consultas

    /** post: devuelve una copia de los DNI de los hijos, en el orden del archivo. */
    public List<Long> getHijosDe(long dni) {
        validarVertice(dni);
        return List.copyOf(hijos.get(dni));
    }

    /** post: devuelve una copia de los DNI de los padres que están en el grafo. */
    public List<Long> getPadresDe(long dni) {
        validarVertice(dni);
        return List.copyOf(padres.get(dni));
    }

    /** post: devuelve la persona con ese DNI. */
    public Persona getPersona(long dni) {
        validarVertice(dni);
        return personas.get(dni);
    }

    public boolean contiene(long dni) {
        return personas.containsKey(dni);
    }

    public int getCantidadDeVertices() {
        return personas.size();
    }

    public int getCantidadDeAristas() {
        return cantidadDeAristas;
    }

    private void validarVertice(long dni) {
        ValidacionesUtiles.validarFalso(!personas.containsKey(dni), "No hay ninguna persona con DNI " + dni);
    }
}
