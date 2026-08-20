package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

/**
 * Implementacion del TAD {@link Lista} sobre nodos simplemente enlazados.
 *
 * Complejidad (n = tamanio):
 *  - tamanio: O(1)
 *  - agregar (al final): O(n)
 *  - insertar(i, x) / eliminar(i) / obtener(i): O(i)
 */
public class ListaEnlazada<T> implements Lista<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public void agregar(T x) {
        Nodo<T> nuevo = new Nodo<>(x);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("Indice invalido: " + i);
        }
        if (i == 0) {
            Nodo<T> nuevo = new Nodo<>(x);
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo<T> anterior = nodoEn(i - 1);
            Nodo<T> nuevo = new Nodo<>(x);
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public T eliminar(int i) {
        validarIndice(i);
        Nodo<T> eliminado;
        if (i == 0) {
            eliminado = cabeza;
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> anterior = nodoEn(i - 1);
            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;
        }
        eliminado.siguiente = null;
        tamanio--;
        return eliminado.dato;
    }

    @Override
    public T obtener(int i) {
        validarIndice(i);
        return nodoEn(i).dato;
    }

    private Nodo<T> nodoEn(int i) {
        Nodo<T> actual = cabeza;
        for (int j = 0; j < i; j++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    private void validarIndice(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
    }
}
