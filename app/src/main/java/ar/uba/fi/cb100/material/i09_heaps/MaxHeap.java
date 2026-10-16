package ar.uba.fi.cb100.material.i09_heaps;

import java.util.Arrays;

/**
 * <b>Max-heap binario</b> sobre un arreglo: el TDA Cola de Prioridad donde
 * siempre sale primero el <b>mayor</b>. Es el espejo exacto de {@link MinHeap}:
 * misma forma (árbol binario completo en un arreglo, padre en {@code (i-1)/2},
 * hijos en {@code 2i+1} y {@code 2i+2}) y el invariante al revés:
 * <b>cada padre ≥ sus hijos</b>, así que el máximo está siempre en la raíz.
 * <p>
 * Lo único que cambia respecto del min-heap son las <b>comparaciones</b>:
 * flotar sube mientras el elemento sea <i>mayor</i> que su padre, y hundir baja
 * intercambiando con el <i>mayor</i> de los dos hijos. Todo lo demás (dónde
 * entra el nuevo, quién reemplaza a la raíz, heapify desde el último interno)
 * es idéntico.
 * <p>
 * Es el heap que usa {@link Heapsort} y el que se usa para "los k menores" de
 * un flujo (max-heap de tamaño k). Con la API: {@code new PriorityQueue<>(Comparator.reverseOrder())}.
 */
public class MaxHeap<T extends Comparable<T>> {

    private Object[] datos;
    private int cantidad;

    public MaxHeap() { this(8); }

    public MaxHeap(int capacidadInicial) {
        datos = new Object[Math.max(1, capacidadInicial)];
    }

    /** Construye el max-heap desde un arreglo en O(n) (heapify). */
    public static <T extends Comparable<T>> MaxHeap<T> heapify(T[] valores) {
        MaxHeap<T> heap = new MaxHeap<>(Math.max(1, valores.length));
        heap.datos = Arrays.copyOf(valores, valores.length, Object[].class);
        heap.cantidad = valores.length;
        for (int i = heap.cantidad / 2 - 1; i >= 0; i--) {   // del último interno a la raíz
            heap.hundir(i);
        }
        return heap;
    }

    // ---------------------------------------------------------- operaciones
    /** Agrega un elemento: entra al final (última hoja) y FLOTA mientras sea mayor que su padre. */
    public void encolar(T valor) {
        if (cantidad == datos.length) {
            datos = Arrays.copyOf(datos, datos.length * 2);
        }
        datos[cantidad] = valor;
        flotar(cantidad);
        cantidad++;
    }

    /** El máximo, sin sacarlo: la raíz. O(1). */
    @SuppressWarnings("unchecked")
    public T verMaximo() {
        if (cantidad == 0) {
            throw new IllegalStateException("heap vacío");
        }
        return (T) datos[0];
    }

    /** Saca el máximo: la raíz sale, la última hoja sube a raíz y se HUNDE. */
    @SuppressWarnings("unchecked")
    public T desencolarMaximo() {
        if (cantidad == 0) {
            throw new IllegalStateException("heap vacío");
        }
        T maximo = (T) datos[0];
        cantidad--;
        datos[0] = datos[cantidad];
        datos[cantidad] = null;
        if (cantidad > 0) {
            hundir(0);
        }
        return maximo;
    }

    public int tamanio() { return cantidad; }
    public boolean estaVacio() { return cantidad == 0; }

    // ---------------------------------------------------------- flotar/hundir
    /** Sube el elemento mientras sea MAYOR que su padre. O(log n). */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (comparar(i, padre) <= 0) {
                break;   // ya no es mayor que el padre: quedó bien
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /**
     * Baja el elemento mientras sea menor que alguno de sus hijos, intercambiando
     * con el MAYOR de los dos (el espejo del bug clásico: acá el error sería
     * comparar contra un solo hijo).
     */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int mayor = i;
            if (izquierdo < cantidad && comparar(izquierdo, mayor) > 0) {
                mayor = izquierdo;
            }
            if (derecho < cantidad && comparar(derecho, mayor) > 0) {
                mayor = derecho;
            }
            if (mayor == i) {
                break;   // ya es mayor que ambos hijos
            }
            intercambiar(i, mayor);
            i = mayor;
        }
    }

    @SuppressWarnings("unchecked")
    private int comparar(int i, int j) {
        return ((T) datos[i]).compareTo((T) datos[j]);
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i]; datos[i] = datos[j]; datos[j] = aux;
    }

    /** Vista del arreglo interno (para las figuras, los tests y el generador de ejercicios). */
    public Object[] aArreglo() { return Arrays.copyOf(datos, cantidad); }

    public static void main(String[] args) {
        MaxHeap<Integer> heap = new MaxHeap<>();
        for (int v : new int[]{7, 3, 9, 1, 5}) {
            heap.encolar(v);
        }
        System.out.println(heap.verMaximo());          // 9
        System.out.print("salen: ");
        while (!heap.estaVacio()) {
            System.out.print(heap.desencolarMaximo() + " ");
        }
        System.out.println();                          // 9 7 5 3 1 (descendente)

        MaxHeap<Integer> desdeArreglo = heapify(new Integer[]{12, 5, 9, 3, 20, 7});
        System.out.println(Arrays.toString(desdeArreglo.aArreglo()));  // [20, 12, 9, 3, 5, 7]
        System.out.println(desdeArreglo.desencolarMaximo());           // 20
    }
}
