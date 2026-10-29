package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.presentacion;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.CartaEspanola;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.PartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos.Tiro;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.funcional.AdministradorDePartidaDeCasitaRobada;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Jugador;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.ControladorDeJuego;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Cursor;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion.Sonido;

/**
 * {@link ControladorDeJuego} de la casita robada. El ciclo (teclas, sonido,
 * mensajes, nueva partida) es el de commons; lo único propio es <b>cómo se
 * arma un Tiro</b>, que lleva dos pasos:
 * <ol>
 *   <li>Enter sobre una carta de la mano: la elige (otra vez Enter, la suelta).</li>
 *   <li>Enter sobre la mesa o sobre la casita de otro jugador: arma el Tiro y se juega.</li>
 * </ol>
 */
public class ControladorDeCasitaRobada extends ControladorDeJuego<Tiro> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final AdministradorDePartidaDeCasitaRobada administrador;
    private final InterfazDeCasitaRobada interfaz;
    private final DisposicionDeCasitaRobada disposicion;
    private final Cursor cursor;
    private CartaEspanola cartaElegida;
    private String avisoPuntual;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param administrador lógica del juego, con una partida iniciada, no null
     * @param interfaz pantalla y teclado, no null
     * @param disposicion la misma que usa la interfaz, no null
     * @param sonido sonidos, no null
     */
    public ControladorDeCasitaRobada(AdministradorDePartidaDeCasitaRobada administrador, InterfazDeCasitaRobada interfaz,
                                     DisposicionDeCasitaRobada disposicion, Sonido sonido) {
        super(administrador, interfaz, sonido);
        ValidacionesUtiles.validarDistintoDeNull(disposicion, "disposicion");
        this.administrador = administrador;
        this.interfaz = interfaz;
        this.disposicion = disposicion;
        this.cursor = new Cursor(DisposicionDeCasitaRobada.FILAS, DisposicionDeCasitaRobada.COLUMNAS,
                                 disposicion.getPosicionDeMano(0));
        this.cartaElegida = null;
        this.avisoPuntual = null;
    }

//METODOS DE ControladorDeJuego ---------------------------------------------------------------------------

    @Override
    protected void moverCursor(Direccion direccion) {
        this.cursor.mover(direccion);
    }

    @Override
    protected Tiro elegirMovimiento() {
        Posicion celda = this.cursor.getPosicion();
        switch (this.disposicion.getZona(celda)) {
            case MANO:
                elegirCartaDeLaMano(this.disposicion.getIndice(celda));
                return null;
            case ELEGIDA:
                this.cartaElegida = null;
                return null;
            case MESA:
                if (this.cartaElegida == null) {
                    this.avisoPuntual = "Primero elegí una carta de tu mano.";
                    return null;
                }
                return Tiro.aLaMesa(this.cartaElegida);
            case CASITA:
                if (this.cartaElegida == null) {
                    this.avisoPuntual = "Primero elegí una carta de tu mano.";
                    return null;
                }
                Jugador duenio = getPartida().getJugadores().get(this.disposicion.getIndice(celda));
                return Tiro.aLaCasitaDe(this.cartaElegida, duenio);
            default:
                this.avisoPuntual = "Ahí no se puede jugar: elegí una carta de tu mano, y después la mesa o una casita.";
                return null;
        }
    }

    @Override
    protected void mostrarPartida() {
        this.interfaz.mostrar(getPartida(), this.cursor.getPosicion(), this.cartaElegida);
    }

    @Override
    protected String getAvisoDeSeleccion() {
        if (this.avisoPuntual != null) {
            String aviso = this.avisoPuntual;
            this.avisoPuntual = null;
            return aviso;
        }
        if (this.cartaElegida != null) {
            return this.cartaElegida + ": ¿a la mesa o a una casita?";
        }
        if (getPartida().getUltimaJugada() != null) {
            return getPartida().getUltimaJugada() + ".";      // por ejemplo "Ana levantó con el 5 de espadas (+3)."
        }
        return "";
    }

    /**
     * Después de jugar (o al empezar otra partida): se suelta la carta y el cursor vuelve a la mano.
     */
    @Override
    protected void reiniciarSeleccion() {
        this.cartaElegida = null;
        this.cursor.irA(this.disposicion.getPosicionDeMano(0));
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private void elegirCartaDeLaMano(int indice) {
        List<CartaEspanola> mano = getPartida().getManoDe(getPartida().getJugadorDeTurno()).getCartas();
        if (indice >= mano.size()) {
            this.avisoPuntual = "Ahí no hay carta.";
        } else if (mano.get(indice).equals(this.cartaElegida)) {
            this.cartaElegida = null;                     // Enter otra vez sobre la misma: la suelta
        } else {
            this.cartaElegida = mano.get(indice);
        }
    }

    private PartidaDeCasitaRobada getPartida() {
        return this.administrador.getPartida();
    }
}
