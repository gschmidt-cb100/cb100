package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.funcional;

import java.util.List;
import java.util.Random;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Casita;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.CartaEspanola;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.JugadaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Mano;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Mazo;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.PartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.ResultadoDeTiro;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Tiro;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartida;

/**
 * Las reglas de la casita robada. Hereda directamente de
 * {@link AdministradorDePartida}, con el movimiento {@link Tiro}: no hay
 * tablero ni posiciones. La plantilla de jugada, los turnos, el historial y
 * el ganador son los mismos que en los otros juegos.
 *
 * <ul>
 *   <li>Preparar: mezclar, poner 4 cartas en la mesa y dar 3 a cada jugador.</li>
 *   <li>Tiro válido: la carta está en la mano del jugador de turno; si va a una
 *       casita, es de otro jugador y la de arriba tiene el mismo número.</li>
 *   <li>Aplicar: robar la casita entera, o levantar todas las de la mesa con
 *       ese número, o dejar la carta en la mesa. Lo ganado va a la casita propia,
 *       con la carta tirada arriba.</li>
 *   <li>Si nadie tiene cartas en la mano y quedan en el mazo, se reparten 3 más.</li>
 *   <li>Fin: mazo y manos vacías. Gana la casita más grande; si hay empate en el máximo, empate.</li>
 * </ul>
 */
public class AdministradorDePartidaDeCasitaRobada extends AdministradorDePartida<Jugador, Tiro, PartidaDeCasitaRobada> {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    public static final int CARTAS_POR_MANO = 3;
    public static final int CARTAS_INICIALES_EN_LA_MESA = 4;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Random azar;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param jugadores al menos 2 jugadores
     */
    public AdministradorDePartidaDeCasitaRobada(List<Jugador> jugadores) {
        this(jugadores, new Random());
    }

    /**
     * @param jugadores al menos 2 jugadores
     * @param azar generador para mezclar; con una semilla fija, las cartas salen siempre igual (sirve para probar)
     */
    public AdministradorDePartidaDeCasitaRobada(List<Jugador> jugadores, Random azar) {
        super(jugadores);
        ValidacionesUtiles.validarDistintoDeNull(azar, "azar");
        ValidacionesUtiles.validarFalso(CARTAS_INICIALES_EN_LA_MESA + CARTAS_POR_MANO * jugadores.size() > 40,
                "Hay demasiados jugadores para repartir.");
        this.azar = azar;
        iniciarNuevaPartida();
    }

//METODOS DE AdministradorDePartida -----------------------------------------------------------------------

    @Override
    public String getNombreDelJuego() {
        return "Casita robada";
    }

    @Override
    protected PartidaDeCasitaRobada crearPartida(List<Jugador> jugadores) {
        return new PartidaDeCasitaRobada(jugadores, Mazo.crearMezclado(this.azar), CARTAS_POR_MANO);
    }

    /**
     * 4 cartas a la mesa y 3 a cada jugador.
     */
    @Override
    protected void prepararPartida(PartidaDeCasitaRobada partida) {
        for (int i = 0; i < CARTAS_INICIALES_EN_LA_MESA; i++) {
            partida.getMesa().poner(partida.getMazo().sacar());
        }
        repartir(partida);
    }

    @Override
    public boolean esJugadaValida(Tiro tiro) {
        Jugador jugador = getPartida().getJugadorDeTurno();
        if (!getPartida().getManoDe(jugador).contiene(tiro.getCarta())) {
            return false;
        }
        if (tiro.esALaMesa()) {
            return true;
        }
        Jugador duenio = tiro.getDuenioDeLaCasita();
        return !duenio.equals(jugador) &&
               getPartida().getJugadores().contains(duenio) &&
               getPartida().getCasitaDe(duenio).sePuedeRobarCon(tiro.getCarta());
    }

    @Override
    protected String explicarJugadaInvalida(Tiro tiro) {
        Jugador jugador = getPartida().getJugadorDeTurno();
        if (!getPartida().getManoDe(jugador).contiene(tiro.getCarta())) {
            return "El " + tiro.getCarta() + " no está en tu mano.";
        }
        if (tiro.getDuenioDeLaCasita().equals(jugador)) {
            return "No te podés robar tu propia casita.";
        }
        if (getPartida().getCasitaDe(tiro.getDuenioDeLaCasita()).estaVacia()) {
            return tiro.getDuenioDeLaCasita().getNombre() + " no tiene casita para robar.";
        }
        return "Para robar, la de arriba de la casita tiene que ser un " + tiro.getCarta().getNumero() + ".";
    }

    /**
     * Se llama antes de aplicar el tiro: describe lo que va a pasar.
     */
    @Override
    protected Jugada<Jugador> crearJugada(Jugador jugador, Tiro tiro, int numero) {
        ResultadoDeTiro resultado;
        int cartasGanadas;
        if (!tiro.esALaMesa()) {
            resultado = ResultadoDeTiro.ROBO;
            cartasGanadas = getPartida().getCasitaDe(tiro.getDuenioDeLaCasita()).getCantidad() + 1;
        } else {
            int coinciden = getPartida().getMesa().getLasQueCoincidenCon(tiro.getCarta()).size();
            resultado = (coinciden > 0) ? ResultadoDeTiro.LEVANTO : ResultadoDeTiro.DEJO_EN_LA_MESA;
            cartasGanadas = (coinciden > 0) ? coinciden + 1 : 0;
        }
        return new JugadaDeCasitaRobada(jugador, tiro, resultado, cartasGanadas, numero);
    }

    @Override
    protected void aplicarJugada(Tiro tiro, Jugador jugador) {
        PartidaDeCasitaRobada partida = getPartida();
        CartaEspanola carta = tiro.getCarta();
        Casita miCasita = partida.getCasitaDe(jugador);
        partida.getManoDe(jugador).quitar(carta);

        if (!tiro.esALaMesa()) {
            miCasita.agregarTodas(partida.getCasitaDe(tiro.getDuenioDeLaCasita()).robarTodas());
            miCasita.agregar(carta);
        } else {
            List<CartaEspanola> levantadas = partida.getMesa().levantarLasQueCoincidenCon(carta);
            if (levantadas.isEmpty()) {
                partida.getMesa().poner(carta);
            } else {
                miCasita.agregarTodas(levantadas);
                miCasita.agregar(carta);
            }
        }

        if (partida.todasLasManosEstanVacias() && !partida.getMazo().estaVacio()) {
            repartir(partida);
        }
    }

    @Override
    protected Jugador buscarGanador() {
        if (!estaTerminada()) {
            return null;
        }
        Jugador mejor = null;
        int maximo = -1;
        boolean hayEmpate = false;
        for (Jugador jugador : getPartida().getJugadores()) {
            int cartas = getPartida().getCasitaDe(jugador).getCantidad();
            if (cartas > maximo) {
                mejor = jugador;
                maximo = cartas;
                hayEmpate = false;
            } else if (cartas == maximo) {
                hayEmpate = true;
            }
        }
        return hayEmpate ? null : mejor;
    }

    @Override
    protected boolean esEmpate() {
        return estaTerminada();          // se llama sólo si no hubo ganador
    }

    @Override
    public String describirSituacion() {
        String situacion = "Casitas:";
        for (Jugador jugador : getPartida().getJugadores()) {
            situacion += " " + jugador.getNombre() + " " + getPartida().getCasitaDe(jugador).getCantidad();
        }
        return situacion + "   Mazo: " + getPartida().getMazo().getCantidad();
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * Da cartas a cada jugador, en orden, hasta llenar las manos o vaciar el mazo.
     */
    private void repartir(PartidaDeCasitaRobada partida) {
        for (int i = 0; i < CARTAS_POR_MANO; i++) {
            for (Jugador jugador : partida.getJugadores()) {
                Mano mano = partida.getManoDe(jugador);
                if (!partida.getMazo().estaVacio() && !mano.estaLlena()) {
                    mano.recibir(partida.getMazo().sacar());
                }
            }
        }
    }

    /**
     * @return true si ya no se puede jugar: mazo vacío y nadie tiene cartas
     */
    private boolean estaTerminada() {
        return getPartida().getMazo().estaVacio() && getPartida().todasLasManosEstanVacias();
    }
}
