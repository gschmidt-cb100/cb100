package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaminosEnGrillaTest {

    @Test
    @DisplayName("una grilla de 3x3 tiene 6 caminos")
    void grillaDeTresPorTres() {
        assertEquals(6, CaminosEnGrilla.caminos(3, 3));
    }

    @Test
    @DisplayName("una grilla de una sola fila tiene un único camino")
    void unaSolaFila() {
        assertEquals(1, CaminosEnGrilla.caminos(1, 5));
    }

    @Test
    @DisplayName("la grilla de 1x1 tiene un camino: quedarse donde está")
    void grillaMinima() {
        assertEquals(1, CaminosEnGrilla.caminos(1, 1));
    }

    @Test
    @DisplayName("la cantidad de caminos es simétrica: 2x3 = 3x2 = 3")
    void esSimetrica() {
        assertEquals(3, CaminosEnGrilla.caminos(2, 3));
        assertEquals(CaminosEnGrilla.caminos(2, 3), CaminosEnGrilla.caminos(3, 2));
    }

    @Test
    @DisplayName("dimensiones menores a 1 lanzan IllegalArgumentException")
    void dimensionInvalidaLanza() {
        assertThrows(IllegalArgumentException.class, () -> CaminosEnGrilla.caminos(0, 3));
        assertThrows(IllegalArgumentException.class, () -> CaminosEnGrilla.caminos(3, -1));
    }
}
