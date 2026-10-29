package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.funcional;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Tablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartidaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.datos.FichaDeTateti;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.tateti.datos.PartidaDeTateti;

/**
 * Las reglas del tateti.
 *
 * <p>Fijate todo lo que <b>no</b> está acá: el ciclo de la jugada, los turnos
 * y el historial vienen de AdministradorDePartida; que la jugada sea poner
 * la ficha en una celda vacía viene de {@link AdministradorDePartidaConFicha}.
 * Lo único propio del tateti es:
 * <ul>
 *   <li>Ganador: quien hizo la última jugada, si con ella completó
 *       {@code fichasEnLineaParaGanar} en línea (fila, columna o diagonal).</li>
 *   <li>Empate: tablero lleno sin ganador.</li>
 * </ul>
 */
public class AdministradorDePartidaDeTateti extends AdministradorDePartidaConFicha<FichaDeTateti, PartidaDeTateti> {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    /** Una dirección por cada tipo de línea; la opuesta se recorre con getOpuesta(). */
    private static final Direccion[] DIRECCIONES_DE_LINEA = {
            Direccion.DERECHA,          // horizontal
            Direccion.ABAJO,            // vertical
            Direccion.ABAJO_DERECHA,    // diagonal principal
            Direccion.ABAJO_IZQUIERDA,  // diagonal secundaria
    };

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int cantidadDeFilas;
    private final int cantidadDeColumnas;
    private final int fichasEnLineaParaGanar;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Tateti clásico: 3x3, tres en línea.
     * @param jugadores los 2 jugadores, con X y O; empieza el primero
     */
    public AdministradorDePartidaDeTateti(List<JugadorConFicha<FichaDeTateti>> jugadores) {
        this(3, 3, 3, jugadores);
    }

    /**
     * Tateti generalizado.
     * @param cantidadDeFilas filas del tablero
     * @param cantidadDeColumnas columnas del tablero
     * @param fichasEnLineaParaGanar cuántas en línea ganan
     * @param jugadores los 2 jugadores, con X y O; empieza el primero
     */
    public AdministradorDePartidaDeTateti(int cantidadDeFilas, int cantidadDeColumnas, int fichasEnLineaParaGanar,
                                         List<JugadorConFicha<FichaDeTateti>> jugadores) {
        super(jugadores);
        ValidacionesUtiles.validarFalso(jugadores.size() != 2, "El tateti se juega de a 2.");
        this.cantidadDeFilas = cantidadDeFilas;
        this.cantidadDeColumnas = cantidadDeColumnas;
        this.fichasEnLineaParaGanar = fichasEnLineaParaGanar;
        iniciarNuevaPartida();
    }

//METODOS DE AdministradorDePartida -----------------------------------------------------------------------

    @Override
    public String getNombreDelJuego() {
        return "Tateti";
    }

    @Override
    protected PartidaDeTateti crearPartida(List<JugadorConFicha<FichaDeTateti>> jugadores) {
        return new PartidaDeTateti(this.cantidadDeFilas, this.cantidadDeColumnas, this.fichasEnLineaParaGanar, jugadores);
    }

    /**
     * Sólo la última jugada puede haber formado una línea: alcanza con mirar
     * las líneas que pasan por esa posición, en vez de recorrer todo el tablero.
     */
    @Override
    protected JugadorConFicha<FichaDeTateti> buscarGanador() {
        Jugada<JugadorConFicha<FichaDeTateti>> ultima = getPartida().getUltimaJugada();
        if (ultima == null) {
            return null;
        }
        Posicion posicion = getPartida().getPosicionDeLaUltimaJugada();
        for (Direccion direccion : DIRECCIONES_DE_LINEA) {
            if (largoDeLinea(posicion, direccion) >= getPartida().getFichasEnLineaParaGanar()) {
                return ultima.getJugador();
            }
        }
        return null;
    }

    @Override
    protected boolean esEmpate() {
        return getPartida().getTablero().estaLleno();
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * @return cuántas fichas iguales a la de la posición hay en línea, contándola,
     *         hacia la dirección y hacia su opuesta
     */
    private int largoDeLinea(Posicion posicion, Direccion direccion) {
        FichaDeTateti ficha = getPartida().getTablero().obtener(posicion);
        return 1 + contarIguales(posicion, direccion, ficha) + contarIguales(posicion, direccion.getOpuesta(), ficha);
    }

    /**
     * @return cuántas fichas iguales seguidas hay desde la posición (sin contarla) hacia la dirección
     */
    private int contarIguales(Posicion desde, Direccion direccion, FichaDeTateti ficha) {
        Tablero<FichaDeTateti> tablero = getPartida().getTablero();
        int cantidad = 0;
        Posicion actual = desde.avanzar(direccion);
        while (tablero.estaDentro(actual) && ficha.equals(tablero.obtener(actual))) {
            cantidad++;
            actual = actual.avanzar(direccion);
        }
        return cantidad;
    }
}
