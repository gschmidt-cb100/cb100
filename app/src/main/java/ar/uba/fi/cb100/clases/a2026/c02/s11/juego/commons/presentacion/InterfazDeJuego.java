package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

/**
 * Lo que necesita el {@link ControladorDeJuego} de <b>cualquier</b> pantalla:
 * abrirse, mostrar un mensaje y recibir lo que quiere hacer el usuario.
 *
 * <p>Cómo se muestra la partida depende del juego, y lo agregan las
 * subinterfaces:
 * <ul>
 *   <li>{@link InterfazDeTablero}: un tablero con un cursor.</li>
 *   <li>InterfazDeCasitaRobada: mesa, casitas, mazo y la mano del jugador.</li>
 * </ul>
 *
 * <p>Es una interfaz para que el juego sea <b>portable</b>: hoy está la
 * implementación gráfica, y se podría agregar una de consola sin tocar los juegos.
 */
public interface InterfazDeJuego extends AutoCloseable {

    /**
     * Abre la interfaz (por ejemplo, muestra la ventana).
     */
    void abrir();

    /**
     * @param mensaje texto a mostrar (turno, avisos, resultado)
     */
    void mostrarMensaje(String mensaje);

    /**
     * Espera hasta que el usuario haga algo.
     * @return la acción pedida; SALIR si cerró la interfaz
     */
    Accion esperarAccion();

    /**
     * Cierra la interfaz. Sin "throws Exception", para que se pueda usar en un try sin catch.
     */
    @Override
    void close();
}
