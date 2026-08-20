package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e01;

import java.util.Arrays;

/**
 * Pila (LIFO) implementada sobre un arreglo que se redimensiona
 * automáticamente cuando se llena. No usa colecciones del JDK.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class PilaArregloDinamica<T> {

    private static final int CAPACIDAD_INICIAL = 4;

    private Object[] datos;
    private int cantidad;

    public PilaArregloDinamica() {
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    /** Apila un elemento; duplica la capacidad si el arreglo está lleno. */
    public void apilar(T elemento) {
        if (cantidad == datos.length) {
            redimensionar(datos.length * 2);
        }
        datos[cantidad++] = elemento;
    }

    /** Desapila y devuelve el elemento del tope (el último apilado). */
    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T elemento = (T) datos[--cantidad];
        datos[cantidad] = null; // evita retener referencias innecesarias
        return elemento;
    }

    /** Devuelve el tope sin quitarlo. */
    @SuppressWarnings("unchecked")
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return (T) datos[cantidad - 1];
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    private void redimensionar(int nuevaCapacidad) {
        datos = Arrays.copyOf(datos, nuevaCapacidad);
    }

    public static void main(String[] args) {
        PilaArregloDinamica<Integer> pila = new PilaArregloDinamica<>();
        // Apilamos más elementos que la capacidad inicial (4) para forzar redimensiones.
        for (int i = 1; i <= 10; i++) {
            pila.apilar(i);
        }
        System.out.println("Tamaño: " + pila.tamanio());
        System.out.println("Tope: " + pila.tope());
        System.out.print("Desapilando (LIFO): ");
        while (!pila.estaVacia()) {
            System.out.print(pila.desapilar() + " ");
        }
        System.out.println();
    }
}
