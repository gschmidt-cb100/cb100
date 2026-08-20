package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaminoMinimoEnGrillaTest {

    @Test
    @DisplayName("Grilla clásica [[1,3,1],[1,5,1],[4,2,1]] -> costo 7")
    void costoClasico() {
        int[][] grilla = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        assertEquals(7, CaminoMinimoEnGrilla.costoMinimo(grilla));
    }

    @Test
    @DisplayName("El camino de la grilla clásica es D, D, A, A (por la fila de arriba)")
    void caminoClasico() {
        int[][] grilla = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        assertEquals(List.of("D", "D", "A", "A"),
                CaminoMinimoEnGrilla.camino(grilla));
    }

    @Test
    @DisplayName("El camino reconstruido efectivamente cuesta el costo mínimo")
    void elCaminoSumaElCosto() {
        int[][] grilla = {{5, 9, 6}, {11, 5, 2}};
        List<String> camino = CaminoMinimoEnGrilla.camino(grilla);
        int i = 0;
        int j = 0;
        int costo = grilla[0][0];
        for (String paso : camino) {
            if (paso.equals("D")) {
                j++;
            } else {
                i++;
            }
            costo += grilla[i][j];
        }
        assertEquals(1, i, "debe terminar en la última fila");
        assertEquals(2, j, "debe terminar en la última columna");
        assertEquals(CaminoMinimoEnGrilla.costoMinimo(grilla), costo);
    }

    @Test
    @DisplayName("Grilla de 1x1: el costo es la única casilla y no hay movimientos")
    void grillaDeUnaCasilla() {
        int[][] grilla = {{42}};
        assertEquals(42, CaminoMinimoEnGrilla.costoMinimo(grilla));
        assertTrue(CaminoMinimoEnGrilla.camino(grilla).isEmpty());
    }

    @Test
    @DisplayName("Una sola fila: solo se puede ir a la derecha")
    void unaSolaFila() {
        int[][] grilla = {{2, 7, 4}};
        assertEquals(13, CaminoMinimoEnGrilla.costoMinimo(grilla));
        assertEquals(List.of("D", "D"), CaminoMinimoEnGrilla.camino(grilla));
    }

    @Test
    @DisplayName("Una sola columna: solo se puede bajar")
    void unaSolaColumna() {
        int[][] grilla = {{2}, {7}, {4}};
        assertEquals(13, CaminoMinimoEnGrilla.costoMinimo(grilla));
        assertEquals(List.of("A", "A"), CaminoMinimoEnGrilla.camino(grilla));
    }
}
