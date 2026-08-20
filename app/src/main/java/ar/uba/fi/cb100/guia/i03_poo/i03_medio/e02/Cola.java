package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e02;

/**
 * TDA Cola (FIFO) implementado sobre un arreglo circular redimensionable.
 * No se usan colecciones del JDK.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Cola<T> {

    private static final int CAPACIDAD_INICIAL = 8;

    private T[] datos;    // arreglo interno (usado de forma circular)
    private int frente;   // índice del primer elemento
    private int cantidad; // cantidad de elementos encolados

    @SuppressWarnings("unchecked")
    public Cola() {
        this.datos = (T[]) new Object[CAPACIDAD_INICIAL];
        this.frente = 0;
        this.cantidad = 0;
    }

    /** Agrega un elemento al final de la cola. */
    public void encolar(T elemento) {
        if (cantidad == datos.length) {
            redimensionar(datos.length * 2);
        }
        int fin = (frente + cantidad) % datos.length;
        datos[fin] = elemento;
        cantidad++;
    }

    /** Quita y devuelve el elemento del frente. Lanza excepción si está vacía. */
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desencolar: la cola está vacía");
        }
        T elemento = datos[frente];
        datos[frente] = null; // libera la referencia
        frente = (frente + 1) % datos.length;
        cantidad--;
        return elemento;
    }

    /** Devuelve (sin quitar) el elemento del frente. Lanza excepción si está vacía. */
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("No hay frente: la cola está vacía");
        }
        return datos[frente];
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar(int nuevaCapacidad) {
        T[] nuevo = (T[]) new Object[nuevaCapacidad];
        // Se "desenrolla" el arreglo circular dejando el frente en el índice 0.
        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = datos[(frente + i) % datos.length];
        }
        datos = nuevo;
        frente = 0;
    }

    public static void main(String[] args) {
        Cola<String> cola = new Cola<>();
        cola.encolar("primero");
        cola.encolar("segundo");
        cola.encolar("tercero");

        System.out.println("Frente: " + cola.frente());
        System.out.println("Tamaño: " + cola.tamanio());

        // Sale en orden FIFO: primero, segundo, tercero
        System.out.println("Desencola: " + cola.desencolar());
        System.out.println("Desencola: " + cola.desencolar());
        System.out.println("Desencola: " + cola.desencolar());
        System.out.println("¿Vacía? " + cola.estaVacia());
    }
}
