package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LaberintoTest {

    @Test
    @DisplayName("laberinto con salida devuelve true")
    void conSalida() {
        boolean[][] libre = {
                {true, true, false},
                {false, true, false},
                {false, true, true}
        };
        assertTrue(Laberinto.hayCamino(libre));
    }

    @Test
    @DisplayName("laberinto sin salida devuelve false")
    void sinSalida() {
        boolean[][] libre = {
                {true, false},
                {false, true}
        };
        assertFalse(Laberinto.hayCamino(libre));
    }

    @Test
    @DisplayName("entrada bloqueada devuelve false")
    void entradaBloqueada() {
        boolean[][] libre = {
                {false, true},
                {true, true}
        };
        assertFalse(Laberinto.hayCamino(libre));
    }

    @Test
    @DisplayName("una sola celda libre es camino trivial")
    void celdaUnica() {
        assertTrue(Laberinto.hayCamino(new boolean[][]{{true}}));
        assertFalse(Laberinto.hayCamino(new boolean[][]{{false}}));
    }

    @Test
    @DisplayName("camino que obliga a rodear (requiere backtracking)")
    void caminoConRodeo() {
        boolean[][] libre = {
                {true, true, true, true},
                {false, false, false, true},
                {true, true, true, true},
                {true, false, false, false},
                {true, true, true, true}
        };
        assertTrue(Laberinto.hayCamino(libre));
    }

    @Test
    @DisplayName("laberinto vacio lanza excepcion")
    void vacioLanza() {
        assertThrows(IllegalArgumentException.class,
                () -> Laberinto.hayCamino(new boolean[0][0]));
    }
}
