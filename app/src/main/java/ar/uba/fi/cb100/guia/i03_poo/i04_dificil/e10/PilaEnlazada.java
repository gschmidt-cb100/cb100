package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e10;

/**
 * Implementación de {@link Pila} sobre nodos enlazados. El tope es la cabeza
 * de la lista, por lo que apilar y desapilar son O(1).
 *
 * @param <T> tipo de los elementos
 */
public class PilaEnlazada<T> implements Pila<T> {

    private static final class Nodo<T> {
        private final T valor;
        private final Nodo<T> siguiente;

        private Nodo(T valor, Nodo<T> siguiente) {
            this.valor = valor;
            this.siguiente = siguiente;
        }
    }

    private Nodo<T> tope;
    private int cantidad;

    @Override
    public void apilar(T elemento) {
        tope = new Nodo<>(elemento, tope);
        cantidad++;
    }

    @Override
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T valor = tope.valor;
        tope = tope.siguiente;
        cantidad--;
        return valor;
    }

    @Override
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return tope.valor;
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
