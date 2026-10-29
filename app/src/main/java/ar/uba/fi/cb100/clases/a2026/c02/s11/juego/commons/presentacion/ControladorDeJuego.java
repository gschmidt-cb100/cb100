package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.EstadoDePartida;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Partida;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartida;

/**
 * El ciclo de <b>cualquier</b> juego por turnos: une el
 * {@link AdministradorDePartida} (las reglas), la {@link InterfazDeJuego}
 * (la pantalla y el teclado) y el {@link Sonido}.
 *
 * <pre>
 *   esperar acción ──► flechas:  mover el cursor                 (abstracto)
 *                 ├──► Enter:   elegir el movimiento             (abstracto)
 *                 │             y si está completo, jugarlo      (común)
 *                 ├──► Z:       nueva partida                    (común)
 *                 └──► Esc:     salir                            (común)
 *   después de cada acción: mostrar la partida (abstracto) y el mensaje (común)
 * </pre>
 *
 * <p>Lo que cambia es <b>cómo se elige un movimiento</b>: en un tablero, el
 * movimiento es la posición del cursor ({@link ControladorDeJuegoDeTablero});
 * en la casita robada hay que elegir primero una carta de la mano y después
 * adónde va (ControladorDeCasitaRobada).
 *
 * @param <M> tipo de movimiento del juego
 */
public abstract class ControladorDeJuego<M> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final AdministradorDePartida<?, M, ?> administrador;
    private final InterfazDeJuego interfaz;
    private final Sonido sonido;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param administrador lógica del juego, con una partida iniciada, no null
     * @param interfaz pantalla y teclado, no null
     * @param sonido sonidos, no null (usar SinSonido para jugar en silencio)
     */
    protected ControladorDeJuego(AdministradorDePartida<?, M, ?> administrador, InterfazDeJuego interfaz, Sonido sonido) {
        ValidacionesUtiles.validarDistintoDeNull(administrador, "administrador");
        ValidacionesUtiles.validarDistintoDeNull(interfaz, "interfaz");
        ValidacionesUtiles.validarDistintoDeNull(sonido, "sonido");
        this.administrador = administrador;
        this.interfaz = interfaz;
        this.sonido = sonido;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------

    /**
     * El usuario movió el cursor.
     * @param direccion hacia dónde
     */
    protected abstract void moverCursor(Direccion direccion);

    /**
     * El usuario apretó Enter con la partida en curso.
     * @return el movimiento elegido, o null si todavía falta elegir algo
     *         (en ese caso, {@link #getAvisoDeSeleccion} explica qué)
     */
    protected abstract M elegirMovimiento();

    /**
     * Dibuja la partida en la interfaz.
     */
    protected abstract void mostrarPartida();

//GANCHOS -------------------------------------------------------------------------------------------------

    /**
     * @return qué tiene que hacer el usuario, si eligió algo a medias, o un aviso
     *         sobre lo último que pasó. Por defecto, nada.
     */
    protected String getAvisoDeSeleccion() {
        return "";
    }

    /**
     * Se llama después de una jugada y al empezar una partida, para olvidar
     * lo que se estaba eligiendo. Por defecto, nada.
     */
    protected void reiniciarSeleccion() {
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Abre la interfaz y atiende al usuario hasta que pide salir.
     */
    public void jugar() {
        this.interfaz.abrir();
        this.sonido.iniciarMusica();
        actualizarPantalla("Flechas: mover · Enter: elegir · Z: nueva · Esc: salir");

        boolean salir = false;
        while (!salir) {
            Accion accion = this.interfaz.esperarAccion();
            if (accion == Accion.SALIR) {
                salir = true;
            } else if (accion.esMovimiento()) {
                moverCursor(accion.getDireccion());
                actualizarPantalla(getAvisoDeSeleccion());
            } else if (accion == Accion.NUEVA_PARTIDA) {
                nuevaPartida();
            } else if (accion == Accion.JUGAR) {
                if (this.administrador.getPartida().estaEnCurso()) {
                    M movimiento = elegirMovimiento();
                    if (movimiento != null) {
                        intentarJugada(movimiento);
                    } else {
                        actualizarPantalla(getAvisoDeSeleccion());
                    }
                } else {
                    nuevaPartida();
                }
            }
        }
    }

//GETTERS PARA LAS SUBCLASES ------------------------------------------------------------------------------

    protected AdministradorDePartida<?, M, ?> getAdministrador() {
        return administrador;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * Juega el movimiento. Si no es válido, el administrador lanza una
     * excepción con el motivo: se muestra y suena el error.
     */
    private void intentarJugada(M movimiento) {
        try {
            EstadoDePartida estado = this.administrador.jugar(movimiento);
            if (estado == EstadoDePartida.GANADA) {
                this.sonido.reproducirVictoria();
            } else if (estado == EstadoDePartida.EMPATADA) {
                this.sonido.reproducirEmpate();
            } else {
                this.sonido.reproducirJugada();
            }
            reiniciarSeleccion();
            actualizarPantalla(getAvisoDeSeleccion());
        } catch (IllegalArgumentException e) {
            this.sonido.reproducirError();
            actualizarPantalla(e.getMessage());
        }
    }

    private void nuevaPartida() {
        this.administrador.iniciarNuevaPartida();
        reiniciarSeleccion();
        actualizarPantalla("Nueva partida.");
    }

    /**
     * Redibuja la partida y arma el mensaje de abajo.
     * @param aviso texto que va primero en el mensaje (puede ser vacío)
     */
    private void actualizarPantalla(String aviso) {
        mostrarPartida();
        this.interfaz.mostrarMensaje(armarMensaje(this.administrador.getPartida(), aviso));
    }

    private String armarMensaje(Partida<?> partida, String aviso) {
        String mensaje = "";
        if (aviso != null && !aviso.isEmpty()) {
            mensaje += aviso + "   ";
        }
        if (partida.getEstado() == EstadoDePartida.GANADA) {
            mensaje += "¡Ganó " + partida.getGanador() + "! Enter: otra";
        } else if (partida.getEstado() == EstadoDePartida.EMPATADA) {
            mensaje += "Empate. Enter: otra";
        } else {
            mensaje += "Turno: " + partida.getJugadorDeTurno();
        }
        String situacion = this.administrador.describirSituacion();
        if (!situacion.isEmpty()) {
            mensaje += "   " + situacion;
        }
        return mensaje + "   [" + armarMarcador() + "]";
    }

    /**
     * @return las partidas ganadas por cada jugador, por ejemplo "Ana 2 - Beto 1"
     */
    private String armarMarcador() {
        String marcador = "";
        for (Jugador jugador : this.administrador.getJugadores()) {
            if (!marcador.isEmpty()) {
                marcador += " - ";
            }
            marcador += jugador.getNombre() + " " + jugador.getPartidasGanadas();
        }
        return marcador;
    }
}
