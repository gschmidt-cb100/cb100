package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Jugador (dato).
 * Un jugador de cualquier juego: tiene un nombre y acumula las partidas que
 * ganó. Vive más que una partida: al empezar otra, los jugadores son los
 * mismos y conservan sus victorias.
 *
 * <p>No tiene ficha: hay juegos sin fichas (memotest). Los juegos que sí
 * las tienen usan {@link JugadorConFicha}, que hereda de esta clase.
 *
 * Invariantes:
 *  - nombre no es null ni vacío, y no cambia.
 *  - partidasGanadas >= 0 y nunca disminuye.
 */
public class Jugador {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String nombre;
    private int partidasGanadas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param nombre nombre del jugador, no null ni vacío
     * @throws IllegalArgumentException si el nombre es null o vacío
     */
    public Jugador(String nombre) {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre del jugador no puede estar vacío.");
        this.nombre = nombre;
        this.partidasGanadas = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * post: suma una partida ganada
     */
    public void sumarPartidaGanada() {
        this.partidasGanadas++;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     * Dos jugadores son iguales si son de la misma clase y tienen el mismo nombre.
     * (getClass() y no instanceof: un Jugador nunca es igual a un JugadorConFicha.)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador otro = (Jugador) o;
        return this.nombre.equals(otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
