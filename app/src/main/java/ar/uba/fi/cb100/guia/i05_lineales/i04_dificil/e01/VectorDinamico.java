package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e01;

import java.util.Arrays;

/**
 * Vector dinamico implementado A MANO sobre un arreglo, con redimension por
 * duplicacion de capacidad cuando el arreglo interno se llena.
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
public class VectorDinamico<T> {

    /** Capacidad inicial del arreglo interno. */
    private static final int CAPACIDAD_INICIAL = 4;

    /** Arreglo de respaldo. Se usa Object[] porque no se pueden crear T[]. */
    private Object[] datos;

    /** Cantidad de elementos efectivamente almacenados. */
    private int tamanio;

    public VectorDinamico() {
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    /** Cantidad de elementos almacenados. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Capacidad actual del arreglo interno. O(1). */
    public int capacidad() {
        return datos.length;
    }

    /** Agrega un elemento al final. O(1) amortizado. */
    public void agregar(T elemento) {
        if (tamanio == datos.length) {
            redimensionar(datos.length * 2);
        }
        datos[tamanio] = elemento;
        tamanio++;
    }

    /** Devuelve el elemento en la posicion i (0-based). O(1). */
    @SuppressWarnings("unchecked")
    public T obtener(int i) {
        validarIndice(i, tamanio - 1);
        return (T) datos[i];
    }

    /**
     * Inserta x en la posicion i corriendo hacia la derecha los elementos
     * desde i en adelante. Admite i == tamanio (insertar al final). O(n).
     */
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) {
            throw new IndexOutOfBoundsException("Indice invalido: " + i);
        }
        if (tamanio == datos.length) {
            redimensionar(datos.length * 2);
        }
        // Corro a la derecha desde el final hasta i.
        for (int j = tamanio; j > i; j--) {
            datos[j] = datos[j - 1];
        }
        datos[i] = x;
        tamanio++;
    }

    /**
     * Elimina el elemento en la posicion i corriendo hacia la izquierda los
     * elementos posteriores. Devuelve el elemento eliminado. O(n).
     */
    @SuppressWarnings("unchecked")
    public T eliminar(int i) {
        validarIndice(i, tamanio - 1);
        T eliminado = (T) datos[i];
        for (int j = i; j < tamanio - 1; j++) {
            datos[j] = datos[j + 1];
        }
        datos[tamanio - 1] = null; // Evita retener referencias.
        tamanio--;
        return eliminado;
    }

    private void redimensionar(int nuevaCapacidad) {
        datos = Arrays.copyOf(datos, nuevaCapacidad);
    }

    private void validarIndice(int i, int max) {
        if (i < 0 || i > max) {
            throw new IndexOutOfBoundsException(
                    "Indice fuera de rango: " + i + " (tamanio " + tamanio + ")");
        }
    }

    /** Prueba manual del vector dinamico. */
    public static void main(String[] args) {
        VectorDinamico<String> v = new VectorDinamico<>();
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
