package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA JugadorConFicha (dato): un {@link Jugador} que además juega siempre con
 * la misma ficha (X u O en el tateti, negras o blancas en el reversi).
 *
 * <p>Herencia: nombre y partidas ganadas vienen de Jugador; acá sólo se
 * agrega la ficha.
 *
 * Invariante: ficha no es null y no cambia.
 *
 * @param <F> tipo de ficha del juego
 */
public class JugadorConFicha<F extends Ficha> extends Jugador {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final F ficha;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param nombre nombre del jugador, no null ni vacío
     * @param ficha ficha con la que juega, no null
     * @throws IllegalArgumentException si el nombre es null o vacío, o la ficha es null
     */
    public JugadorConFicha(String nombre, F ficha) {
        super(nombre);
        ValidacionesUtiles.validarDistintoDeNull(ficha, "ficha");
        this.ficha = ficha;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public F getFicha() {
        return ficha;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     * Dos jugadores con ficha son iguales si tienen el mismo nombre y la misma ficha.
     */
    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;          // misma clase y mismo nombre
        JugadorConFicha<?> otro = (JugadorConFicha<?>) o;
        return this.ficha.equals(otro.ficha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ficha);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + ficha.getNombre() + ")";
    }
}
