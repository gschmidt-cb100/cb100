package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e02;

import java.util.Objects;

/**
 * Lista simplemente enlazada implementada A MANO con nodos propios.
 * Solo se mantiene una referencia a la cabeza; el largo se lleva en un contador.
 *
 * Complejidad (n = tamanio):
 *  - tamanio: O(1)
 *  - agregar (al final): O(n) porque hay que recorrer hasta el ultimo nodo
 *  - insertar(i, x): O(i)
 *  - eliminar(i): O(i)
 *  - obtener(i): O(i)
 *  - indiceDe(x): O(n)
 */
public class ListaSimplementeEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaSimplementeEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Agrega al final de la lista. O(n). */
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

    /**
     * Inserta x en la posicion i (0-based). Admite i == tamanio. O(i).
     */
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

    /** Elimina y devuelve el elemento en la posicion i. O(i). */
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

    /** Devuelve el elemento en la posicion i. O(i). */
    public T obtener(int i) {
        validarIndice(i);
        return nodoEn(i).dato;
    }

    /**
     * Devuelve el indice de la primera aparicion de x, o -1 si no esta.
     * Usa Objects.equals para admitir null. O(n).
     */
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

    /** Prueba manual. */
    public static void main(String[] args) {
        ListaSimplementeEnlazada<String> lista = new ListaSimplementeEnlazada<>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");
        lista.insertar(1, "x");
        System.out.println("indiceDe(x): " + lista.indiceDe("x"));
        lista.eliminar(0);
        for (int i = 0; i < lista.tamanio(); i++) {
            System.out.println(i + " -> " + lista.obtener(i));
        }
    }
}
