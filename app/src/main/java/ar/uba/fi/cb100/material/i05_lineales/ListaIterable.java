package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Una lista simplemente enlazada <b>recorrible con for-each</b>. Muestra qué
 * hay detrás del {@code for (T x : lista)}: la lista implementa
 * {@link Iterable} y entrega un {@link Iterator}, que es un <b>cursor</b> con
 * dos preguntas: ¿hay siguiente? y dame el siguiente.
 *
 * <p>Es la razón por la que una lista enlazada, que no tiene acceso por
 * índice, igual se recorre en O(n) y no en O(n²): el cursor <b>recuerda dónde
 * está</b>. Recorrerla con {@code obtener(i)} en un for con índice costaría
 * O(n²), porque cada {@code obtener} arranca desde el primer nodo.
 */
public class ListaIterable<T> implements Iterable<T> {

    // ------------------------------------------------------------ la parte privada

    /** Un eslabón: el dato y la flecha al siguiente. Nadie de afuera lo ve. */
    private static final class Nodo<T> {
        T valor;
        Nodo<T> siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> primero;    // null si está vacía
    private Nodo<T> ultimo;     // para agregar al final en O(1)
    private int tamanio;

    // ------------------------------------------------------------ operaciones

    public void agregar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (primero == null) {
            primero = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
        tamanio++;
    }

    /** Llega a la posición caminando desde el primero: O(n). */
    public T obtener(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException("posición " + i);
        }
        Nodo<T> actual = primero;
        for (int k = 0; k < i; k++) {
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    public int tamanio() {
        return tamanio;
    }

    // ------------------------------------------------------------ el iterador

    /** Lo que llama el for-each. Cada llamada devuelve un cursor nuevo, parado antes del primero. */
    @Override
    public Iterator<T> iterator() {
        return new Cursor();
    }

    /**
     * El cursor. Es una clase interna (no static) porque necesita ver
     * {@code primero}. Guarda una sola cosa: el próximo nodo a devolver.
     */
    private final class Cursor implements Iterator<T> {
        private Nodo<T> proximo = primero;

        @Override
        public boolean hasNext() {
            return proximo != null;
        }

        @Override
        public T next() {
            if (proximo == null) {
                throw new NoSuchElementException("no hay más elementos");
            }
            T valor = proximo.valor;
            proximo = proximo.siguiente;     // avanzar UNA flecha: O(1)
            return valor;
        }
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        for (Nodo<T> n = primero; n != null; n = n.siguiente) {
            if (n != primero) {
                texto.append(" -> ");
            }
            texto.append(n.valor);
        }
        return texto.append("]").toString();
    }

    public static void main(String[] args) {
        ListaIterable<String> lista = new ListaIterable<>();
        lista.agregar("a");
        lista.agregar("b");
        lista.agregar("c");
        System.out.println("lista: " + lista);

        // 1. Recorrido "a mano", siguiendo las flechas: lo que hace el iterador por dentro.
        //    (Desde afuera no se puede, Nodo es privado; esto es lo que pasa adentro.)

        // 2. Con el Iterator explícito.
        System.out.print("iterator:  ");
        Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // 3. Con for-each: funciona porque la clase implementa Iterable.
        System.out.print("for-each:  ");
        for (String x : lista) {
            System.out.print(x + " ");
        }
        System.out.println();

        // 4. Con índice: FUNCIONA, pero cada obtener(i) camina desde el principio.
        //    Para n elementos son 0 + 1 + 2 + ... + (n-1) pasos: O(n²).
        System.out.print("indice:    ");
        for (int i = 0; i < lista.tamanio(); i++) {
            System.out.print(lista.obtener(i) + " ");
        }
        System.out.println("  <- anda, pero es O(n^2): no lo hagas con listas enlazadas");
    }
}
