package ar.uba.fi.cb100.guia.i11_grafos.i04_dificil.e08;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuelloDeBotellaTest {

    private static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;
    private Grafo red;

    @BeforeEach
    void armarRedDeFibra() {
        red = new Grafo(6, false);
        red.agregarArista(A, B, 2);
        red.agregarArista(B, C, 1);
        red.agregarArista(A, C, 3);
        red.agregarArista(B, D, 4);
        red.agregarArista(C, E, 6);
        red.agregarArista(D, E, 5);
        red.agregarArista(E, F, 3);
        red.agregarArista(D, F, 7);
    }

    @Test
    @DisplayName("A->F: el cuello de botella es 5 (verificado a mano)")
    void cuelloDeBotellaAF() {
        // Aristas ordenadas: 1 (B-C), 2 (A-B), 3 (A-C), 3 (E-F), 4 (B-D), 5 (D-E)...
        // Recien al agregar D-E 5 se conectan A y F (camino A-B-D-E-F, maximo 5).
        // La alternativa directa D-F pesa 7 y C-E pesa 6: peor.
        assertEquals(5, CuelloDeBotella.entre(red, A, F));
    }

    @Test
    @DisplayName("La respuesta es simetrica en grafos no dirigidos")
    void simetria() {
        assertEquals(CuelloDeBotella.entre(red, A, F), CuelloDeBotella.entre(red, F, A));
    }

    @Test
    @DisplayName("A->C: conviene dar la vuelta por B (cuello 2) antes que la directa de 3")
    void convieneRodear() {
        // Camino directo A-C: maximo 3. Camino A-B-C: maximo max(2, 1) = 2.
        assertEquals(2, CuelloDeBotella.entre(red, A, C));
    }

    @Test
    @DisplayName("Vecinos directos con la arista mas liviana: el cuello es esa arista")
    void vecinosDirectos() {
        assertEquals(1, CuelloDeBotella.entre(red, B, C));
        assertEquals(2, CuelloDeBotella.entre(red, A, B));
    }

    @Test
    @DisplayName("D->E: gana la arista directa de 5 sobre los rodeos de 6 y 7")
    void caminoDeD() {
        // D-E directo: maximo 5. Alternativa D-B-C-E: max(4, 1, 6) = 6.
        // Alternativa D-F-E: max(7, 3) = 7. Gana la directa: 5.
        assertEquals(5, CuelloDeBotella.entre(red, D, E));
    }

    @Test
    @DisplayName("El cuello de botella de un vertice a si mismo es 0")
    void mismoVertice() {
        assertEquals(0, CuelloDeBotella.entre(red, A, A));
    }

    @Test
    @DisplayName("Vertices desconectados lanzan IllegalStateException")
    void desconectados() {
        Grafo partido = new Grafo(4, false);
        partido.agregarArista(0, 1, 1);
        partido.agregarArista(2, 3, 1);
        assertThrows(IllegalStateException.class, () -> CuelloDeBotella.entre(partido, 0, 3));
    }

    @Test
    @DisplayName("Sobre un grafo dirigido lanza IllegalArgumentException")
    void rechazaDirigidos() {
        Grafo dirigido = new Grafo(2, true);
        dirigido.agregarArista(0, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> CuelloDeBotella.entre(dirigido, 0, 1));
    }
}
