package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e09;

import java.util.NoSuchElementException;

/**
 * Lista simplemente enlazada con el metodo medio(), que encuentra el nodo del
 * medio con la tecnica de dos punteros (lento/rapido, "liebre y tortuga"):
 * el puntero rapido avanza de a dos y el lento de a uno; cuando el rapido
 * llega al final, el lento esta en el medio. Asi se recorre la lista UNA sola
 * vez sin conocer de antemano el largo.
 *
 * CONVENCION para largo par: se devuelve el SEGUNDO de los dos elementos
 * centrales (p. ej. en [1,2,3,4] devuelve 3). Se aclara en el test.
 *
 * Complejidad:
 *  - agregar: O(n)
 *  - medio: O(n) tiempo, O(1) espacio
 */
public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Agrega al final. O(n). */
    public void agregar(T x) {
        Nodo<T> nuevo = new Nodo<>(x);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    /**
     * Devuelve el dato del nodo del medio usando dos punteros. Para largo par
     * devuelve el segundo de los dos centrales.
     */
    public T medio() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista esta vacia");
        }
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;
        // El rapido avanza de a dos; cuando no puede, el lento quedo en el medio.
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento.dato;
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        ListaEnlazada<Integer> impar = new ListaEnlazada<>();
        for (int i = 1; i <= 5; i++) {
            impar.agregar(i);
        }
        System.out.println("medio impar [1..5]: " + impar.medio()); // 3

        ListaEnlazada<Integer> par = new ListaEnlazada<>();
        for (int i = 1; i <= 4; i++) {
            par.agregar(i);
        }
        System.out.println("medio par [1..4]: " + par.medio()); // 3
    }
}
