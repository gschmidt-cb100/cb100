package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e03;

import java.util.NoSuchElementException;

/**
 * ABB con las consultas de orden: minimo, maximo, piso y techo.
 *
 * piso(x): el MAYOR valor del arbol que sea menor o igual a x.
 * techo(x): el MENOR valor del arbol que sea mayor o igual a x.
 * Son las operaciones que un HashMap no puede ofrecer y un arbol si:
 * aprovechan que el arbol guarda los valores ordenados.
 *
 * Las cuatro consultas son ITERATIVAS y cuestan O(h): bajamos una sola
 * rama desde la raiz, sin recorrer todo el arbol.
 */
public class AbbConPisoYTecho<T extends Comparable<T>> {

    /** Nodo del arbol: un valor y sus dos hijos. */
    private static class Nodo<T> {
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

    /** El menor valor del arbol: bajar siempre por la izquierda. O(h). */
    public T minimo() {
        if (raiz == null) {
            throw new NoSuchElementException("El arbol esta vacio");
        }
        Nodo<T> actual = raiz;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual.valor;
    }

    /** El mayor valor del arbol: bajar siempre por la derecha. O(h). */
    public T maximo() {
        if (raiz == null) {
            throw new NoSuchElementException("El arbol esta vacio");
        }
        Nodo<T> actual = raiz;
        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.valor;
    }

    /**
     * El mayor valor del arbol que sea menor o igual a x,
     * o null si todos los valores son mayores que x. O(h).
     */
    public T piso(T x) {
        Nodo<T> actual = raiz;
        T candidato = null;
        while (actual != null) {
            int comparacion = x.compareTo(actual.valor);
            if (comparacion == 0) {
                return actual.valor; // x esta en el arbol: es su propio piso.
            }
            if (comparacion > 0) {
                // actual.valor <= x: sirve como piso, pero puede haber uno
                // mas grande (mas cercano a x) en el subarbol derecho.
                candidato = actual.valor;
                actual = actual.der;
            } else {
                // actual.valor > x: no sirve, buscamos por la izquierda.
                actual = actual.izq;
            }
        }
        return candidato;
    }

    /**
     * El menor valor del arbol que sea mayor o igual a x,
     * o null si todos los valores son menores que x. O(h).
     */
    public T techo(T x) {
        Nodo<T> actual = raiz;
        T candidato = null;
        while (actual != null) {
            int comparacion = x.compareTo(actual.valor);
            if (comparacion == 0) {
                return actual.valor; // x esta en el arbol: es su propio techo.
            }
            if (comparacion < 0) {
                // actual.valor >= x: sirve como techo, pero puede haber uno
                // mas chico (mas cercano a x) en el subarbol izquierdo.
                candidato = actual.valor;
                actual = actual.izq;
            } else {
                // actual.valor < x: no sirve, buscamos por la derecha.
                actual = actual.der;
            }
        }
        return candidato;
    }

    /** Demostracion con decenas: piso y techo de valores intermedios. */
    public static void main(String[] args) {
        AbbConPisoYTecho<Integer> arbol = new AbbConPisoYTecho<>();
        for (int valor : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            arbol.insertar(valor);
        }
        System.out.println("minimo    = " + arbol.minimo());
        System.out.println("maximo    = " + arbol.maximo());
        System.out.println("piso(45)  = " + arbol.piso(45));  // 40
        System.out.println("techo(45) = " + arbol.techo(45)); // 50
        System.out.println("piso(10)  = " + arbol.piso(10));  // null: todo es mayor.
        System.out.println("techo(90) = " + arbol.techo(90)); // null: todo es menor.
    }
}
