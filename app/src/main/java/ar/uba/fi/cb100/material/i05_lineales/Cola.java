package ar.uba.fi.cb100.material.i05_lineales;

/**
 * TDA <b>Cola</b> (queue): estructura lineal <b>FIFO</b> (First In, First Out): el
 * primero que entra es el primero que sale, como la fila del banco. Se agrega por
 * el <b>fondo</b> y se saca por el <b>frente</b>.
 * <p>
 * La implementamos con <b>nodos enlazados</b> y punteros al {@code frente} y al
 * {@code fondo}, para que encolar y desencolar sean $O(1)$.
 */
public class Cola<T> {

    private static final class Nodo<T> {
        T valor;
        Nodo<T> siguiente;
        Nodo(T valor) { this.valor = valor; }
    }

    private Nodo<T> frente;   // por acá salen
    private Nodo<T> fondo;    // por acá entran
    private int tamanio;

    /** Agrega {@code x} al fondo de la cola. */
    public void encolar(T x) {
        Nodo<T> n = new Nodo<>(x);
        if (fondo == null) frente = fondo = n;
        else { fondo.siguiente = n; fondo = n; }
        tamanio++;
    }

    /** Quita y devuelve el elemento del frente. */
    public T desencolar() {
        if (estaVacia()) throw new IllegalStateException("la cola está vacía");
        T valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) fondo = null;
        tamanio--;
        return valor;
    }

    /** Devuelve (sin quitar) el elemento del frente. */
    public T frente() {
        if (estaVacia()) throw new IllegalStateException("la cola está vacía");
        return frente.valor;
    }

    public boolean estaVacia() { return tamanio == 0; }
    public int tamanio()       { return tamanio; }

    public static void main(String[] args) {
        Cola<String> c = new Cola<>();
        c.encolar("a"); c.encolar("b"); c.encolar("c");   // frente = a
        System.out.println("frente: " + c.frente());       // a
        System.out.println("atiendo: " + c.desencolar());  // a
        System.out.println("atiendo: " + c.desencolar());  // b (FIFO)
        System.out.println("tamaño: " + c.tamanio());       // 1
    }
}
