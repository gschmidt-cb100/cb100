package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.commons.datos;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Pila (LIFO) genérica, la misma de la guía (i05_lineales): el tope es el
 * último elemento de la lista interna, así apilar y desapilar son al final.
 *
 * <p>La usa la casita robada: el mazo y cada casita son pilas (sólo importa
 * la carta de arriba).
 *
 * @param <T> tipo de los elementos
 */
public class Pila<T> {

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final List<T> datos;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea una pila vacía.
     */
    public Pila() {
        this.datos = new ArrayList<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * @param elemento elemento a poner en el tope, no null
     */
    public void apilar(T elemento) {
        ValidacionesUtiles.validarDistintoDeNull(elemento, "elemento");
        this.datos.add(elemento);
    }

    /**
     * @return el elemento del tope, que se quita
     * @throws IllegalStateException si la pila está vacía
     */
    public T desapilar() {
        validarNoVacia();
        return this.datos.remove(this.datos.size() - 1);
    }

//CONSULTAS -----------------------------------------------------------------------------------------------

    /**
     * @return el elemento del tope, sin quitarlo
     * @throws IllegalStateException si la pila está vacía
     */
    public T tope() {
        validarNoVacia();
        return this.datos.get(this.datos.size() - 1);
    }

    public boolean estaVacia() {
        return this.datos.isEmpty();
    }

    public int tamanio() {
        return this.datos.size();
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    private void validarNoVacia() {
        ValidacionesUtiles.validarEstadoFalso(estaVacia(), "La pila está vacía.");
    }
}
