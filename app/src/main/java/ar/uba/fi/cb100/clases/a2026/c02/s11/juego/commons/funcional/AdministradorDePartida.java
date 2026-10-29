package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.EstadoDePartida;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Partida;

/**
 * TDA funcional: la <b>lógica</b> de una partida de cualquier juego por
 * turnos. La {@link Partida} tiene los datos; el administrador aplica las
 * reglas y la modifica.
 *
 * <h2>Método plantilla</h2>
 * {@link #jugar} es igual para todos los juegos de turnos y por eso está acá,
 * una sola vez, y es {@code final}:
 * <ol>
 *   <li>validar que la partida esté en curso y que el movimiento sea válido,</li>
 *   <li>armar la jugada para el historial (antes de aplicarla: así puede
 *       describir lo que va a pasar),</li>
 *   <li>aplicar el movimiento,</li>
 *   <li>registrar la jugada en el historial,</li>
 *   <li>ver si alguien ganó o si es empate,</li>
 *   <li>si sigue, pasar el turno.</li>
 * </ol>
 *
 * <h2>El movimiento M</h2>
 * Qué se juega en cada turno depende del juego: en un juego de tablero es una
 * {@code Posicion}; en la casita robada es un {@code Tiro} (una carta de la
 * mano y adónde va). Por eso el movimiento es un parámetro genérico.
 *
 * <p>Jerarquía:
 * <pre>
 *   AdministradorDePartida&lt;J, M, P&gt;                       (cualquier juego por turnos)
 *   ├── AdministradorDePartidaDeCasitaRobada            M = Tiro
 *   └── AdministradorDePartidaDeTablero&lt;T, J, P&gt;          M = Posicion
 *       ├── AdministradorDePartidaDeMemotest
 *       └── AdministradorDePartidaConFicha&lt;F, P&gt;
 *           ├── AdministradorDePartidaDeTateti
 *           └── AdministradorDePartidaDeReversi
 * </pre>
 *
 * <p>Esta clase <b>no sabe nada de pantallas, teclas ni sonidos</b>.
 *
 * @param <J> tipo de jugador
 * @param <M> tipo de movimiento (lo que se juega en cada turno)
 * @param <P> tipo de partida del juego
 */
public abstract class AdministradorDePartida<J extends Jugador, M, P extends Partida<J>> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final List<J> jugadores;
    private P partida;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Guarda los jugadores. <b>No crea la partida</b>: la subclase concreta
     * tiene que llamar a {@link #iniciarNuevaPartida()} al final de su
     * constructor, cuando sus propios atributos ya están inicializados.
     * @param jugadores al menos 2 jugadores
     */
    protected AdministradorDePartida(List<J> jugadores) {
        ValidacionesUtiles.validarDistintoDeNull(jugadores, "jugadores");
        ValidacionesUtiles.validarFalso(jugadores.size() < 2, "Se necesitan al menos 2 jugadores.");
        this.jugadores = new ArrayList<>(jugadores);
        this.partida = null;
    }

//METODOS ABSTRACTOS (lo que cambia en cada juego) --------------------------------------------------------

    /**
     * @return el nombre del juego, para mostrar
     */
    public abstract String getNombreDelJuego();

    /**
     * Crea una partida nueva, todavía sin preparar.
     * @param jugadores jugadores en orden de turno
     * @return la partida
     */
    protected abstract P crearPartida(List<J> jugadores);

    /**
     * @param movimiento movimiento ya validado con {@link #validarMovimiento}
     * @return true si el jugador de turno puede hacer ese movimiento
     */
    public abstract boolean esJugadaValida(M movimiento);

    /**
     * Arma la jugada que va al historial. Se llama <b>antes</b> de aplicar el
     * movimiento, con la partida todavía como estaba.
     * @param jugador quién juega
     * @param movimiento qué juega
     * @param numero número de jugada
     * @return la jugada
     */
    protected abstract Jugada<J> crearJugada(J jugador, M movimiento, int numero);

    /**
     * Modifica la partida con el movimiento. Se llama sólo con movimientos válidos.
     * @param movimiento qué se juega
     * @param jugador quién juega (el de turno)
     */
    protected abstract void aplicarJugada(M movimiento, J jugador);

    /**
     * Se llama después de cada jugada.
     * @return el jugador que ganó, o null si todavía nadie
     */
    protected abstract J buscarGanador();

    /**
     * Se llama después de cada jugada, si no hubo ganador.
     * @return true si la partida terminó empatada
     */
    protected abstract boolean esEmpate();

//GANCHOS (comportamiento por defecto que un juego puede redefinir) ---------------------------------------

    /**
     * Prepara una partida nueva (repartir, poner fichas iniciales...). Por defecto, nada.
     * @param partida partida recién creada
     */
    protected void prepararPartida(P partida) {
    }

    /**
     * Validaciones del movimiento en sí, antes de ver si es una jugada válida
     * (por ejemplo, que la posición esté dentro del tablero). Por defecto, ninguna.
     * @param movimiento movimiento, no null
     * @throws IllegalArgumentException si el movimiento no tiene sentido
     */
    protected void validarMovimiento(M movimiento) {
    }

    /**
     * Pasa el turno. Por defecto, al siguiente jugador.
     */
    protected void pasarAlSiguienteTurno() {
        getPartida().avanzarTurno();
    }

    /**
     * @param movimiento movimiento que no es válido
     * @return por qué no es válido
     */
    protected String explicarJugadaInvalida(M movimiento) {
        return "No se puede jugar " + movimiento + ".";
    }

    /**
     * @return información extra del juego para mostrar. Por defecto, nada.
     */
    public String describirSituacion() {
        return "";
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Empieza una partida nueva con los mismos jugadores (conservan sus victorias).
     * post: hay una partida en curso, preparada
     */
    public final void iniciarNuevaPartida() {
        P nueva = crearPartida(new ArrayList<>(this.jugadores));
        ValidacionesUtiles.validarDistintoDeNull(nueva, "partida creada");
        prepararPartida(nueva);
        this.partida = nueva;
    }

    /**
     * Hace el movimiento con el jugador de turno (método plantilla).
     * @param movimiento qué jugar, no null
     * @return el estado de la partida después de la jugada
     * @throws IllegalStateException si la partida ya terminó
     * @throws IllegalArgumentException si el movimiento es null o no es una jugada válida
     */
    public final EstadoDePartida jugar(M movimiento) {
        P partidaActual = getPartida();
        ValidacionesUtiles.validarEstadoFalso(!partidaActual.estaEnCurso(), "La partida ya terminó.");
        ValidacionesUtiles.validarDistintoDeNull(movimiento, "movimiento");
        validarMovimiento(movimiento);
        if (!esJugadaValida(movimiento)) {
            // Con un if y no con validarFalso(..., explicarJugadaInvalida(...)): el mensaje
            // se arma sólo si la jugada es inválida. Como argumento, se calcularía siempre.
            throw new IllegalArgumentException(explicarJugadaInvalida(movimiento));
        }

        J jugador = partidaActual.getJugadorDeTurno();
        Jugada<J> jugada = crearJugada(jugador, movimiento, partidaActual.getCantidadDeJugadas() + 1);
        aplicarJugada(movimiento, jugador);
        partidaActual.registrarJugada(jugada);
        actualizarEstado();
        if (partidaActual.estaEnCurso()) {
            pasarAlSiguienteTurno();
        }
        return partidaActual.getEstado();
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return la partida actual
     * @throws IllegalStateException si todavía no se inició ninguna partida
     */
    public P getPartida() {
        ValidacionesUtiles.validarEstadoFalso(this.partida == null,
                "No hay partida: la subclase tiene que llamar a iniciarNuevaPartida().");
        return this.partida;
    }

    /**
     * @return una copia de la lista de jugadores
     */
    public List<J> getJugadores() {
        return new ArrayList<>(this.jugadores);
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * Después de cada jugada: si hay ganador, termina la partida y le suma la victoria;
     * si no, y es empate, termina empatada.
     */
    private void actualizarEstado() {
        J ganador = buscarGanador();
        if (ganador != null) {
            getPartida().finalizarConGanador(ganador);
            ganador.sumarPartidaGanada();
        } else if (esEmpate()) {
            getPartida().finalizarEnEmpate();
        }
    }
}
