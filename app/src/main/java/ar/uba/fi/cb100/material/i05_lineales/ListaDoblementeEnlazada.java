package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Objects;

/**
 * TDA {@link Lista} con <b>nodos doblemente enlazados</b>: cada nodo conoce al
 * {@code anterior} y al {@code siguiente}. Esto permite recorrer en los dos
 * sentidos y eliminar un nodo dado en O(1) (una vez ubicado). Para llegar a la
 * posición {@code i} recorremos desde el extremo más cercano.
 */
public class ListaDoblementeEnlazada<T> implements Lista<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> anterior;
        Nodo<T> siguiente;
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    @Override
    public void agregar(T x) {                 // al final: O(1)
        Nodo<T> n = new Nodo<>(x);
        if (ultimo == null) {
            primero = ultimo = n;
        } else {
            n.anterior = ultimo;
            ultimo.siguiente = n;
            ultimo = n;
        }
        tamanio++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) throw new IndexOutOfBoundsException("índice " + i);
        if (i == tamanio) { agregar(x); return; }
        Nodo<T> actual = nodoEn(i);
        Nodo<T> n = new Nodo<>(x);
        Nodo<T> ant = actual.anterior;
        n.siguiente = actual;
        n.anterior = ant;
        actual.anterior = n;
        if (ant == null) primero = n;          // insertó al principio
        else ant.siguiente = n;
        tamanio++;
    }

    @Override
    public T obtener(int i) {
        verificar(i);
        return nodoEn(i).valor;
    }

    @Override
    public T eliminar(int i) {
        verificar(i);
        Nodo<T> nodo = nodoEn(i);
        Nodo<T> ant = nodo.anterior, sig = nodo.siguiente;
        if (ant == null) primero = sig; else ant.siguiente = sig;
        if (sig == null) ultimo = ant; else sig.anterior = ant;
        tamanio--;
        return nodo.valor;
    }

    @Override
    public int indiceDe(T x) {
        int i = 0;
        for (Nodo<T> n = primero; n != null; n = n.siguiente, i++) {
            if (Objects.equals(n.valor, x)) return i;
        }
        return -1;
    }

    @Override public boolean contiene(T x) { return indiceDe(x) >= 0; }

    @Override
    public void agregarTodos(Lista<T> otra) {
        for (int i = 0; i < otra.tamanio(); i++) agregar(otra.obtener(i));
    }

    @Override public int tamanio()      { return tamanio; }
    @Override public boolean estaVacia() { return tamanio == 0; }

    /** Recorre desde el extremo más cercano a la posición {@code i}. */
    private Nodo<T> nodoEn(int i) {
        if (i < tamanio / 2) {
            Nodo<T> n = primero;
            for (int k = 0; k < i; k++) n = n.siguiente;
            return n;
        } else {
            Nodo<T> n = ultimo;
            for (int k = tamanio - 1; k > i; k--) n = n.anterior;
            return n;
        }
    }

    private void verificar(int i) {
        if (i < 0 || i >= tamanio) throw new IndexOutOfBoundsException("índice " + i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Nodo<T> n = primero; n != null; n = n.siguiente) {
            if (n != primero) sb.append(" <-> ");
            sb.append(n.valor);
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.agregar(1); l.agregar(2); l.agregar(3);
        l.insertar(1, 99);                     // [1 <-> 99 <-> 2 <-> 3]
        System.out.println(l);
        l.eliminar(0);                         // [99 <-> 2 <-> 3]
        System.out.println(l + "  obtener(2)=" + l.obtener(2));
    }
}
