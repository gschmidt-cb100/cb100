package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.PartidaDeTablero;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.funcional.AdministradorDePartidaDeTablero;

/**
 * {@link ControladorDeJuego} de los juegos de tablero: el cursor recorre el
 * tablero, y el movimiento es la posición del cursor.
 *
 * <p>Es el mismo para el tateti, el reversi y el memotest: no sabe qué hay en
 * las celdas ni si el juego tiene fichas.
 *
 * @param <T> lo que hay en cada celda del tablero
 */
public class ControladorDeJuegoDeTablero<T> extends ControladorDeJuego<Posicion> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final AdministradorDePartidaDeTablero<T, ?, ?> administrador;
    private final InterfazDeTablero<T> interfaz;
    private final Cursor cursor;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param administrador lógica del juego, con una partida iniciada, no null
     * @param interfaz pantalla y teclado, no null
     * @param sonido sonidos, no null
     */
    public ControladorDeJuegoDeTablero(AdministradorDePartidaDeTablero<T, ?, ?> administrador, InterfazDeTablero<T> interfaz,
                                       Sonido sonido) {
        super(administrador, interfaz, sonido);
        this.administrador = administrador;
        this.interfaz = interfaz;
        PartidaDeTablero<T, ?> partida = administrador.getPartida();
        this.cursor = new Cursor(partida.getTablero().getCantidadDeFilas(), partida.getTablero().getCantidadDeColumnas());
    }

//METODOS DE ControladorDeJuego ---------------------------------------------------------------------------

    @Override
    protected void moverCursor(Direccion direccion) {
        this.cursor.mover(direccion);
    }

    /**
     * En un tablero, el movimiento está completo con sólo apretar Enter: es donde está el cursor.
     */
    @Override
    protected Posicion elegirMovimiento() {
        return this.cursor.getPosicion();
    }

    @Override
    protected void mostrarPartida() {
        List<Posicion> marcadas = new ArrayList<>();
        if (this.administrador.debeMarcarJugadasValidas()) {
            marcadas = this.administrador.getJugadasValidas();
        }
        this.interfaz.mostrar(this.administrador.getPartida(), this.cursor.getPosicion(), marcadas);
    }
}
