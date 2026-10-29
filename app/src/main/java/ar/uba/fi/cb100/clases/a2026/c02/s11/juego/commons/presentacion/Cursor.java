package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.presentacion;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Direccion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos.Posicion;

/**
 * TDA Cursor (dato de presentación): la celda que el usuario tiene
 * seleccionada. No es parte del juego (la partida no sabe que existe), es
 * parte de cómo se juega con el teclado.
 *
 * Invariante: la posición está siempre dentro de filas x columnas.
 * Al llegar a un borde, el cursor no se sale: se queda donde está.
 */
public class Cursor {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int cantidadDeFilas;
    private final int cantidadDeColumnas;
    private Posicion posicion;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea el cursor en el centro del área.
     * @param cantidadDeFilas filas del área, mayor que cero
     * @param cantidadDeColumnas columnas del área, mayor que cero
     */
    public Cursor(int cantidadDeFilas, int cantidadDeColumnas) {
        this(cantidadDeFilas, cantidadDeColumnas, new Posicion((cantidadDeFilas + 1) / 2, (cantidadDeColumnas + 1) / 2));
    }

    /**
     * Crea el cursor en la posición indicada.
     * @param cantidadDeFilas filas del área, mayor que cero
     * @param cantidadDeColumnas columnas del área, mayor que cero
     * @param inicial posición inicial, dentro del área
     */
    public Cursor(int cantidadDeFilas, int cantidadDeColumnas, Posicion inicial) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeFilas, "cantidadDeFilas");
        ValidacionesUtiles.esMayorQueCero(cantidadDeColumnas, "cantidadDeColumnas");
        this.cantidadDeFilas = cantidadDeFilas;
        this.cantidadDeColumnas = cantidadDeColumnas;
        ValidacionesUtiles.validarDistintoDeNull(inicial, "inicial");
        ValidacionesUtiles.validarFalso(!estaDentro(inicial), "La posición inicial " + inicial + " está fuera del área.");
        this.posicion = inicial;
    }

//METODOS DE COMPORTAMIENTO (posicion) --------------------------------------------------------------------

    /**
     * Lleva el cursor a una posición.
     * @param destino posición dentro del área
     * @throws IllegalArgumentException si está fuera
     */
    public void irA(Posicion destino) {
        ValidacionesUtiles.validarDistintoDeNull(destino, "destino");
        ValidacionesUtiles.validarFalso(!estaDentro(destino), "La posición " + destino + " está fuera del área.");
        this.posicion = destino;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Mueve el cursor una celda en la dirección indicada, si no se sale del área.
     * @param direccion dirección, no null
     * @return true si se movió, false si estaba en el borde
     */
    public boolean mover(Direccion direccion) {
        Posicion nueva = this.posicion.avanzar(direccion);
        if (estaDentro(nueva)) {
            this.posicion = nueva;
            return true;
        }
        return false;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Posicion getPosicion() {
        return posicion;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private boolean estaDentro(Posicion posicion) {
        return posicion.getFila() >= 1 && posicion.getFila() <= this.cantidadDeFilas &&
               posicion.getColumna() >= 1 && posicion.getColumna() <= this.cantidadDeColumnas;
    }
}
