package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e05;

import java.util.Objects;

/**
 * Conjunto (sin elementos repetidos) implementado A MANO sobre una lista
 * simplemente enlazada de nodos propios. La igualdad se decide con
 * Objects.equals, admitiendo un unico null.
 *
 * Complejidad (n = tamanio):
 *  - contiene: O(n) (busqueda lineal)
 *  - agregar: O(n) (hay que verificar que no exista)
 *  - eliminar: O(n)
 *  - union(otro): O(n * m) siendo m el tamanio del otro conjunto
 *  - tamanio: O(1)
 */
public class ConjuntoLista<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ConjuntoLista() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** true si x pertenece al conjunto. O(n). */
    public boolean contiene(T x) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.dato, x)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Agrega x si no estaba. Devuelve true si efectivamente se agrego,
     * false si ya existia. O(n).
     */
    public boolean agregar(T x) {
        if (contiene(x)) {
            return false;
        }
        Nodo<T> nuevo = new Nodo<>(x);
        nuevo.siguiente = cabeza; // insercion al frente: O(1)
        cabeza = nuevo;
        tamanio++;
        return true;
    }

    /**
     * Elimina x del conjunto. Devuelve true si estaba y se elimino. O(n).
     */
    public boolean eliminar(T x) {
        Nodo<T> previo = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.dato, x)) {
                if (previo == null) {
                    cabeza = actual.siguiente;
                } else {
                    previo.siguiente = actual.siguiente;
                }
                tamanio--;
                return true;
            }
            previo = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Devuelve un NUEVO conjunto con la union de este y otro. No modifica los
     * originales. O(n * m).
     */
    public ConjuntoLista<T> union(ConjuntoLista<T> otro) {
        Objects.requireNonNull(otro, "El otro conjunto no puede ser null");
        ConjuntoLista<T> resultado = new ConjuntoLista<>();
        for (Nodo<T> actual = this.cabeza; actual != null; actual = actual.siguiente) {
            resultado.agregar(actual.dato);
        }
        for (Nodo<T> actual = otro.cabeza; actual != null; actual = actual.siguiente) {
            resultado.agregar(actual.dato);
        }
        return resultado;
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        ConjuntoLista<Integer> a = new ConjuntoLista<>();
        a.agregar(1);
        a.agregar(2);
        System.out.println("agregar 2 de nuevo: " + a.agregar(2)); // false
        ConjuntoLista<Integer> b = new ConjuntoLista<>();
        b.agregar(2);
        b.agregar(3);
        ConjuntoLista<Integer> u = a.union(b);
        System.out.println("tamanio union: " + u.tamanio()); // 3
    }
}
