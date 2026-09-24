package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Arrays;

/**
 * TDA <b>Pila</b> (stack): estructura lineal <b>LIFO</b> (Last In, First Out): el
 * último que entra es el primero que sale, como una pila de platos. Sólo se toca
 * un extremo: el <b>tope</b>.
 * <p>
 * La implementamos con <b>la técnica del vector</b>: un arreglo interno que se
 * duplica cuando se llena. Apilar y desapilar operan al final del arreglo, así
 * que son O(1) (amortizado).
 */
public class Pila<T> {

    private T[] valores;      // el arreglo interno; el tope es valores[tamanio - 1]
    private int tamanio;

    @SuppressWarnings("unchecked")
    public Pila() {
        this.valores = (T[]) new Object[4];
        this.tamanio = 0;
    }

    /** Pone {@code x} en el tope. O(1) amortizado. */
    public void apilar(T x) {
        if (tamanio == valores.length) {
            valores = Arrays.copyOf(valores, valores.length * 2);
        }
        valores[tamanio++] = x;
    }

    /** Quita y devuelve el elemento del tope. O(1). */
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("la pila está vacía");
        }
        T valor = valores[--tamanio];
        valores[tamanio] = null;   // no retener la referencia (Unidad 2)
        return valor;
    }

    /** Devuelve (sin quitar) el elemento del tope. O(1). */
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("la pila está vacía");
        }
        return valores[tamanio - 1];
    }

    public boolean estaVacia() { return tamanio == 0; }
    public int tamanio()       { return tamanio; }

    public static void main(String[] args) {
        Pila<String> p = new Pila<>();
        p.apilar("a"); p.apilar("b"); p.apilar("c");   // tope = c
        System.out.println("tope: " + p.tope());        // c
        System.out.println("saco: " + p.desapilar());   // c
        System.out.println("saco: " + p.desapilar());   // b (LIFO)
        System.out.println("tamaño: " + p.tamanio());    // 1
    }
}
