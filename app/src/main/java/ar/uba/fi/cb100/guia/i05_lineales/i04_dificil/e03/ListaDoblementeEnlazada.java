package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e03;

import java.util.Objects;

/**
 * Lista doblemente enlazada implementada A MANO. Cada nodo apunta a su
 * anterior y a su siguiente. Se mantienen referencias a la cabeza y a la cola,
 * lo que permite agregar al final en O(1) y recorrer desde el extremo mas
 * cercano al indice buscado.
 *
 * Complejidad (n = tamanio):
 *  - tamanio: O(1)
 *  - agregar (al final): O(1) por el puntero a la cola
 *  - insertar(i, x): O(min(i, n-i)) porque recorre desde el extremo mas cercano
 *  - eliminar(i): O(min(i, n-i))
 *  - obtener(i): O(min(i, n-i))
 *  - indiceDe(x): O(n)
 */
public class ListaDoblementeEnlazada<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public ListaDoblementeEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Agrega al final. O(1). */
    public void agregar(T x) {
        Nodo<T> nuevo = new Nodo<>(x);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamanio++;
    }

    /** Inserta x en la posicion i (0-based). Admite i == tamanio. */
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("Indice invalido: " + i);
        }
        if (i == tamanio) {
            agregar(x);
            return;
        }
        Nodo<T> actual = nodoEn(i); // nodo que quedara despues del nuevo
        Nodo<T> nuevo = new Nodo<>(x);
        Nodo<T> previo = actual.anterior;
        nuevo.anterior = previo;
        nuevo.siguiente = actual;
        actual.anterior = nuevo;
        if (previo == null) {
            cabeza = nuevo; // se inserto al frente
        } else {
            previo.siguiente = nuevo;
        }
        tamanio++;
    }

    /** Elimina y devuelve el elemento en la posicion i. */
    public T eliminar(int i) {
        validarIndice(i);
        Nodo<T> actual = nodoEn(i);
        Nodo<T> previo = actual.anterior;
        Nodo<T> proximo = actual.siguiente;
        if (previo == null) {
            cabeza = proximo;
        } else {
            previo.siguiente = proximo;
        }
        if (proximo == null) {
            cola = previo;
        } else {
            proximo.anterior = previo;
        }
        actual.anterior = null;
        actual.siguiente = null;
        tamanio--;
        return actual.dato;
    }

    /** Devuelve el elemento en la posicion i. */
    public T obtener(int i) {
        validarIndice(i);
        return nodoEn(i).dato;
    }

    /** Indice de la primera aparicion de x, o -1. O(n). */
    public int indiceDe(T x) {
        Nodo<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (Objects.equals(actual.dato, x)) {
                return indice;
            }
            actual = actual.siguiente;
            indice++;
        }
        return -1;
    }

    /** Recorre desde el extremo mas cercano a i para ahorrar pasos. */
    private Nodo<T> nodoEn(int i) {
        if (i < tamanio / 2) {
            Nodo<T> actual = cabeza;
            for (int j = 0; j < i; j++) {
                actual = actual.siguiente;
            }
            return actual;
        } else {
            Nodo<T> actual = cola;
            for (int j = tamanio - 1; j > i; j--) {
                actual = actual.anterior;
            }
            return actual;
        }
    }

    private void validarIndice(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        for (int i = 1; i <= 5; i++) {
            l.agregar(i);
        }
        l.eliminar(2); // elimina el 3 (del medio)
        l.insertar(0, 99);
        for (int i = 0; i < l.tamanio(); i++) {
            System.out.println(i + " -> " + l.obtener(i));
        }
    }
}
