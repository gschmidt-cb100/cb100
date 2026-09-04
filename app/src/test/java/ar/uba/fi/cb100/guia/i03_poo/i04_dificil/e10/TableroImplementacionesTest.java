package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Un MISMO juego de tests contra las DOS implementaciones del contrato
 * {@link Tablero}: los métodos auxiliares reciben un Tablero (el contrato)
 * y lo ejercitan sin saber cómo guarda las casillas; cada implementación
 * tiene su @Test que los invoca con una instancia concreta.
 */
class TableroImplementacionesTest {

    @Test
    @DisplayName("TableroConMatriz cumple el contrato completo")
    void tableroConMatrizCumpleElContrato() {
        ejercitarContrato(TableroConMatriz::new);
    }

    @Test
    @DisplayName("TableroPlano cumple el contrato completo")
    void tableroPlanoCumpleElContrato() {
        ejercitarContrato(TableroPlano::new);
    }

    /**
     * Cada escenario arranca de un tablero nuevo, así que se recibe una
     * "fábrica" de tableros en vez de una única instancia.
     */
    private void ejercitarContrato(FabricaDeTableros fabrica) {
        partidaConGanador(fabrica.crear());
        partidaEmpatada(fabrica.crear());
        jugadasInvalidas(fabrica.crear());
        tableroNuevo(fabrica.crear());
    }

    private void partidaConGanador(Tablero tablero) {
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 1, 0);
        tablero.poner(Ficha.X, 0, 1);
        tablero.poner(Ficha.O, 1, 1);
        assertFalse(tablero.hayGanador());

        tablero.poner(Ficha.X, 0, 2); // X gana la primera fila.
        assertTrue(tablero.hayGanador());
        assertFalse(tablero.hayEmpate());
        assertEquals(Ficha.X, tablero.fichaEn(0, 2));
        assertEquals(Ficha.O, tablero.fichaEn(1, 1));
        assertNull(tablero.fichaEn(2, 2), "casilla libre devuelve null");
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 2, 2),
                "terminada la partida no se juega más");
    }

    private void partidaEmpatada(Tablero tablero) {
        // X O X / X O O / O X X : nadie hace tres en línea.
        tablero.poner(Ficha.X, 0, 0);
        tablero.poner(Ficha.O, 0, 1);
        tablero.poner(Ficha.X, 0, 2);
        tablero.poner(Ficha.O, 1, 1);
        tablero.poner(Ficha.X, 1, 0);
        tablero.poner(Ficha.O, 1, 2);
        tablero.poner(Ficha.X, 2, 1);
        tablero.poner(Ficha.O, 2, 0);
        assertFalse(tablero.hayEmpate(), "con una casilla libre no hay empate todavía");

        tablero.poner(Ficha.X, 2, 2);
        assertTrue(tablero.hayEmpate());
        assertFalse(tablero.hayGanador());
    }

    private void jugadasInvalidas(Tablero tablero) {
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 0, 0),
                "arranca X");
        tablero.poner(Ficha.X, 1, 1);
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.X, 0, 0),
                "los turnos se alternan");
        assertThrows(IllegalStateException.class, () -> tablero.poner(Ficha.O, 1, 1),
                "casilla ocupada");
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(Ficha.O, 3, 0),
                "fuera de rango");
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(Ficha.O, 0, -1),
                "fuera de rango");
        assertThrows(IllegalArgumentException.class, () -> tablero.poner(null, 0, 0),
                "ficha null");
        assertThrows(IllegalArgumentException.class, () -> tablero.fichaEn(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> tablero.fichaEn(0, 3));
    }

    private void tableroNuevo(Tablero tablero) {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                assertNull(tablero.fichaEn(fila, columna));
            }
        }
        assertFalse(tablero.hayGanador());
        assertFalse(tablero.hayEmpate());
    }

    /** Fábrica mínima para crear tableros nuevos por escenario. */
    private interface FabricaDeTableros {
        Tablero crear();
    }
}
