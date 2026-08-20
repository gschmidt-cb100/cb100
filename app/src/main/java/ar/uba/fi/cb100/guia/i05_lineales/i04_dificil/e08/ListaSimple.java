package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e08;

/**
 * Lista simplemente enlazada con un metodo invertir() que da vuelta la lista
 * IN PLACE, es decir reenganchando los nodos existentes sin crear nodos nuevos.
 *
 * Idea de invertir(): se recorre la lista una sola vez llevando tres punteros
 * (previo, actual, proximo). En cada paso se hace que 'actual' apunte hacia
 * atras (a 'previo') y se avanza. Al terminar, 'previo' es la nueva cabeza.
 *
 * Complejidad:
 *  - agregar: O(n) (recorre hasta el final)
 *  - obtener(i): O(i)
 *  - invertir: O(n) tiempo, O(1) espacio adicional
 */
public class ListaSimple<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaSimple() {
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

    /** Devuelve el elemento en la posicion i. O(i). */
    public T obtener(int i) {
        if (i < 0 || i >= tamanio) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
        Nodo<T> actual = cabeza;
        for (int j = 0; j < i; j++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    /**
     * Invierte la lista IN PLACE reenganchando los nodos existentes.
     * No se crean nodos nuevos.
     */
    public void invertir() {
        Nodo<T> previo = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> proximo = actual.siguiente; // guardo antes de romper el enlace
            actual.siguiente = previo;          // reengancho hacia atras
            previo = actual;                    // avanzo previo
            actual = proximo;                   // avanzo actual
        }
        cabeza = previo; // el ultimo nodo visitado es la nueva cabeza
    }

    /** Prueba manual. */
    public static void main(String[] args) {
        ListaSimple<Integer> l = new ListaSimple<>();
        for (int i = 1; i <= 5; i++) {
            l.agregar(i);
        }
        l.invertir();
        for (int i = 0; i < l.tamanio(); i++) {
            System.out.print(l.obtener(i) + " "); // 5 4 3 2 1
        }
        System.out.println();
    }
}
