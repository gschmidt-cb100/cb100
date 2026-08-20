package ar.uba.fi.cb100.guia.i11_grafos.i02_facil.e09;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LecturaDeDijkstraTest {

    private Dijkstra.Resultado resultado;

    @BeforeEach
    void correrDijkstra() {
        resultado = Dijkstra.caminosMinimos(LecturaDeDijkstra.mapaDeRutas(), 0);
    }

    @Test
    @DisplayName("Las distancias desde A son [0, 7, 9, 20, 20, 11]")
    void distanciasDesdeA() {
        assertArrayEquals(new int[] {0, 7, 9, 20, 20, 11}, resultado.distancia());
    }

    @Test
    @DisplayName("El camino minimo hasta E es A-C-F-E = [0, 2, 5, 4]")
    void caminoHastaE() {
        assertEquals(List.of(0, 2, 5, 4), resultado.caminoHasta(4));
    }

    @Test
    @DisplayName("El desvio A-C-F-E (20) le gana al camino directo A-F-E (23)")
    void elDesvioLeGanaAlDirecto() {
        assertEquals(20, resultado.distancia()[4]);
        assertTrue(resultado.distancia()[4] < 14 + 9);   // A-F + F-E
    }

    @Test
    @DisplayName("El camino hasta el propio origen es [0] y su distancia 0")
    void caminoAlOrigen() {
        assertEquals(List.of(0), resultado.caminoHasta(0));
        assertEquals(0, resultado.distancia()[0]);
    }
}
