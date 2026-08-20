package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e06;

/**
 * Lista simplemente enlazada construida con nodos propios (sin colecciones
 * del JDK). Mantiene referencias a la cabeza y a la cola para agregar en O(1)
 * al final.
 *
 * @param <T> tipo de los elementos
 */
public class ListaEnlazada<T> {

    /** Nodo interno de la lista enlazada. */
    private static final class Nodo<T> {
        private final T valor;
        private Nodo<T> siguiente;

        private Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int cantidad;

    /** Agrega un elemento al final de la lista. */
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        cantidad++;
    }

    /** Devuelve el elemento en la posición indicada (0-based). */
    public T obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    public int tamanio() {
        return cantidad;
    }

    public static void main(String[] args) {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");
        System.out.println("Tamaño: " + lista.tamanio());
        for (int i = 0; i < lista.tamanio(); i++) {
            System.out.println("  [" + i + "] = " + lista.obtener(i));
        }
    }
}
