package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Posicion (dato inmutable): una fila y una columna, <b>de 1 a n</b>.
 *
 * <p>Es inmutable: avanzar en una dirección devuelve una posición nueva. Por
 * eso se puede compartir sin miedo a que alguien la cambie.
 *
 * <p>Una posición puede quedar fuera de un tablero (por ejemplo, al avanzar
 * desde un borde): decidir si está dentro es responsabilidad del
 * {@link Tablero}, que es quien conoce sus límites.
 */
public class Posicion {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int fila;
    private final int columna;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * @param fila fila, de 1 a n
     * @param columna columna, de 1 a n
     */
    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param direccion dirección hacia donde avanzar, no null
     * @return la posición vecina en esa dirección (puede quedar fuera del tablero)
     */
    public Posicion avanzar(Direccion direccion) {
        ValidacionesUtiles.validarDistintoDeNull(direccion, "direccion");
        return new Posicion(this.fila + direccion.getDeltaFila(), this.columna + direccion.getDeltaColumna());
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion otra = (Posicion) o;
        return this.fila == otra.fila && this.columna == otra.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ")";
    }
}
