package ar.uba.fi.cb100.material.i09_heaps;

import java.util.Arrays;

/**
 * <b>Min-heap binario</b> sobre un arreglo: el TDA Cola de Prioridad donde
 * siempre sale primero el <b>menor</b>. Es un árbol binario <b>completo</b>
 * guardado en un arreglo (sin nodos ni referencias): el padre de {@code i} está
 * en {@code (i-1)/2} y sus hijos en {@code 2i+1} y {@code 2i+2}.
 * <p>
 * Invariante de heap: <b>cada padre ≤ sus hijos</b> (no hay orden entre
 * hermanos). Encolar y desencolar cuestan O(log n); ver el mínimo, O(1);
 * y construir un heap desde un arreglo entero ({@link #heapify}), O(n).
 */
public class MinHeap<T extends Comparable<T>> {

    private Object[] datos;
    private int cantidad;

    public MinHeap() { this(8); }

    public MinHeap(int capacidadInicial) {
        datos = new Object[Math.max(1, capacidadInicial)];
    }

    /** Construye el heap desde un arreglo en O(n) (algoritmo de heapify). */
    public static <T extends Comparable<T>> MinHeap<T> heapify(T[] valores) {
        MinHeap<T> heap = new MinHeap<>(Math.max(1, valores.length));
        heap.datos = Arrays.copyOf(valores, valores.length, Object[].class);
        heap.cantidad = valores.length;
        // CLAVE: se hunde desde el ÚLTIMO NODO INTERNO hacia la raíz.
        // (Las hojas ya son heaps de un elemento: no hay nada que hundir.)
        for (int i = heap.cantidad / 2 - 1; i >= 0; i--) {
            heap.hundir(i);
        }
        return heap;
    }

    // ---------------------------------------------------------- operaciones
    /** Agrega un elemento: entra al final (última hoja) y FLOTA a su lugar. */
    public void encolar(T valor) {
        if (cantidad == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);   // redimensión (U4)
        }
        datos[cantidad] = valor;
        flotar(cantidad);
        cantidad++;
    }

    /** El mínimo, sin sacarlo: la raíz. O(1). */
    @SuppressWarnings("unchecked")
    public T verMinimo() {
        if (cantidad == 0) {
            throw new IllegalStateException("heap vacío");
        }
        return (T) datos[0];
    }

    /** Saca el mínimo: la raíz sale, la última hoja sube a raíz y se HUNDE. */
    @SuppressWarnings("unchecked")
    public T desencolarMinimo() {
        if (cantidad == 0) {
            throw new IllegalStateException("heap vacío");
        }
        T minimo = (T) datos[0];
        cantidad--;
        datos[0] = datos[cantidad];      // la última hoja pasa a la raíz
        datos[cantidad] = null;
        if (cantidad > 0) {
            hundir(0);   // y se hunde hasta su lugar
        }
        return minimo;
    }

    public int tamanio() { return cantidad; }
    public boolean estaVacio() { return cantidad == 0; }

    // ---------------------------------------------------------- flotar/hundir
    /** Sube el elemento mientras sea menor que su padre. O(log n). */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (comparar(i, padre) >= 0) {
                break;   // ya no es menor: quedó bien
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /**
     * Baja el elemento mientras sea mayor que alguno de sus hijos.
     * <b>El detalle que no perdona errores</b>: hay que intercambiar con el
     * MENOR de los dos hijos — comparar contra uno solo deja heaps inválidos.
     */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < cantidad && comparar(izquierdo, menor) < 0) {
                menor = izquierdo;
            }
            if (derecho < cantidad && comparar(derecho, menor) < 0) {
                menor = derecho;
            }
            if (menor == i) {
                break;   // ya es menor que ambos hijos
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    @SuppressWarnings("unchecked")
    private int comparar(int i, int j) {
        return ((T) datos[i]).compareTo((T) datos[j]);
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i]; datos[i] = datos[j]; datos[j] = aux;
    }

    /** Vista del arreglo interno (para las figuras y los tests). */
    public Object[] aArreglo() { return Arrays.copyOf(datos, cantidad); }

    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>();
        for (int v : new int[]{7, 3, 9, 1, 5}) {
            heap.encolar(v);
        }
        System.out.println(heap.verMinimo());          // 1
        System.out.print("salen: ");
        while (!heap.estaVacio()) {
            System.out.print(heap.desencolarMinimo() + " ");
        }
        System.out.println();                          // 1 3 5 7 9 (¡ordenados!)

        MinHeap<Integer> desdeArreglo = heapify(new Integer[]{12, 5, 9, 3, 20, 7});
        System.out.println(Arrays.toString(desdeArreglo.aArreglo()));  // [3, 5, 7, 12, 20, 9]
        System.out.println(desdeArreglo.desencolarMinimo());           // 3
    }
}
