package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * TDA Vector propio: un vector dinamico implementado A MANO sobre un arreglo,
 * con redimension por duplicacion de capacidad cuando el arreglo interno se
 * llena. Es la implementacion de referencia del apunte (Unidad 5).
 *
 * IMPORTANTE: esta clase vive en la guia A PROPOSITO. En la practica, los TP
 * y los parciales se usa java.util.ArrayList; este Vector es para entender
 * COMO funciona por dentro.
 *
 * Estrategia de redimension: al agregar sobre un arreglo lleno, se crea un
 * arreglo del doble de capacidad y se copian los elementos.
 *
 * Complejidad (n = tamanio):
 *  - obtener / tamanio / capacidad: O(1)
 *  - agregar: O(1) amortizado (O(n) puntual cuando duplica)
 *  - insertar(i, x): O(n) por el corrimiento de elementos
 *  - eliminar(i): O(n) por el corrimiento de elementos
 *
 * El costo amortizado de agregar es O(1) porque la suma de todas las copias
 * al ir duplicando (1 + 2 + 4 + ... + n) es < 2n, es decir O(n) en total
 * repartido entre n inserciones.
 */
public class Vector<T> {

    /** Capacidad inicial del arreglo interno. */
    private static final int CAPACIDAD_INICIAL = 4;

    /** El arreglo interno: la capacidad es valores.length. Como en Java no
     *  existe "new T[]", se crea un Object[] y se castea UNA sola vez aca. */
    private T[] valores;

    /** Cantidad de elementos efectivamente almacenados. */
    private int tamanio;

    @SuppressWarnings("unchecked")
    public Vector() {
        this.valores = (T[]) new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    /** Cantidad de elementos almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Capacidad actual del arreglo interno. O(1). */
    public int capacidad() {
        return valores.length;
    }

    /** Agrega un elemento al final. O(1) amortizado. */
    public void agregar(T elemento) {
        if (tamanio == valores.length) {
            redimensionar(valores.length * 2);
        }
        valores[tamanio] = elemento;
        tamanio++;
    }

    /** Devuelve el elemento en la posicion i (0-based). O(1). */
    public T obtener(int i) {
        validarIndice(i, tamanio - 1);
        return valores[i];
    }

    /**
     * Inserta x en la posicion i corriendo hacia la derecha los elementos
     * desde i en adelante. Admite i == tamanio (insertar al final). O(n).
     */
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("Indice invalido: " + i);
        }
        if (tamanio == valores.length) {
            redimensionar(valores.length * 2);
        }
        // Corro a la derecha desde el final hasta i.
        for (int j = tamanio; j > i; j--) {
            valores[j] = valores[j - 1];
        }
        valores[i] = x;
        tamanio++;
    }

    /**
     * Elimina el elemento en la posicion i corriendo hacia la izquierda los
     * elementos posteriores. Devuelve el elemento eliminado. O(n).
     */
    public T eliminar(int i) {
        validarIndice(i, tamanio - 1);
        T eliminado = valores[i];
        for (int j = i; j < tamanio - 1; j++) {
            valores[j] = valores[j + 1];
        }
        valores[tamanio - 1] = null; // Evita retener referencias.
        tamanio--;
        return eliminado;
    }


    /** Indica si no hay elementos. O(1). */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Primera posicion de x (comparando con equals), o -1 si no esta. O(n). */
    public int indiceDe(T x) {
        for (int i = 0; i < tamanio; i++) {
            if (Objects.equals(valores[i], x)) {
                return i;
            }
        }
        return -1;
    }

    /** Indica si x esta en el vector. O(n). */
    public boolean contiene(T x) {
        return indiceDe(x) != -1;
    }

    /** Agrega al final todos los elementos de otro vector, en orden. O(m) amortizado. */
    public void agregarTodos(Vector<T> otro) {
        for (int i = 0; i < otro.tamanio(); i++) {
            agregar(otro.obtener(i));
        }
    }

    /** Ordena los elementos segun el comparador. O(n log n). */
    public void ordenar(Comparator<? super T> comparador) {
        Arrays.sort(valores, 0, tamanio, comparador);
    }

    private void redimensionar(int nuevaCapacidad) {
        valores = Arrays.copyOf(valores, nuevaCapacidad);
    }

    private void validarIndice(int i, int max) {
        if (i < 0 || i > max) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
    }

    /** Prueba manual del TDA Vector. */
    public static void main(String[] args) {
        Vector<String> v = new Vector<>();
        System.out.println("Capacidad inicial: " + v.capacidad());
        for (int i = 0; i < 6; i++) {
            v.agregar("e" + i);
        }
        System.out.println("Tamanio: " + v.tamanio() + " capacidad: " + v.capacidad());
        v.insertar(0, "primero");
        v.eliminar(3);
        for (int i = 0; i < v.tamanio(); i++) {
            System.out.println(i + " -> " + v.obtener(i));
        }
    }
}
