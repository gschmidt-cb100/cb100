package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.funcional;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.JugadorConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Tablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartidaConFicha;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.datos.FichaDeReversi;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.reversi.datos.PartidaDeReversi;

/**
 * Las reglas del reversi. Hereda de {@link AdministradorDePartidaConFicha}
 * y <b>extiende con super</b> dos de sus pasos: una jugada válida es una celda
 * vacía que además encierra fichas, y aplicar es poner la ficha y además dar
 * vuelta las encerradas. También redefine ganchos: preparar el tablero
 * (4 fichas al centro), pasar el turno (si el siguiente no puede jugar,
 * pasa) y marcar las jugadas válidas en la pantalla.
 *
 * <ul>
 *   <li>Jugada válida: celda vacía que encierra, en al menos una de las 8
 *       direcciones, una o más fichas contrarias seguidas entre la nueva
 *       ficha y otra propia.</li>
 *   <li>Aplicar: poner la ficha y dar vuelta todas las fichas encerradas.</li>
 *   <li>Turno: si el siguiente no tiene jugadas, pasa y vuelve a jugar el mismo.</li>
 *   <li>Fin: cuando ninguno de los dos puede jugar (o el tablero se llenó).
 *       Gana quien tiene más fichas; con igual cantidad, empate.</li>
 * </ul>
 */
public class AdministradorDePartidaDeReversi extends AdministradorDePartidaConFicha<FichaDeReversi, PartidaDeReversi> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int lado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Reversi clásico de 8x8.
     * @param jugadores los 2 jugadores, con NEGRA y BLANCA; empieza el primero (por regla, las negras)
     */
    public AdministradorDePartidaDeReversi(List<JugadorConFicha<FichaDeReversi>> jugadores) {
        this(8, jugadores);
    }

    /**
     * @param lado lado del tablero, par y al menos 4
     * @param jugadores los 2 jugadores, con NEGRA y BLANCA; empieza el primero
     */
    public AdministradorDePartidaDeReversi(int lado, List<JugadorConFicha<FichaDeReversi>> jugadores) {
        super(jugadores);
        ValidacionesUtiles.validarFalso(jugadores.size() != 2, "El reversi se juega de a 2.");
        this.lado = lado;
        iniciarNuevaPartida();
    }

//METODOS DE AdministradorDePartida -----------------------------------------------------------------------

    @Override
    public String getNombreDelJuego() {
        return "Reversi";
    }

    @Override
    protected PartidaDeReversi crearPartida(List<JugadorConFicha<FichaDeReversi>> jugadores) {
        return new PartidaDeReversi(this.lado, jugadores);
    }

    /**
     * Las 4 fichas del centro, en diagonal: blancas en (m, m) y (m+1, m+1), negras en las otras dos.
     */
    @Override
    protected void prepararTablero(PartidaDeReversi partida) {
        Tablero<FichaDeReversi> tablero = partida.getTablero();
        int medio = this.lado / 2;
        tablero.colocar(new Posicion(medio, medio), FichaDeReversi.BLANCA);
        tablero.colocar(new Posicion(medio, medio + 1), FichaDeReversi.NEGRA);
        tablero.colocar(new Posicion(medio + 1, medio), FichaDeReversi.NEGRA);
        tablero.colocar(new Posicion(medio + 1, medio + 1), FichaDeReversi.BLANCA);
    }

    /**
     * Válida si es válida para AdministradorDePartidaConFicha (celda vacía)
     * y además encierra al menos una ficha contraria.
     */
    @Override
    public boolean esJugadaValida(Posicion posicion) {
        return super.esJugadaValida(posicion) &&
               !buscarFichasEncerradas(posicion, getPartida().getJugadorDeTurno().getFicha()).isEmpty();
    }

    @Override
    protected String explicarJugadaInvalida(Posicion posicion) {
        if (!getPartida().getTablero().estaVacio(posicion)) {
            return super.explicarJugadaInvalida(posicion);
        }
        return "En " + posicion + " no se encierra ninguna ficha contraria.";
    }

    /**
     * Pone la ficha (lo hace super) y además da vuelta las encerradas.
     */
    @Override
    protected void aplicarJugada(Posicion posicion, JugadorConFicha<FichaDeReversi> jugador) {
        FichaDeReversi ficha = jugador.getFicha();
        List<Posicion> aDarVuelta = buscarFichasEncerradas(posicion, ficha);   // antes de colocar
        super.aplicarJugada(posicion, jugador);
        for (Posicion encerrada : aDarVuelta) {
            getPartida().getTablero().reemplazar(encerrada, ficha);
        }
        getPartida().limpiarPase();
    }

    @Override
    protected JugadorConFicha<FichaDeReversi> buscarGanador() {
        if (!estaTerminada()) {
            return null;
        }
        int negras = getPartida().getTablero().contar(FichaDeReversi.NEGRA);
        int blancas = getPartida().getTablero().contar(FichaDeReversi.BLANCA);
        if (negras > blancas) {
            return getPartida().getJugadorCon(FichaDeReversi.NEGRA);
        }
        if (blancas > negras) {
            return getPartida().getJugadorCon(FichaDeReversi.BLANCA);
        }
        return null;
    }

    @Override
    protected boolean esEmpate() {
        return estaTerminada();   // se llama sólo si no hubo ganador: terminada y sin ganador es empate
    }

    /**
     * Pasa al siguiente; si ese no tiene jugadas, pasa y vuelve al mismo jugador.
     * (Si ninguno tuviera jugadas, la partida ya habría terminado antes de llegar acá.)
     */
    @Override
    protected void pasarAlSiguienteTurno() {
        PartidaDeReversi partida = getPartida();
        partida.avanzarTurno();
        if (!tieneJugadas(partida.getJugadorDeTurno().getFicha())) {
            partida.registrarPase(partida.getJugadorDeTurno());
            partida.avanzarTurno();
        }
    }

    @Override
    public String describirSituacion() {
        Tablero<FichaDeReversi> tablero = getPartida().getTablero();
        String situacion = "● " + tablero.contar(FichaDeReversi.NEGRA) + "  ○ " + tablero.contar(FichaDeReversi.BLANCA);
        if (getPartida().getJugadorQuePaso() != null) {
            situacion = getPartida().getJugadorQuePaso().getNombre() + " pasó.   " + situacion;
        }
        return situacion;
    }

    @Override
    public boolean debeMarcarJugadasValidas() {
        return true;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * @return true si la partida no puede seguir: tablero lleno o ninguno de los dos tiene jugadas
     */
    private boolean estaTerminada() {
        return getPartida().getTablero().estaLleno() ||
               (!tieneJugadas(FichaDeReversi.NEGRA) && !tieneJugadas(FichaDeReversi.BLANCA));
    }

    /**
     * @return true si la ficha tiene al menos una jugada válida en todo el tablero
     */
    private boolean tieneJugadas(FichaDeReversi ficha) {
        for (Posicion posicion : getPartida().getTablero().getPosiciones()) {
            if (puedeJugarEn(posicion, ficha)) {
                return true;
            }
        }
        return false;
    }

    private boolean puedeJugarEn(Posicion posicion, FichaDeReversi ficha) {
        Tablero<FichaDeReversi> tablero = getPartida().getTablero();
        return tablero.estaDentro(posicion) && tablero.estaVacio(posicion) &&
               !buscarFichasEncerradas(posicion, ficha).isEmpty();
    }

    /**
     * @return todas las fichas contrarias que quedarían encerradas al poner la ficha en la posición
     */
    private List<Posicion> buscarFichasEncerradas(Posicion posicion, FichaDeReversi ficha) {
        List<Posicion> encerradas = new ArrayList<>();
        for (Direccion direccion : Direccion.values()) {
            encerradas.addAll(buscarFichasEncerradas(posicion, ficha, direccion));
        }
        return encerradas;
    }

    /**
     * Avanza desde la posición mientras haya fichas contrarias. Si al final hay
     * una propia, las contrarias recorridas quedan encerradas; si no (borde o
     * celda vacía), no se encierra nada en esa dirección.
     */
    private List<Posicion> buscarFichasEncerradas(Posicion posicion, FichaDeReversi ficha, Direccion direccion) {
        Tablero<FichaDeReversi> tablero = getPartida().getTablero();
        List<Posicion> recorridas = new ArrayList<>();
        Posicion actual = posicion.avanzar(direccion);
        while (tablero.estaDentro(actual) && tablero.obtener(actual) == ficha.getContraria()) {
            recorridas.add(actual);
            actual = actual.avanzar(direccion);
        }
        if (tablero.estaDentro(actual) && tablero.obtener(actual) == ficha) {
            return recorridas;
        }
        return new ArrayList<>();
    }
}
