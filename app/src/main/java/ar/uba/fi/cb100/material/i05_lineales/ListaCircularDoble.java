package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Objects;

/**
 * TDA {@link Lista} como <b>lista circular doblemente enlazada</b>: combina lo
 * mejor de las anteriores. Cada nodo conoce al {@code anterior} y al
 * {@code siguiente}, y el anillo se cierra en ambos sentidos
 * ({@code ultimo.siguiente == primero} y {@code primero.anterior == ultimo}).
 * Permite avanzar y retroceder en ronda, y agregar/quitar en cualquiera de los
 * dos extremos en O(1).
 */
public class ListaCircularDoble<T> implements Lista<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> anterior;
        Nodo<T> siguiente;
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> primero;   // el "último" es primero.anterior
    private int tamanio;

    @Override
    public void agregar(T x) {
        Nodo<T> n = new Nodo<>(x);
        if (primero == null) {
            n.siguiente = n;
            n.anterior = n;
            primero = n;
        } else {
            Nodo<T> ultimo = primero.anterior;
            n.anterior = ultimo;
            n.siguiente = primero;
            ultimo.siguiente = n;
            primero.anterior = n;
        }
        tamanio++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("índice " + i);
        }
        if (i == tamanio) { agregar(x); return; }
        Nodo<T> actual = nodoEn(i);
        Nodo<T> n = new Nodo<>(x);
        Nodo<T> ant = actual.anterior;
        n.anterior = ant;
        n.siguiente = actual;
        ant.siguiente = n;
        actual.anterior = n;
        if (i == 0) {
            primero = n;
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
        Nodo<T> nodo = nodoEn(i);
        if (tamanio == 1) {
            primero = null;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
            nodo.siguiente.anterior = nodo.anterior;
            if (nodo == primero) {
                primero = nodo.siguiente;
            }
        }
        tamanio--;
        return nodo.valor;
    }

    @Override
    public int indiceDe(T x) {
        Nodo<T> n = primero;
        for (int i = 0; i < tamanio; i++, n = n.siguiente) {
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

    private Nodo<T> nodoEn(int i) {
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
        StringBuilder sb = new StringBuilder("(");
        Nodo<T> n = primero;
        for (int i = 0; i < tamanio; i++, n = n.siguiente) {
            if (i > 0) {
                sb.append(" <-> ");
            }
            sb.append(n.valor);
        }
        return sb.append(" <->…)").toString();
    }

    public static void main(String[] args) {
        ListaCircularDoble<String> c = new ListaCircularDoble<>();
        c.agregar("a"); c.agregar("b"); c.agregar("c");
        c.insertar(0, "X");                 // (X <-> a <-> b <-> c)
        System.out.println(c);
        c.eliminar(2);                      // saca "b"
        System.out.println(c + "  obtener(0)=" + c.obtener(0));
    }
}
