package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * e01 - Lista que se mantiene SIEMPRE ordenada, construida sobre un ArrayList.
 *
 * La gracia esta en insertar: en vez de agregar y reordenar (O(n log n)), se
 * busca la posicion con busqueda binaria y se inserta ahi. Se aprovecha el
 * valor negativo que devuelve Collections.binarySearch cuando el elemento no
 * esta: -(punto de insercion) - 1, o sea, exactamente donde hay que ponerlo.
 *
 * Complejidad (n = tamanio):
 *  - contiene / indiceDe: O(log n), busqueda binaria (la lista esta ordenada)
 *  - insertar: O(log n) para ubicar + O(n) para correr los que siguen = O(n)
 *  - eliminar: idem, O(n)
 *  - obtener(i) / minimo / maximo / tamanio: O(1)
 *
 * Se admiten duplicados: quedan contiguos.
 *
 * @param <T> tipo de los elementos; tiene que saber compararse consigo mismo
 */
public class ListaOrdenada<T extends Comparable<T>> {

    private final List<T> elementos = new ArrayList<>();

    /** Inserta x en la posicion que le corresponde para que la lista siga ordenada. */
    public void insertar(T x) {
        Objects.requireNonNull(x, "no se puede insertar null");
        int posicion = Collections.binarySearch(elementos, x);
        if (posicion < 0) {
            posicion = -posicion - 1;      // no estaba: binarySearch dice donde iria
        }
        elementos.add(posicion, x);        // si estaba, queda al lado de su igual
    }

    /** Busqueda binaria: O(log n). */
    public boolean contiene(T x) {
        return indiceDe(x) >= 0;
    }

    /** Una posicion donde esta x, o -1. Con duplicados, cualquiera de ellas. */
    public int indiceDe(T x) {
        Objects.requireNonNull(x, "no se puede buscar null");
        int posicion = Collections.binarySearch(elementos, x);
        return posicion >= 0 ? posicion : -1;
    }

    /** Elimina una aparicion de x. Devuelve false si no estaba. */
    public boolean eliminar(T x) {
        int posicion = indiceDe(x);
        if (posicion < 0) {
            return false;
        }
        elementos.remove(posicion);
        return true;
    }

    public T obtener(int i) {
        return elementos.get(i);           // ArrayList ya valida el rango
    }

    public T minimo() {
        verificarNoVacia();
        return elementos.get(0);
    }

    public T maximo() {
        verificarNoVacia();
        return elementos.get(elementos.size() - 1);
    }

    public int tamanio() {
        return elementos.size();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    private void verificarNoVacia() {
        if (elementos.isEmpty()) {
            throw new IllegalStateException("la lista esta vacia");
        }
    }

    @Override
    public String toString() {
        return elementos.toString();
    }

    public static void main(String[] args) {
        ListaOrdenada<Integer> lista = new ListaOrdenada<>();
        for (int x : new int[]{42, 7, 19, 7, 3, 25}) {
            lista.insertar(x);
            System.out.println("insertar(" + x + ") -> " + lista);
        }
        System.out.println("contiene(19) = " + lista.contiene(19) + "   contiene(20) = " + lista.contiene(20));
        System.out.println("minimo = " + lista.minimo() + "   maximo = " + lista.maximo());
        lista.eliminar(7);
        System.out.println("eliminar(7)  -> " + lista + "   (queda el otro 7)");
    }
}
