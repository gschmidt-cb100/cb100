package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Objects;

/**
 * Implementación del TDA {@link Lista} con <b>nodos simplemente enlazados</b>:
 * cada nodo guarda su valor y una referencia al siguiente. Mantenemos punteros
 * al {@code primero} y al {@code ultimo} para agregar al final en O(1).
 * <p>
 * Costos: agregar al principio/final es O(1); {@code obtener}/{@code insertar}/
 * {@code eliminar} en una posición cualquiera son O(n) porque hay que recorrer
 * hasta ella. No hay acceso directo por índice como en el arreglo.
 */
public class ListaSimplementeEnlazada<T> implements Lista<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> siguiente;
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    @Override
    public void agregar(T x) {                 // al final: O(1)
        Nodo<T> n = new Nodo<>(x);
        if (primero == null) {
            primero = ultimo = n;
        } else { ultimo.siguiente = n; ultimo = n; }
        tamanio++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("índice " + i);
        }
        if (i == tamanio) { agregar(x); return; }
        Nodo<T> n = new Nodo<>(x);
        if (i == 0) {                          // al principio
            n.siguiente = primero;
            primero = n;
        } else {                               // en el medio: enganchar tras el (i-1)
            Nodo<T> anterior = nodoEn(i - 1);
            n.siguiente = anterior.siguiente;
            anterior.siguiente = n;
        }
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
        Nodo<T> eliminado;
        if (i == 0) {                          // quitar el primero
            eliminado = primero;
            primero = primero.siguiente;
            if (primero == null) {
                ultimo = null;
            }
        } else {                               // saltear el nodo i
            Nodo<T> anterior = nodoEn(i - 1);
            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;
            if (eliminado == ultimo) {
                ultimo = anterior;
            }
        }
        tamanio--;
        return eliminado.valor;
    }

    @Override
    public int indiceDe(T x) {
        int i = 0;
        for (Nodo<T> n = primero; n != null; n = n.siguiente, i++) {
            if (Objects.equals(n.valor, x)) {
                return i;
            }
        }
        return -1;
    }

    @Override public boolean contiene(T x) { return indiceDe(x) >= 0; }

    @Override
    public void agregarTodos(Lista<T> otra) {
        for (int i = 0; i < otra.tamanio(); i++) {
            agregar(otra.obtener(i));
        }
    }

    @Override public int tamanio()      { return tamanio; }
    @Override public boolean estaVacia() { return tamanio == 0; }

    private Nodo<T> nodoEn(int i) {             // recorre hasta la posición i
        Nodo<T> n = primero;
        for (int k = 0; k < i; k++) {
            n = n.siguiente;
        }
        return n;
    }

    private void verificar(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException("índice " + i);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Nodo<T> n = primero; n != null; n = n.siguiente) {
            if (n != primero) {
                sb.append(" -> ");
            }
            sb.append(n.valor);
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ListaSimplementeEnlazada<String> l = new ListaSimplementeEnlazada<>();
        l.agregar("a"); l.agregar("b"); l.agregar("c");
        l.insertar(0, "X");                    // [X -> a -> b -> c]
        System.out.println(l + "  contiene b? " + l.contiene("b"));
        l.eliminar(2);                         // saca "b"
        System.out.println(l + "  índiceDe(c)=" + l.indiceDe("c"));
    }
}
