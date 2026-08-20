package ar.uba.fi.cb100.guia.i10_tecnicas.i04_dificil.e05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaminosConObstaculosTest {

    private final CaminosConObstaculos contador = new CaminosConObstaculos();

    @Test
    @DisplayName("Grilla 3x3 sin obstaculos: 6 caminos")
    void tresPorTresLibre() {
        int[][] grilla = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        assertEquals(6, contador.caminos(grilla));
    }

    @Test
    @DisplayName("Grilla 3x3 con obstaculo central: 2 caminos")
    void obstaculoCentral() {
        int[][] grilla = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        assertEquals(2, contador.caminos(grilla));
    }

    @Test
    @DisplayName("Llegada bloqueada: 0 caminos")
    void llegadaBloqueada() {
        int[][] grilla = {{0, 0, 0}, {0, 0, 0}, {0, 0, 1}};
        assertEquals(0, contador.caminos(grilla));
    }

    @Test
    @DisplayName("Salida bloqueada: 0 caminos")
    void salidaBloqueada() {
        int[][] grilla = {{1, 0}, {0, 0}};
        assertEquals(0, contador.caminos(grilla));
    }

    @Test
    @DisplayName("Grilla 1x1 libre: 1 camino (quedarse quieto)")
    void unaCelda() {
        assertEquals(1, contador.caminos(new int[][] {{0}}));
    }

    @Test
    @DisplayName("Sin obstaculos coincide con el numero combinatorio C(m+n-2, m-1) y no desborda")
    void coincideConCombinatoria() {
        // 16x16 libre: C(30, 15) = 155117520, ya cerca del limite de int.
        int[][] grilla = new int[16][16];
        assertEquals(155117520L, contador.caminos(grilla));
    }
}
