package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.funcional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.Carta;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.FiguraDeCarta;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos.PartidaDeMemotest;

/**
 * Las reglas del memotest: un juego de tablero <b>sin fichas</b>. Hereda de
 * {@link AdministradorDePartidaDeTablero}, igual que el tateti y el reversi
 * (a través de AdministradorDePartidaConFicha): la misma plantilla de jugada,
 * los mismos turnos, el mismo historial.
 *
 * <p>Una jugada es elegir <b>una</b> carta. El turno de un jugador son dos
 * jugadas:
 * <ol>
 *   <li>Primera carta: se da vuelta. El turno sigue.</li>
 *   <li>Segunda carta: se da vuelta. Si es pareja de la primera, las dos
 *       quedan encontradas, el jugador suma un par y <b>vuelve a jugar</b>.
 *       Si no, quedan a la vista (para que todos las memoricen) y el turno pasa.</li>
 *   <li>La primera jugada del siguiente tapa las dos que quedaron a la vista.</li>
 * </ol>
 * Cuando se encontraron todos los pares, gana quien tiene más; con la misma
 * cantidad, empate.
 */
public class AdministradorDePartidaDeMemotest extends AdministradorDePartidaDeTablero<Carta, Jugador, PartidaDeMemotest> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int cantidadDeFilas;
    private final int cantidadDeColumnas;
    private final Random azar;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Memotest de 4x4 (8 pares), mezclado al azar.
     * @param jugadores al menos 2 jugadores
     */
    public AdministradorDePartidaDeMemotest(List<Jugador> jugadores) {
        this(4, 4, jugadores, new Random());
    }

    /**
     * @param cantidadDeFilas filas del tablero
     * @param cantidadDeColumnas columnas del tablero (filas x columnas par)
     * @param jugadores al menos 2 jugadores
     * @param azar generador para mezclar; con una semilla fija, las cartas salen siempre igual (sirve para probar)
     */
    public AdministradorDePartidaDeMemotest(int cantidadDeFilas, int cantidadDeColumnas, List<Jugador> jugadores, Random azar) {
        super(jugadores);
        ValidacionesUtiles.validarDistintoDeNull(azar, "azar");
        this.cantidadDeFilas = cantidadDeFilas;
        this.cantidadDeColumnas = cantidadDeColumnas;
        this.azar = azar;
        iniciarNuevaPartida();
    }

//METODOS DE AdministradorDePartidaDeTablero -----------------------------------------------------------------------

    @Override
    public String getNombreDelJuego() {
        return "Memotest";
    }

    @Override
    protected PartidaDeMemotest crearPartida(List<Jugador> jugadores) {
        return new PartidaDeMemotest(this.cantidadDeFilas, this.cantidadDeColumnas, jugadores);
    }

    /**
     * Reparte las cartas mezcladas: dos de cada figura, boca abajo.
     */
    @Override
    protected void prepararTablero(PartidaDeMemotest partida) {
        List<Carta> mazo = new ArrayList<>();
        FiguraDeCarta[] figuras = FiguraDeCarta.values();
        for (int i = 0; i < partida.getCantidadDePares(); i++) {
            mazo.add(new Carta(figuras[i]));
            mazo.add(new Carta(figuras[i]));
        }
        Collections.shuffle(mazo, this.azar);

        List<Posicion> posiciones = partida.getTablero().getPosiciones();
        for (int i = 0; i < posiciones.size(); i++) {
            partida.getTablero().colocar(posiciones.get(i), mazo.get(i));
        }
    }

    /**
     * Se puede elegir una carta boca abajo, o una de las dos que quedaron a
     * la vista del turno anterior (porque esta jugada las va a tapar).
     */
    @Override
    public boolean esJugadaValida(Posicion posicion) {
        Carta carta = getPartida().getTablero().obtener(posicion);
        return carta.estaBocaAbajo() ||
               (hayCartasParaTapar() && getPartida().getCartasDadasVuelta().contains(posicion));
    }

    @Override
    protected String explicarJugadaInvalida(Posicion posicion) {
        Carta carta = getPartida().getTablero().obtener(posicion);
        if (carta.estaEncontrada()) {
            return "Esa carta ya es parte de un par encontrado.";
        }
        return "Esa carta ya está dada vuelta: elegí otra.";
    }

    @Override
    protected void aplicarJugada(Posicion posicion, Jugador jugador) {
        PartidaDeMemotest partida = getPartida();
        if (hayCartasParaTapar()) {
            taparCartasDadasVuelta();
        }
        partida.getTablero().obtener(posicion).darVuelta();
        partida.agregarCartaDadaVuelta(posicion);

        List<Posicion> dadasVuelta = partida.getCartasDadasVuelta();
        if (dadasVuelta.size() == 2) {
            Carta primera = partida.getTablero().obtener(dadasVuelta.get(0));
            Carta segunda = partida.getTablero().obtener(dadasVuelta.get(1));
            if (primera.esParejaDe(segunda)) {
                primera.marcarEncontrada();
                segunda.marcarEncontrada();
                partida.sumarPar(jugador);
                partida.limpiarCartasDadasVuelta();
            }
            // si no son pareja, quedan las dos a la vista hasta la próxima jugada
        }
    }

    /**
     * El turno pasa sólo si se dieron vuelta dos cartas que no eran pareja.
     * Después de la primera carta, o si encontró un par, sigue el mismo jugador.
     */
    @Override
    protected void pasarAlSiguienteTurno() {
        if (hayCartasParaTapar()) {
            getPartida().avanzarTurno();
        }
    }

    @Override
    protected Jugador buscarGanador() {
        if (!seEncontraronTodosLosPares()) {
            return null;
        }
        Jugador mejor = null;
        int maximo = -1;
        boolean hayEmpate = false;
        for (Jugador jugador : getPartida().getJugadores()) {
            int pares = getPartida().getParesDe(jugador);
            if (pares > maximo) {
                mejor = jugador;
                maximo = pares;
                hayEmpate = false;
            } else if (pares == maximo) {
                hayEmpate = true;
            }
        }
        return hayEmpate ? null : mejor;
    }

    @Override
    protected boolean esEmpate() {
        return seEncontraronTodosLosPares();     // se llama sólo si no hubo ganador
    }

    @Override
    public String describirSituacion() {
        String situacion = "Pares:";
        for (Jugador jugador : getPartida().getJugadores()) {
            situacion += " " + jugador.getNombre() + " " + getPartida().getParesDe(jugador);
        }
        if (getPartida().estaEnCurso()) {
            int dadasVuelta = getPartida().getCartasDadasVuelta().size();
            if (dadasVuelta == 1) {
                situacion = "Elegí la segunda carta.   " + situacion;
            } else if (dadasVuelta == 2) {
                situacion = "No eran pareja.   " + situacion;
            }
        }
        return situacion;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * @return true si quedaron a la vista dos cartas que no eran pareja
     */
    private boolean hayCartasParaTapar() {
        return getPartida().getCartasDadasVuelta().size() == 2;
    }

    private void taparCartasDadasVuelta() {
        for (Posicion posicion : getPartida().getCartasDadasVuelta()) {
            getPartida().getTablero().obtener(posicion).tapar();
        }
        getPartida().limpiarCartasDadasVuelta();
    }

    private boolean seEncontraronTodosLosPares() {
        return getPartida().getTotalDeParesEncontrados() == getPartida().getCantidadDePares();
    }
}
