package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

import java.util.Arrays;

/**
 * Implementación de {@link Pila} sobre un arreglo que se redimensiona al
 * llenarse.
 *
 * @param <T> tipo de los elementos
 */
public class PilaArreglo<T> implements Pila<T> {

    private Object[] datos;
    private int cantidad;

    public PilaArreglo() {
        this.datos = new Object[4];
        this.cantidad = 0;
    }

    @Override
    public void apilar(T elemento) {
        if (cantidad == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);
        }
        datos[cantidad++] = elemento;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T elemento = (T) datos[--cantidad];
        datos[cantidad] = null;
        return elemento;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return (T) datos[cantidad - 1];
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    @Override
    public int tamanio() {
        return cantidad;
    }
}
