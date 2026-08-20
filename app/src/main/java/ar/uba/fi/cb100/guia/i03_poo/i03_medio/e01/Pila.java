package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e01;

import java.util.Arrays;

/**
 * TDA Pila (LIFO) implementado sobre un arreglo redimensionable.
 * No se usan colecciones del JDK: el almacenamiento es un arreglo propio.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Pila<T> {

    private static final int CAPACIDAD_INICIAL = 8;

    private T[] datos;   // arreglo interno de almacenamiento
    private int cantidad; // cantidad de elementos actualmente apilados

    @SuppressWarnings("unchecked")
    public Pila() {
        // No se puede crear un arreglo genérico directamente: se crea Object[] y se castea.
        this.datos = (T[]) new Object[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    /** Apila un elemento en el tope de la pila. */
    public void apilar(T elemento) {
        if (cantidad == datos.length) {
            redimensionar(datos.length * 2);
        }
        datos[cantidad] = elemento;
        cantidad++;
    }

    /** Quita y devuelve el elemento en el tope. Lanza excepción si la pila está vacía. */
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desapilar: la pila está vacía");
        }
        cantidad--;
        T elemento = datos[cantidad];
        datos[cantidad] = null; // se libera la referencia para permitir GC
        return elemento;
    }

    /** Devuelve (sin quitar) el elemento en el tope. Lanza excepción si está vacía. */
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("No hay tope: la pila está vacía");
        }
        return datos[cantidad - 1];
    }

    /** Indica si la pila no tiene elementos. */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /** Cantidad de elementos apilados. */
    public int tamanio() {
        return cantidad;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar(int nuevaCapacidad) {
        T[] nuevo = (T[]) new Object[nuevaCapacidad];
        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = datos[i];
        }
        datos = nuevo;
    }

    public static void main(String[] args) {
        Pila<String> pila = new Pila<>();
        System.out.println("¿Vacía al inicio? " + pila.estaVacia());

        pila.apilar("a");
        pila.apilar("b");
        pila.apilar("c");
        System.out.println("Tamaño: " + pila.tamanio());
        System.out.println("Tope: " + pila.tope());

        // Se desapila en orden LIFO: c, b, a
        System.out.println("Desapila: " + pila.desapilar());
        System.out.println("Desapila: " + pila.desapilar());
        System.out.println("Desapila: " + pila.desapilar());
        System.out.println("¿Vacía al final? " + pila.estaVacia());
        System.out.println("Contenido interno: " + Arrays.toString(new Object[0]));
    }
}
