package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.TableroMatriz;

/**
 * Partida de memotest (dato): una {@link PartidaDeTablero} <b>sin fichas</b>. El
 * tablero tiene cartas ({@code Tablero<Carta>}) y los jugadores son
 * {@link Jugador} comunes: hereda de PartidaDeTablero, no de PartidaConFicha.
 *
 * <p>Agrega lo propio del memotest:
 * <ul>
 *   <li>las cartas que el jugador de turno dio vuelta y todavía no se taparon (0, 1 o 2);</li>
 *   <li>cuántos pares encontró cada jugador en esta partida.</li>
 * </ul>
 *
 * Invariantes:
 *  - filas x columnas es par, y no hay más pares que figuras.
 *  - 0 <= cartas dadas vuelta <= 2.
 *  - paresEncontrados tiene un contador por jugador, en orden de turno.
 */
public class PartidaDeMemotest extends PartidaDeTablero<Carta, Jugador> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final List<Posicion> cartasDadasVuelta;
    private final int[] paresEncontrados;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una partida con el tablero vacío (el administrador reparte las cartas).
     * @param filas filas del tablero
     * @param columnas columnas del tablero
     * @param jugadores al menos 2 jugadores
     * @throws IllegalArgumentException si la cantidad de celdas es impar o hay más pares que figuras
     */
    public PartidaDeMemotest(int filas, int columnas, List<Jugador> jugadores) {
        super(new TableroMatriz<>(filas, columnas), jugadores);
        ValidacionesUtiles.validarFalso((filas * columnas) % 2 != 0, "El tablero del memotest tiene que tener una cantidad par de celdas.");
        ValidacionesUtiles.validarFalso((filas * columnas) / 2 > FiguraDeCarta.values().length,
                "No hay figuras suficientes para " + (filas * columnas) / 2 + " pares.");
        this.cartasDadasVuelta = new ArrayList<>();
        this.paresEncontrados = new int[jugadores.size()];
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param posicion posición de una carta que se acaba de dar vuelta
     * @throws IllegalStateException si ya hay 2 cartas dadas vuelta
     */
    public void agregarCartaDadaVuelta(Posicion posicion) {
        ValidacionesUtiles.validarDistintoDeNull(posicion, "posicion");
        ValidacionesUtiles.validarEstadoFalso(this.cartasDadasVuelta.size() == 2, "Ya hay 2 cartas dadas vuelta.");
        this.cartasDadasVuelta.add(posicion);
    }

    /**
     * Olvida las cartas dadas vuelta (porque se taparon o porque eran un par).
     */
    public void limpiarCartasDadasVuelta() {
        this.cartasDadasVuelta.clear();
    }

    /**
     * @param jugador jugador de esta partida que encontró un par
     */
    public void sumarPar(Jugador jugador) {
        this.paresEncontrados[getOrdenDe(jugador)]++;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return una copia de las posiciones de las cartas dadas vuelta en este turno
     */
    public List<Posicion> getCartasDadasVuelta() {
        return new ArrayList<>(this.cartasDadasVuelta);
    }

    /**
     * @param jugador jugador de esta partida
     * @return cuántos pares encontró
     */
    public int getParesDe(Jugador jugador) {
        return this.paresEncontrados[getOrdenDe(jugador)];
    }

    /**
     * @return cuántos pares se encontraron entre todos
     */
    public int getTotalDeParesEncontrados() {
        int total = 0;
        for (int pares : this.paresEncontrados) {
            total += pares;
        }
        return total;
    }

    /**
     * @return cuántos pares hay en el tablero
     */
    public int getCantidadDePares() {
        return getTablero().getCantidadDeFilas() * getTablero().getCantidadDeColumnas() / 2;
    }
}
