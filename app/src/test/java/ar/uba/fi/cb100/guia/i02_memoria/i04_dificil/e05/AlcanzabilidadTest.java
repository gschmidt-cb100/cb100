package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AlcanzabilidadTest {

    @Test
    @DisplayName("Camino lineal con un vertice aislado")
    void caminoLinealConAislado() {
        boolean[][] ady = {
                {false, true,  false, false},
                {false, false, true,  false},
                {false, false, false, false},
                {false, false, false, false},
        };
        boolean[] visitados = Alcanzabilidad.alcanzables(ady, 0);
        assertArrayEquals(new boolean[]{true, true, true, false}, visitados);
        assertEquals(1, Alcanzabilidad.sinMarcar(visitados));
    }

    @Test
    @DisplayName("Todos alcanzables: sinMarcar devuelve 0")
    void todosAlcanzables() {
        boolean[][] ady = {
                {false, true,  false},
                {false, false, true},
                {true,  false, false},
        };
        boolean[] visitados = Alcanzabilidad.alcanzables(ady, 0);
        assertArrayEquals(new boolean[]{true, true, true}, visitados);
        assertEquals(0, Alcanzabilidad.sinMarcar(visitados));
    }

    @Test
    @DisplayName("El grafo con ciclo no genera bucle infinito")
    void grafoConCicloTermina() {
        boolean[][] ady = {
                {false, true},
                {true,  false},
        };
        boolean[] visitados = Alcanzabilidad.alcanzables(ady, 1);
        assertArrayEquals(new boolean[]{true, true}, visitados);
    }

    @Test
    @DisplayName("Caso borde: raiz aislada solo se marca a si misma")
    void raizAislada() {
        boolean[][] ady = {
                {false, false},
                {false, false},
        };
        boolean[] visitados = Alcanzabilidad.alcanzables(ady, 1);
        assertArrayEquals(new boolean[]{false, true}, visitados);
        assertEquals(1, Alcanzabilidad.sinMarcar(visitados));
    }
}
