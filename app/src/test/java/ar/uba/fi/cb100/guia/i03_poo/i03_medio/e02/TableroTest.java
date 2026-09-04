package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    @DisplayName("partida completa: X gana por la diagonal principal")
    void partidaConGanador() {
        Tablero tablero = new Tablero();
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 0, 1);
        tablero.poner(Ficha.X, 1, 1);
        tablero.poner(Ficha.O, 0, 2);
        assertFalse(tablero.hayGanador(), "todavía nadie ganó");

        tablero.poner(Ficha.X, 2, 2);
        assertTrue(tablero.hayGanador());
        assertFalse(tablero.hayEmpate());
        assertEquals(Ficha.X, tablero.fichaEn(1, 1));
        assertEquals(Ficha.O, tablero.fichaEn(0, 1));
    }

    @Test
    @DisplayName("partida completa sin tres en línea: empate")
    void partidaEmpatada() {
        Tablero tablero = new Tablero();
        // X O X / X O O / O X X : nadie hace tres en línea.
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 0, 1);
        tablero.poner(Ficha.X, 0, 2);
        tablero.poner(Ficha.O, 1, 1);
        tablero.poner(Ficha.X, 1, 0);
        tablero.poner(Ficha.O, 1, 2);
        tablero.poner(Ficha.X, 2, 1);
        tablero.poner(Ficha.O, 2, 0);
        assertFalse(tablero.hayEmpate(), "con una casilla libre todavía no es empate");

        tablero.poner(Ficha.X, 2, 2);
        assertTrue(tablero.hayEmpate());
        assertFalse(tablero.hayGanador());
    }

    @Test
    @DisplayName("una casilla vacía devuelve null y el tablero nuevo no tiene ganador ni empate")
    void tableroNuevo() {
        Tablero tablero = new Tablero();
        assertNull(tablero.fichaEn(1, 1));
        assertFalse(tablero.hayGanador());
        assertFalse(tablero.hayEmpate());
    }

    @Test
    @DisplayName("no se puede poner en una casilla ocupada")
    void casillaOcupada() {
        Tablero tablero = new Tablero();
        tablero.poner(Ficha.X, 1, 1);
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 1, 1));
    }

    @Test
    @DisplayName("no se puede jugar fuera del turno: arranca X y se alterna")
    void turnoEquivocado() {
        Tablero tablero = new Tablero();
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 0, 0),
                "arranca X, no puede empezar O");

        tablero.poner(Ficha.X, 0, 0);
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.X, 0, 1),
                "X no puede jugar dos veces seguidas");
    }

    @Test
    @DisplayName("casillas fuera de rango y ficha null lanzan IllegalArgumentException")
    void parametrosInvalidos() {
        Tablero tablero = new Tablero();
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(Ficha.X, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(Ficha.X, 0, 3));
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(Ficha.X, 3, 3));
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> tablero.fichaEn(0, -1));
        assertThrows(IllegalArgumentException.class, () -> tablero.fichaEn(5, 0));
    }

    @Test
    @DisplayName("terminada la partida no se puede seguir jugando")
    void partidaTerminada() {
        Tablero tablero = new Tablero();
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 1, 0);
        tablero.poner(Ficha.X, 0, 1);
        tablero.poner(Ficha.O, 1, 1);
        tablero.poner(Ficha.X, 0, 2); // X gana por la primera fila.
        assertTrue(tablero.hayGanador());
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 2, 2));
    }
}
