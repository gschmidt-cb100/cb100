package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.memotest.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Carta (dato): una carta del memotest, con una figura y un estado.
 *
 * <p>Es lo que hay en cada celda del tablero: {@code Tablero<Carta>}. A
 * diferencia de una ficha, la carta <b>cambia de estado</b> sin moverse de la
 * celda, y cuida que los cambios sigan el orden correcto (ver
 * {@link EstadoDeCarta}).
 *
 * <p>No redefine equals: dos cartas con la misma figura son <b>dos cartas
 * distintas</b> (un par). Para saber si forman pareja está {@link #esParejaDe}.
 *
 * Invariantes: figura y estado no son null; la figura no cambia.
 */
public class Carta {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final FiguraDeCarta figura;
    private EstadoDeCarta estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una carta boca abajo.
     * @param figura figura de la carta, no null
     * @throws IllegalArgumentException si la figura es null
     */
    public Carta(FiguraDeCarta figura) {
        ValidacionesUtiles.validarDistintoDeNull(figura, "figura");
        this.figura = figura;
        this.estado = EstadoDeCarta.BOCA_ABAJO;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * La pone boca arriba.
     * @throws IllegalStateException si no estaba boca abajo
     */
    public void darVuelta() {
        validarEstado(EstadoDeCarta.BOCA_ABAJO, "dar vuelta");
        this.estado = EstadoDeCarta.BOCA_ARRIBA;
    }

    /**
     * La vuelve a poner boca abajo.
     * @throws IllegalStateException si no estaba boca arriba
     */
    public void tapar() {
        validarEstado(EstadoDeCarta.BOCA_ARRIBA, "tapar");
        this.estado = EstadoDeCarta.BOCA_ABAJO;
    }

    /**
     * La marca como parte de un par encontrado: queda a la vista para siempre.
     * @throws IllegalStateException si no estaba boca arriba
     */
    public void marcarEncontrada() {
        validarEstado(EstadoDeCarta.BOCA_ARRIBA, "marcar como encontrada");
        this.estado = EstadoDeCarta.ENCONTRADA;
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @param otra otra carta, no null
     * @return true si es otra carta (no la misma) con la misma figura
     */
    public boolean esParejaDe(Carta otra) {
        ValidacionesUtiles.validarDistintoDeNull(otra, "otra");
        return this != otra && this.figura == otra.figura;
    }

    public boolean estaBocaAbajo() {
        return this.estado == EstadoDeCarta.BOCA_ABAJO;
    }

    public boolean estaBocaArriba() {
        return this.estado == EstadoDeCarta.BOCA_ARRIBA;
    }

    public boolean estaEncontrada() {
        return this.estado == EstadoDeCarta.ENCONTRADA;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public FiguraDeCarta getFigura() {
        return figura;
    }

    public EstadoDeCarta getEstado() {
        return estado;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private void validarEstado(EstadoDeCarta esperado, String accion) {
        ValidacionesUtiles.validarEstadoFalso(this.estado != esperado,
                "No se puede " + accion + " una carta " + this.estado + ".");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return figura + " (" + estado + ")";
    }
}
