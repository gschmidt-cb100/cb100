package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

/**
 * Los sonidos que puede hacer un juego de tablero, por <b>evento</b> y no por
 * archivo: el controlador dice "hubo una jugada", y la implementación decide
 * qué suena.
 *
 * <p>Implementaciones:
 * <ul>
 *   <li>{@link SonidoDeLaCatedra}: usa ReproductorDeSonido y los MP3 de la cátedra.</li>
 *   <li>{@link SinSonido}: no hace nada. Se usa si no hay audio o no están los archivos,
 *       así el controlador nunca tiene que preguntar "¿hay sonido?" (patrón "objeto nulo").</li>
 * </ul>
 */
public interface Sonido extends AutoCloseable {

    /** Se hizo una jugada válida. */
    void reproducirJugada();

    /** Se intentó una jugada inválida. */
    void reproducirError();

    /** La partida terminó con un ganador. */
    void reproducirVictoria();

    /** La partida terminó empatada. */
    void reproducirEmpate();

    /** Empieza la música de fondo (si hay). */
    void iniciarMusica();

    /** Libera el audio. */
    @Override
    void close();
}
