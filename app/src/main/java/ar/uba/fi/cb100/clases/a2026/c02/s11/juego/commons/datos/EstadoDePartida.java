package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

/**
 * Estados posibles de una partida de cualquier juego.
 *
 * <p>A diferencia del tateti de la semana 4 (GANO_X, GANO_O), acá el estado no
 * dice <i>quién</i> ganó: eso lo guarda la {@link Partida}. Así el enum sirve
 * para cualquier juego y cualquier cantidad de jugadores.
 */
public enum EstadoDePartida {
    EN_CURSO,
    GANADA,
    EMPATADA;

    /**
     * @return true si la partida terminó, con ganador o en empate
     */
    public boolean estaTerminada() {
        return this != EN_CURSO;
    }
}
