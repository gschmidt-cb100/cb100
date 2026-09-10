package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests del tateti, y de paso un catálogo de <b>cómo se usa cada assert</b>
 * de JUnit 5. Cada test dice en su nombre qué regla verifica, y el comentario
 * de arriba dice qué assert enseña y cuándo conviene usarlo.
 *
 * <h2>Las reglas de oro</h2>
 * <ul>
 *   <li>Un test = una idea. Si falla, el nombre ya dice qué se rompió.</li>
 *   <li><b>Primero el esperado, después el real</b>: {@code assertEquals(esperado, real)}.
 *       Si los invertís, el mensaje de error te miente.</li>
 *   <li>El mensaje opcional va al final y se muestra sólo si falla. Úsalo
 *       cuando el valor solo no alcanza para entender qué pasó.</li>
 *   <li>Nada de {@code System.out.println}: el modelo devuelve datos y el
 *       test los compara.</li>
 * </ul>
 *
 * <h2>Lo que queda afuera, y por qué</h2>
 * {@code assertIterableEquals} y {@code assertLinesMatch} trabajan sobre
 * {@code List}, que todavía no vimos. {@code assertTimeoutPreemptively} corta
 * el test en otro hilo, y los hilos son de otra materia.
 */
@DisplayName("Tateti")
class TatetiTest {

    private Tateti tateti;

    /**
     * {@code @BeforeEach} corre antes de CADA test: cada uno arranca con un
     * tablero nuevo y vacío, y ningún test depende de lo que hizo otro.
     */
    @BeforeEach
    void tableroNuevo() {
        tateti = new Tateti(3, 3);
    }

    // ==================================================================
    //  Estado inicial
    // ==================================================================

    @Test
    @DisplayName("un tateti nuevo está en JUGANDO, vacío, y le toca a X")
    void elTatetiNuevo() {
        // assertEquals: el más usado. Compara con equals(). Sirve para
        // enums, Strings, enteros, objetos con equals bien definido.
        assertEquals(EstadoDeTateti.JUGANDO, tateti.getEstado());
        assertEquals(3, tateti.getCantidadDeFilas());
        assertEquals(3, tateti.getCantidadDeColumnas());
        assertEquals(0, tateti.getCantidadDeJugadas());

        // assertTrue / assertFalse: para booleanos. Preferilos a
        // assertEquals(true, ...), que dice lo mismo pero se lee peor.
        assertTrue(tateti.estaVacio(2, 2));
        assertFalse(tateti.estaFinalizado());
        assertFalse(tateti.hayGanador());
        assertFalse(tateti.hayEmpate());

        // assertNull / assertNotNull: para "todavía no hay" y "ya hay".
        assertNull(tateti.obtenerGanador(), "sin jugadas no puede haber ganador");
        assertNull(tateti.getUltimaFicha(), "nadie jugó todavía");
        assertNotNull(tateti.getProximaFicha(), "pero alguien tiene que jugar");
    }

    @Test
    @DisplayName("las fichas se alternan: X, O, X, O")
    void lasFichasSeAlternan() {
        // assertSame / assertNotSame: comparan IDENTIDAD (==), no contenido.
        // Con un enum son lo mismo, porque cada constante existe una sola
        // vez. Con Strings u objetos, equals y == pueden dar distinto: ver
        // el escenario 9 de stack y heap.
        assertSame(Ficha.X, tateti.getProximaFicha());
        tateti.colocar(1, 1);
        assertSame(Ficha.O, tateti.getProximaFicha());
        assertSame(Ficha.X, tateti.getUltimaFicha());
        assertNotSame(tateti.getUltimaFicha(), tateti.getProximaFicha());

        // assertNotEquals: cuando lo importante es que dos cosas difieran.
        assertNotEquals(Ficha.X.alternar(), Ficha.X);
        assertEquals(Ficha.X, Ficha.X.alternar().alternar());
    }

    // ==================================================================
    //  Jugadas
    // ==================================================================

    @Test
    @DisplayName("colocar guarda la ficha y la devuelve obtener")
    void colocarYObtener() {
        // assertDoesNotThrow: para decir explícitamente "esto es válido".
        // Sin él, una excepción también haría fallar el test, pero el
        // mensaje sería menos claro sobre qué se estaba probando.
        assertDoesNotThrow(() -> tateti.colocar(2, 2, Ficha.X));

        assertEquals(Ficha.X, tateti.obtener(2, 2));
        assertFalse(tateti.estaVacio(2, 2));
        assertTrue(tateti.estaVacio(1, 1), "las demás siguen vacías");
        assertEquals(1, tateti.getCantidadDeJugadas());
    }

    @Test
    @DisplayName("la fila 1 se lee de izquierda a derecha")
    void unaFilaCompleta() {
        tateti.colocar(1, 1, Ficha.X);
        tateti.colocar(2, 1, Ficha.O);
        tateti.colocar(1, 2, Ficha.X);
        tateti.colocar(2, 2, Ficha.O);

        // assertArrayEquals: compara arreglos ELEMENTO por elemento. Un
        // assertEquals entre dos arreglos compara referencias y siempre
        // falla, aunque tengan lo mismo adentro.
        Ficha[] fila1 = {tateti.obtener(1, 1), tateti.obtener(1, 2), tateti.obtener(1, 3)};
        assertArrayEquals(new Ficha[]{Ficha.X, Ficha.X, null}, fila1);
    }

    @Test
    @DisplayName("después de 4 jugadas, la mitad son de X")
    void laMitadDeLasJugadasSonDeX() {
        tateti.colocar(1, 1);
        tateti.colocar(1, 2);
        tateti.colocar(2, 1);
        tateti.colocar(2, 2);

        int deX = 0;
        for (int f = 1; f <= 3; f++) {
            for (int c = 1; c <= 3; c++) {
                if (tateti.obtener(f, c) == Ficha.X) {
                    deX++;
                }
            }
        }
        // assertEquals con DELTA: obligatorio para double. Dos cálculos con
        // decimales casi nunca dan el mismo bit, así que se compara "cerca
        // de", con una tolerancia.
        double proporcion = deX / (double) tateti.getCantidadDeJugadas();
        assertEquals(0.5, proporcion, 0.0001);
    }

    // ==================================================================
    //  Fin del juego
    // ==================================================================

    @Nested
    @DisplayName("cuando alguien gana")
    class CuandoAlguienGana {

        @Test
        @DisplayName("X completa la primera fila")
        void ganaX() {
            tateti.colocar(1, 1, Ficha.X);
            tateti.colocar(2, 1, Ficha.O);
            tateti.colocar(1, 2, Ficha.X);
            tateti.colocar(2, 2, Ficha.O);
            EstadoDeTateti estado = tateti.colocar(1, 3, Ficha.X);

            // assertAll: agrupa varias verificaciones y las corre TODAS,
            // aunque la primera falle. Sin assertAll, el test se corta en
            // la primera que falla y no te enterás de las demás.
            assertAll("estado final de la partida",
                    () -> assertEquals(EstadoDeTateti.GANO_X, estado),
                    () -> assertSame(estado, tateti.getEstado(), "colocar devuelve el mismo estado que guarda"),
                    () -> assertTrue(tateti.hayGanador()),
                    () -> assertTrue(tateti.estaFinalizado()),
                    () -> assertFalse(tateti.hayEmpate()),
                    () -> assertEquals(Ficha.X, tateti.obtenerGanador()),
                    () -> assertNull(tateti.getProximaFicha(), "terminado el juego, no le toca a nadie")
            );
        }

        @Test
        @DisplayName("O completa la columna 2 con el turno automático")
        void ganaO() {
            tateti.colocar(1, 1);   // X
            tateti.colocar(1, 2);   // O
            tateti.colocar(2, 1);   // X
            tateti.colocar(2, 2);   // O
            tateti.colocar(3, 3);   // X
            EstadoDeTateti estado = tateti.colocar(3, 2);   // O

            assertEquals(EstadoDeTateti.GANO_O, estado);
            assertEquals(Ficha.O, tateti.obtenerGanador());
            assertTrue(estado.esGanadorO());
            assertFalse(estado.esGanadorX());
        }

        /**
         * {@code @ParameterizedTest} corre el MISMO test una vez por fila de
         * datos. Acá, una vez por cada una de las 8 líneas ganadoras. Sin
         * esto serían 8 tests copiados, o un ciclo adentro de un test, que
         * al fallar no dice cuál línea se rompió.
         */
        @ParameterizedTest(name = "X gana con la línea ({0},{1}) ({2},{3}) ({4},{5})")
        @CsvSource({
                "1,1, 1,2, 1,3",    // fila 1
                "2,1, 2,2, 2,3",    // fila 2
                "3,1, 3,2, 3,3",    // fila 3
                "1,1, 2,1, 3,1",    // columna 1
                "1,2, 2,2, 3,2",    // columna 2
                "1,3, 2,3, 3,3",    // columna 3
                "1,1, 2,2, 3,3",    // diagonal principal
                "1,3, 2,2, 3,1",    // diagonal secundaria
        })
        void xGanaConCadaLinea(int f1, int c1, int f2, int c2, int f3, int c3) {
            boolean[][] esDeLaLinea = new boolean[4][4];
            esDeLaLinea[f1][c1] = esDeLaLinea[f2][c2] = esDeLaLinea[f3][c3] = true;

            // O juega en las dos primeras casillas libres que no son de la
            // línea. Con dos fichas no puede ganar, así que no molesta.
            int[][] jugadasDeO = new int[2][];
            int encontradas = 0;
            for (int f = 1; f <= 3 && encontradas < 2; f++) {
                for (int c = 1; c <= 3 && encontradas < 2; c++) {
                    if (!esDeLaLinea[f][c]) {
                        jugadasDeO[encontradas++] = new int[]{f, c};
                    }
                }
            }

            tateti.colocar(f1, c1, Ficha.X);
            tateti.colocar(jugadasDeO[0][0], jugadasDeO[0][1], Ficha.O);
            tateti.colocar(f2, c2, Ficha.X);
            tateti.colocar(jugadasDeO[1][0], jugadasDeO[1][1], Ficha.O);
            assertEquals(EstadoDeTateti.JUGANDO, tateti.getEstado(), "con dos fichas nadie gana");

            EstadoDeTateti estado = tateti.colocar(f3, c3, Ficha.X);

            assertEquals(EstadoDeTateti.GANO_X, estado);
        }

        @Test
        @DisplayName("una partida termina en a lo sumo 9 jugadas")
        void unaPartidaTerminaEnALoSumoNueveJugadas() {
            int[][] jugadas = {{1, 1}, {1, 2}, {1, 3}, {2, 2}, {2, 1}, {2, 3}, {3, 2}, {3, 1}, {3, 3}};
            for (int[] jugada : jugadas) {
                if (tateti.colocar(jugada[0], jugada[1]).esFinalizado()) {
                    return;                     // terminó: el test pasa
                }
            }
            // fail(): para un punto del código al que NO se debería llegar.
            // Si el ciclo termina sin que el juego haya finalizado, algo
            // está mal en la detección de fin.
            fail("se jugaron las 9 casillas y el tateti sigue en " + tateti.getEstado());
        }
    }

    @Nested
    @DisplayName("cuando se empata")
    class CuandoSeEmpata {

        @Test
        @DisplayName("el tablero lleno sin línea es EMPATE")
        void empate() {
            // X O X
            // X O O
            // O X X
            int[][] jugadas = {{1, 1}, {1, 2}, {1, 3}, {2, 2}, {2, 1}, {2, 3}, {3, 2}, {3, 1}, {3, 3}};
            EstadoDeTateti estado = null;
            for (int[] jugada : jugadas) {
                estado = tateti.colocar(jugada[0], jugada[1]);
            }

            assertEquals(EstadoDeTateti.EMPATE, estado);
            assertTrue(tateti.hayEmpate());
            assertFalse(tateti.hayGanador());
            assertNull(tateti.obtenerGanador());
            assertTrue(tateti.estaFinalizado());
            assertEquals(9, tateti.getCantidadDeJugadas());
        }
    }

    // ==================================================================
    //  Validaciones
    // ==================================================================

    @Nested
    @DisplayName("validaciones")
    class Validaciones {

        @Test
        @DisplayName("no se puede empezar con O")
        void noSePuedeEmpezarConO() {
            // assertThrows: verifica que se lance una excepción del tipo dado
            // y la DEVUELVE, así se puede seguir preguntando sobre ella. El
            // código que debería fallar va adentro de una lambda; si se
            // escribiera directo, la excepción saltaría antes del assert.
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                    () -> tateti.colocar(1, 1, Ficha.O));

            assertTrue(error.getMessage().contains("Le toca a X"), error.getMessage());
            assertEquals(0, tateti.getCantidadDeJugadas(), "la jugada rechazada no cuenta");
        }

        @Test
        @DisplayName("no se puede jugar dos veces seguidas con la misma ficha")
        void noSePuedeRepetirFicha() {
            tateti.colocar(1, 1, Ficha.X);

            assertThrows(IllegalArgumentException.class, () -> tateti.colocar(1, 2, Ficha.X));
            assertTrue(tateti.estaVacio(1, 2), "la jugada rechazada no dejó ficha");
        }

        @Test
        @DisplayName("no se puede pisar una posición ocupada")
        void noSePuedePisar() {
            tateti.colocar(1, 1, Ficha.X);

            IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                    () -> tateti.colocar(1, 1, Ficha.O));

            assertTrue(error.getMessage().contains("ocupada"), error.getMessage());
            assertEquals(Ficha.X, tateti.obtener(1, 1), "la ficha original sigue ahí");
        }

        /**
         * {@code @ValueSource} es la forma corta de {@code @CsvSource} para un
         * solo parámetro: el mismo test con cada valor de la lista.
         */
        @ParameterizedTest(name = "la fila {0} está fuera del tablero de 3x3")
        @ValueSource(ints = {0, 4, -1, 100})
        void fueraDeRango(int fila) {
            assertThrows(IllegalArgumentException.class, () -> tateti.colocar(fila, 1, Ficha.X));
            assertThrows(IllegalArgumentException.class, () -> tateti.obtener(fila, 1));
            assertThrows(IllegalArgumentException.class, () -> tateti.estaVacio(1, fila));
        }

        @Test
        @DisplayName("la ficha no puede ser nula")
        void fichaNula() {
            assertThrows(IllegalArgumentException.class, () -> tateti.colocar(1, 1, null));
        }

        @Test
        @DisplayName("terminado el juego, no se puede seguir")
        void noSeJuegaDespuesDeTerminar() {
            tateti.colocar(1, 1, Ficha.X);
            tateti.colocar(2, 1, Ficha.O);
            tateti.colocar(1, 2, Ficha.X);
            tateti.colocar(2, 2, Ficha.O);
            tateti.colocar(1, 3, Ficha.X);           // gana X

            assertThrows(IllegalArgumentException.class, () -> tateti.colocar(3, 3, Ficha.O));
            assertThrows(IllegalArgumentException.class, () -> tateti.colocar(3, 3));
            // Mensaje "perezoso": la lambda se evalúa sólo si el assert falla.
            // Conviene cuando armar el mensaje es caro, como acá con toString().
            assertEquals(5, tateti.getCantidadDeJugadas(), () -> "no se sumó la jugada rechazada:\n" + tateti);
        }

        @ParameterizedTest(name = "un tablero de {0} filas no se puede crear")
        @ValueSource(ints = {0, -1, -10})
        void tableroInvalido(int filas) {
            assertThrows(IllegalArgumentException.class, () -> new Tateti(filas, 3));
            assertThrows(IllegalArgumentException.class, () -> new Tateti(3, filas));
        }
    }

    // ==================================================================
    //  Otros tableros
    // ==================================================================

    @Test
    @DisplayName("en un 4x4, X gana por la diagonal secundaria")
    void diagonalSecundariaEnCuatroPorCuatro() {
        Tateti grande = new Tateti(4, 4);
        grande.colocar(1, 4);   // X
        grande.colocar(1, 1);   // O
        grande.colocar(2, 3);   // X
        grande.colocar(1, 2);   // O
        grande.colocar(3, 2);   // X
        grande.colocar(1, 3);   // O
        EstadoDeTateti estado = grande.colocar(4, 1);   // X: (1,4) (2,3) (3,2) (4,1)

        assertEquals(EstadoDeTateti.GANO_X, estado);
    }

    @Test
    @DisplayName("buscar ganador en un tablero de 200x200 tarda menos de un segundo")
    void buscarGanadorEsRapido() {
        Tateti enorme = new Tateti(200, 200);
        enorme.colocar(1, 1);
        enorme.colocar(200, 200);

        // assertTimeout: falla si el bloque tarda más de lo indicado. Sirve
        // para atrapar un algoritmo que "anda" pero se volvió cuadrático.
        Ficha ganador = assertTimeout(Duration.ofSeconds(1), enorme::obtenerGanador);

        assertNull(ganador);
    }

    @Test
    @DisplayName("el tateti usa un Tablero, y TableroMatriz es uno")
    void elTableroEsUnaImplementacionDeLaInterfaz() {
        TableroMatriz<Ficha> tablero = new TableroMatriz<>(3, 3);

        // assertInstanceOf: verifica el tipo y DEVUELVE el objeto ya casteado.
        // Es la forma limpia de "assertTrue(x instanceof Y); Y y = (Y) x;".
        Tablero<Ficha> comoInterfaz = assertInstanceOf(Tablero.class, tablero);

        assertEquals(3, comoInterfaz.getCantidadDeFilas());
    }

    @Test
    @DisplayName("toString dibuja el tablero con sus índices y el estado")
    void elToStringDibujaElTablero() {
        tateti.colocar(1, 1, Ficha.X);
        tateti.colocar(2, 2, Ficha.O);

        String dibujo = tateti.toString();

        assertTrue(dibujo.startsWith("   1 2 3"), "encabezado de columnas:\n" + dibujo);
        assertTrue(dibujo.contains(" 1 X . ."), "fila 1:\n" + dibujo);
        assertTrue(dibujo.contains(" 2 . O ."), "fila 2:\n" + dibujo);
        assertTrue(dibujo.endsWith("Estado: JUGANDO | le toca a X"), "pie:\n" + dibujo);
    }

    /**
     * {@code @Disabled} deja el test escrito pero no lo corre, y el informe
     * lo muestra como "salteado" con el motivo. Es la forma correcta de
     * dejar un pendiente: mejor que comentarlo, que se pierde.
     */
    @Test
    @Disabled("pendiente: definir si en un tablero no cuadrado la diagonal corta cuenta como línea")
    @DisplayName("en un 3x5 la diagonal de largo 3 gana")
    void diagonalEnTableroNoCuadrado() {
        Tateti rectangular = new Tateti(3, 5);
        rectangular.colocar(1, 1);   // X
        rectangular.colocar(1, 2);   // O
        rectangular.colocar(2, 2);   // X
        rectangular.colocar(1, 3);   // O
        assertEquals(EstadoDeTateti.GANO_X, rectangular.colocar(3, 3));
    }
}
