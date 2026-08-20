package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrafoConNombresTest {

    private GrafoConNombres<String> rutas;

    @BeforeEach
    void armarRed() {
        rutas = new GrafoConNombres<>(false);
        rutas.agregarArista("BA", "Rosario");
        rutas.agregarArista("BA", "MarDelPlata");
        rutas.agregarArista("Rosario", "Cordoba");
        rutas.agregarArista("Cordoba", "Mendoza");
        rutas.agregarArista("BA", "Neuquen");
        rutas.agregarArista("Mendoza", "Neuquen");
        rutas.agregarArista("Neuquen", "Bariloche");
    }

    @Test
    @DisplayName("Los vecinos respetan el orden de insercion de las aristas")
    void vecinosEnOrden() {
        assertEquals(List.of("Rosario", "MarDelPlata", "Neuquen"), rutas.vecinos("BA"));
        assertEquals(List.of("Cordoba", "Neuquen"), rutas.vecinos("Mendoza"));
    }

    @Test
    @DisplayName("BFS desde BA visita por niveles y es determinista")
    void bfsPorNiveles() {
        // Nivel 0: BA; nivel 1: Rosario, MarDelPlata, Neuquen (en orden de
        // insercion); nivel 2: Cordoba (via Rosario), Mendoza y Bariloche (via Neuquen).
        assertEquals(
                List.of("BA", "Rosario", "MarDelPlata", "Neuquen",
                        "Cordoba", "Mendoza", "Bariloche"),
                rutas.bfs("BA"));
    }

    @Test
    @DisplayName("DFS desde BA se mete a fondo por la primera rama")
    void dfsAFondo() {
        // BA -> Rosario -> Cordoba -> Mendoza -> Neuquen -> Bariloche,
        // vuelve hasta BA y recien ahi visita MarDelPlata.
        assertEquals(
                List.of("BA", "Rosario", "Cordoba", "Mendoza",
                        "Neuquen", "Bariloche", "MarDelPlata"),
                rutas.dfs("BA"));
    }

    @Test
    @DisplayName("BFS y DFS visitan el mismo conjunto de vertices")
    void mismoConjunto() {
        assertEquals(new LinkedHashSet<>(rutas.bfs("BA")),
                     new LinkedHashSet<>(rutas.dfs("BA")));
    }

    @Test
    @DisplayName("En un grafo no dirigido la arista vale en ambos sentidos")
    void idaYVuelta() {
        assertTrue(rutas.vecinos("Bariloche").contains("Neuquen"));
        assertTrue(rutas.vecinos("Neuquen").contains("Bariloche"));
    }

    @Test
    @DisplayName("En un grafo dirigido la arista va en un solo sentido")
    void dirigido() {
        GrafoConNombres<String> plan = new GrafoConNombres<>(true);
        plan.agregarArista("Prog1", "CB100");
        assertEquals(List.of("CB100"), plan.vecinos("Prog1"));
        assertEquals(List.of(), plan.vecinos("CB100"));
    }

    @Test
    @DisplayName("Preguntar por un vertice desconocido lanza excepcion")
    void verticeDesconocido() {
        assertThrows(IllegalArgumentException.class, () -> rutas.vecinos("Ushuaia"));
        assertThrows(IllegalArgumentException.class, () -> rutas.bfs("Ushuaia"));
    }

    @Test
    @DisplayName("agregarVertice es idempotente y no pisa los vecinos")
    void agregarVerticeIdempotente() {
        rutas.agregarVertice("BA");                    // ya existia
        assertEquals(3, rutas.vecinos("BA").size());   // conserva sus vecinos
        assertEquals(7, rutas.cantidadDeVertices());
    }
}
