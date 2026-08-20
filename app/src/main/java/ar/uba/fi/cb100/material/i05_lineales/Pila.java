package ar.uba.fi.cb100.material.i05_lineales;

/**
 * TDA <b>Pila</b> (stack): estructura lineal <b>LIFO</b> (Last In, First Out): el
 * último que entra es el primero que sale, como una pila de platos. Sólo se toca
 * un extremo: el <b>tope</b>.
 * <p>
 * La implementamos <b>apoyándonos en un {@link VectorDinamico}</b>: apilar y
 * desapilar operan al final del vector, así que son $O(1)$ (amortizado). Es un
 * ejemplo de construir una estructura sobre otra.
 */
public class Pila<T> {

    private final VectorDinamico<T> datos = new VectorDinamico<>();

    /** Pone {@code x} en el tope. */
    public void apilar(T x) {
        datos.agregar(x);                 // al final del vector
    }

    /** Quita y devuelve el elemento del tope. */
    public T desapilar() {
        if (estaVacia()) throw new IllegalStateException("la pila está vacía");
        return datos.eliminar(datos.tamanio() - 1);
    }

    /** Devuelve (sin quitar) el elemento del tope. */
    public T tope() {
        if (estaVacia()) throw new IllegalStateException("la pila está vacía");
        return datos.obtener(datos.tamanio() - 1);
    }

    public boolean estaVacia() { return datos.estaVacia(); }
    public int tamanio()       { return datos.tamanio(); }

    public static void main(String[] args) {
        Pila<String> p = new Pila<>();
        p.apilar("a"); p.apilar("b"); p.apilar("c");   // tope = c
        System.out.println("tope: " + p.tope());        // c
        System.out.println("saco: " + p.desapilar());   // c
        System.out.println("saco: " + p.desapilar());   // b (LIFO)
        System.out.println("tamaño: " + p.tamanio());    // 1
    }
}
