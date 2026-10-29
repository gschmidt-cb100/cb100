package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Partida (dato): una partida de <b>cualquier juego por turnos</b>, con o
 * sin tablero.
 *
 * <p>Guarda lo que tienen en común todos los juegos: los jugadores, a quién le
 * toca, el historial de jugadas, el estado y el ganador. <b>No conoce las
 * reglas</b>: eso lo decide el {@code AdministradorDePartida} de cada juego.
 * Tampoco sabe si hay tablero: eso lo agrega {@link PartidaDeTablero}.
 *
 * <p>Sí protege sus propias invariantes: no deja finalizar dos veces, ni
 * declarar ganador a alguien que no juega, ni avanzar el turno de una partida
 * terminada.
 *
 * Invariantes:
 *  - hay al menos 2 jugadores, todos distintos.
 *  - 0 <= indiceDelTurno < cantidad de jugadores.
 *  - ganador != null si y solo si estado == GANADA.
 *
 * <p>Jerarquía:
 * <pre>
 *   Partida&lt;J&gt;                                      turnos, historial, ganador
 *   ├── PartidaDeCasitaRobada                       + mazo, mesa, manos, casitas
 *   └── PartidaDeTablero&lt;T, J&gt;                       + tablero
 *       ├── PartidaDeMemotest                       + cartas dadas vuelta, pares
 *       └── PartidaConFicha&lt;F&gt;                      + fichas distintas
 *           ├── PartidaDeTateti
 *           └── PartidaDeReversi
 * </pre>
 *
 * @param <J> tipo de jugador
 */
public class Partida<J extends Jugador> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final List<J> jugadores;
    private final List<Jugada<J>> jugadas;
    private int indiceDelTurno;
    private EstadoDePartida estado;
    private J ganador;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una partida en curso. Empieza el primer jugador de la lista.
     * @param jugadores al menos 2 jugadores, todos distintos
     * @throws IllegalArgumentException si no se cumplen las condiciones
     */
    public Partida(List<J> jugadores) {
        ValidacionesUtiles.validarDistintoDeNull(jugadores, "jugadores");
        ValidacionesUtiles.validarFalso(jugadores.size() < 2, "Una partida necesita al menos 2 jugadores.");
        validarJugadoresDistintos(jugadores);

        this.jugadores = new ArrayList<>(jugadores);   // copia: que nadie de afuera la modifique
        this.jugadas = new ArrayList<>();
        this.indiceDelTurno = 0;
        this.estado = EstadoDePartida.EN_CURSO;
        this.ganador = null;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Agrega una jugada al historial.
     * @param jugada jugada hecha, no null, del jugador de turno
     * @throws IllegalArgumentException si la jugada es null o no es del jugador de turno
     * @throws IllegalStateException si la partida terminó
     */
    public void registrarJugada(Jugada<J> jugada) {
        validarEnCurso();
        ValidacionesUtiles.validarDistintoDeNull(jugada, "jugada");
        ValidacionesUtiles.validarFalso(!jugada.getJugador().equals(getJugadorDeTurno()),
                "La jugada es de " + jugada.getJugador() + " pero le toca a " + getJugadorDeTurno() + ".");
        this.jugadas.add(jugada);
    }

    /**
     * Pasa el turno al siguiente jugador (en ronda).
     * @throws IllegalStateException si la partida terminó
     */
    public void avanzarTurno() {
        validarEnCurso();
        this.indiceDelTurno = (this.indiceDelTurno + 1) % this.jugadores.size();
    }

    /**
     * Termina la partida con un ganador.
     * @param ganador jugador de esta partida, no null
     * @throws IllegalArgumentException si el ganador es null o no juega esta partida
     * @throws IllegalStateException si la partida ya terminó
     */
    public void finalizarConGanador(J ganador) {
        validarEnCurso();
        ValidacionesUtiles.validarDistintoDeNull(ganador, "ganador");
        ValidacionesUtiles.validarFalso(!this.jugadores.contains(ganador), ganador + " no juega esta partida.");
        this.ganador = ganador;
        this.estado = EstadoDePartida.GANADA;
    }

    /**
     * Termina la partida en empate.
     * @throws IllegalStateException si la partida ya terminó
     */
    public void finalizarEnEmpate() {
        validarEnCurso();
        this.estado = EstadoDePartida.EMPATADA;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return el jugador al que le toca jugar
     */
    public J getJugadorDeTurno() {
        return this.jugadores.get(this.indiceDelTurno);
    }

    /**
     * @param jugador jugador de esta partida
     * @return su posición en el orden de turnos (0, 1, ...)
     * @throws IllegalArgumentException si no juega esta partida
     */
    public int getOrdenDe(J jugador) {
        int orden = this.jugadores.indexOf(jugador);
        ValidacionesUtiles.validarFalso(orden == -1, jugador + " no juega esta partida.");
        return orden;
    }

    /**
     * @return la última jugada, o null si todavía no se jugó
     */
    public Jugada<J> getUltimaJugada() {
        if (this.jugadas.isEmpty()) {
            return null;
        }
        return this.jugadas.get(this.jugadas.size() - 1);
    }

    public boolean estaEnCurso() {
        return this.estado == EstadoDePartida.EN_CURSO;
    }

    public int getCantidadDeJugadas() {
        return this.jugadas.size();
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * @return una copia de la lista de jugadores, en orden de turno
     */
    public List<J> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    /**
     * @return una copia del historial de jugadas
     */
    public List<Jugada<J>> getJugadas() {
        return new ArrayList<>(jugadas);
    }

    public EstadoDePartida getEstado() {
        return estado;
    }

    /**
     * @return el ganador, o null si la partida no terminó o terminó empatada
     */
    public J getGanador() {
        return ganador;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private void validarEnCurso() {
        ValidacionesUtiles.validarEstadoFalso(!estaEnCurso(), "La partida ya terminó: " + estado + ".");
    }

    private static <J extends Jugador> void validarJugadoresDistintos(List<J> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            ValidacionesUtiles.validarDistintoDeNull(jugadores.get(i), "jugador");
            for (int j = i + 1; j < jugadores.size(); j++) {
                ValidacionesUtiles.validarFalso(jugadores.get(i).equals(jugadores.get(j)),
                        "El jugador " + jugadores.get(i) + " está repetido.");
            }
        }
    }
}
