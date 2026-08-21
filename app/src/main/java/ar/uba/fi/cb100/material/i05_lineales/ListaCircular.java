package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Objects;

/**
 * TDA {@link Lista} con una <b>lista circular</b> simplemente enlazada: el
 * {@code siguiente} del último nodo vuelve a apuntar al primero. Es útil para
 * recorrer en ronda (por ejemplo, turnos que se repiten). Mantenemos el
 * invariante {@code ultimo.siguiente == primero}.
 */
public class ListaCircular<T> implements Lista<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> siguiente;
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    private void cerrarCirculo() {
        if (ultimo != null) {
            ultimo.siguiente = primero;   // el último apunta al primero
        }
    }

    @Override
    public void agregar(T x) {
        Nodo<T> n = new Nodo<>(x);
        if (primero == null) { primero = ultimo = n; }
        else { ultimo.siguiente = n; ultimo = n; }
        cerrarCirculo();
        tamanio++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("índice " + i);
        }
        if (i == tamanio) { agregar(x); return; }
        Nodo<T> n = new Nodo<>(x);
        if (i == 0) {
            n.siguiente = primero;
            primero = n;
        } else {
            Nodo<T> anterior = nodoEn(i - 1);
            n.siguiente = anterior.siguiente;
            anterior.siguiente = n;
        }
        cerrarCirculo();
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
        if (i == 0) {
            eliminado = primero;
            primero = (tamanio == 1) ? null : primero.siguiente;
            if (tamanio == 1) {
                ultimo = null;
            }
        } else {
            Nodo<T> anterior = nodoEn(i - 1);
            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;
            if (eliminado == ultimo) {
                ultimo = anterior;
            }
        }
        cerrarCirculo();
        tamanio--;
        return eliminado.valor;
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
                sb.append(" -> ");
            }
            sb.append(n.valor);
        }
        return sb.append(" ->…)").toString();
    }

    public static void main(String[] args) {
        ListaCircular<String> c = new ListaCircular<>();
        c.agregar("lun"); c.agregar("mar"); c.agregar("mie");
        System.out.println(c + "  tamaño=" + c.tamanio());
        c.eliminar(1);                         // saca "mar"
        System.out.println(c + "  contiene(mie)? " + c.contiene("mie"));
    }
}
