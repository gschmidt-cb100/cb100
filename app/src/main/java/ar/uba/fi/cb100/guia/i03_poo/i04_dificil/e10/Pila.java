package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

/**
 * TDA Pila (LIFO). Define el contrato común que deben respetar todas las
 * implementaciones, independientemente de su estructura interna.
 *
 * @param <T> tipo de los elementos
 */
public interface Pila<T> {

    /** Apila un elemento en el tope. */
    void apilar(T elemento);

    /** Quita y devuelve el elemento del tope. */
    T desapilar();

    /** Devuelve el elemento del tope sin quitarlo. */
    T tope();

    boolean estaVacia();

    int tamanio();
}
