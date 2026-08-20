package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e04;

import java.util.Iterator;

/**
 * ABB que implementa Iterable, para poder recorrerlo con for-each:
 *
 *   for (Integer valor : arbol) { ... }
 *
 * El recorrido lo hace {@link IteradorEnOrden}, que devuelve los valores
 * de menor a mayor SIN recursion: usa una pila explicita de nodos.
 * La gracia es que el recorrido es "perezoso": no arma ninguna lista,
 * va entregando los valores de a uno a medida que se los piden.
 */
public class AbbIterable<T extends Comparable<T>> implements Iterable<T> {

    /**
     * Nodo del arbol. Es visible dentro del paquete para que el
     * iterador (que vive en otro archivo) pueda apilar nodos.
     */
    static class Nodo<T> {
        final T valor;
        Nodo<T> izq;
        Nodo<T> der;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> raiz;
    private int tamanio;

    /** Cantidad de valores almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Inserta el valor sin duplicados. O(h). */
    public void insertar(T valor) {
        raiz = insertar(raiz, valor);
    }

    private Nodo<T> insertar(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            tamanio++;
            return new Nodo<>(valor);
        }
        int comparacion = valor.compareTo(nodo.valor);
        if (comparacion < 0) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (comparacion > 0) {
            nodo.der = insertar(nodo.der, valor);
        }
        return nodo;
    }

    /** Indica si el valor esta en el arbol. O(h). */
    public boolean contiene(T valor) {
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = valor.compareTo(actual.valor);
            if (comparacion == 0) {
                return true;
            }
            actual = (comparacion < 0) ? actual.izq : actual.der;
        }
        return false;
    }

    /**
     * Metodo que pide la interfaz Iterable: devuelve un iterador nuevo
     * parado en el menor valor del arbol. Con esto el for-each funciona.
     */
    @Override
    public Iterator<T> iterator() {
        return new IteradorEnOrden<>(raiz);
    }

    /** Demostracion: el for-each recorre en orden sin armar listas. */
    public static void main(String[] args) {
        AbbIterable<Integer> arbol = new AbbIterable<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        System.out.print("for-each:");
        for (Integer valor : arbol) {
            System.out.print(" " + valor);
        }
        System.out.println();
    }
}
