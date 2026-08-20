package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e05;

import java.util.Comparator;

/**
 * Heap generico A MANO parametrizado con un Comparator: el MISMO codigo
 * sirve como min-heap o como max-heap segun el comparador que se pase.
 *
 * La unica diferencia con un min-heap de Comparable es que en vez de
 * valor.compareTo(otro) usamos comparador.compare(valor, otro). El heap
 * mantiene en la raiz al elemento "mas chico segun el comparador":
 *  - Comparator.naturalOrder()  -> min-heap clasico.
 *  - Comparator.reverseOrder()  -> max-heap (la raiz es el maximo).
 *  - Comparator.comparing(...)  -> prioridades por cualquier campo.
 *
 * Esta es la misma idea que usa java.util.PriorityQueue, pero aca la
 * escribimos nosotros. Complejidades: encolar y desencolar O(log n),
 * verPrimero O(1), con redimension x2 amortizada.
 */
public class Monticulo<T> {

    private static final int CAPACIDAD_INICIAL = 8;

    /** Decide quien tiene mas prioridad: "menor segun el comparador" sale primero. */
    private final Comparator<T> comparador;

    /** Arreglo que guarda el arbol por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    public Monticulo(Comparator<T> comparador) {
        if (comparador == null) {
            throw new IllegalArgumentException("El comparador no puede ser null");
        }
        this.comparador = comparador;
        this.datos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    /** Cantidad de elementos. O(1). */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si el monticulo esta vacio. O(1). */
    public boolean estaVacio() {
        return tamanio == 0;
    }

    /**
     * Devuelve (sin sacar) el elemento de mayor prioridad: el menor segun
     * el comparador.
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T verPrimero() {
        if (estaVacio()) {
            throw new IllegalStateException("El monticulo esta vacio");
        }
        return elemento(0);
    }

    /** Agrega un elemento: entra como ultima hoja y flota. O(log n). */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        asegurarCapacidad();
        datos[tamanio] = valor;
        flotar(tamanio);
        tamanio++;
    }

    /** Saca y devuelve el de mayor prioridad: ultima hoja a la raiz + hundir. O(log n). */
    public T desencolar() {
        T primero = verPrimero();
        tamanio--;
        datos[0] = datos[tamanio];
        datos[tamanio] = null;
        if (tamanio > 0) {
            hundir(0);
        }
        return primero;
    }

    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamanio);
            datos = nuevo;
        }
    }

    /** Sube el elemento de i mientras el comparador diga que es menor que su padre. */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (comparador.compare(elemento(i), elemento(padre)) >= 0) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    /** Baja el elemento de i comparando con el MENOR (segun el comparador) de sus hijos. */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamanio && comparador.compare(elemento(izquierdo), elemento(menor)) < 0) {
                menor = izquierdo;
            }
            if (derecho < tamanio && comparador.compare(elemento(derecho), elemento(menor)) < 0) {
                menor = derecho;
            }
            if (menor == i) {
                break;
            }
            intercambiar(i, menor);
            i = menor;
        }
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }

    @SuppressWarnings("unchecked")
    private T elemento(int i) {
        return (T) datos[i];
    }

    /** Demostracion: el mismo heap como min-heap y como max-heap. */
    public static void main(String[] args) {
        Monticulo<Integer> minimos = new Monticulo<>(Comparator.<Integer>naturalOrder());
        Monticulo<Integer> maximos = new Monticulo<>(Comparator.<Integer>reverseOrder());
        for (int valor : new int[] {42, 17, 99, 3, 25}) {
            minimos.encolar(valor);
            maximos.encolar(valor);
        }
        System.out.println("Con naturalOrder sale primero: " + minimos.verPrimero()); // 3
        System.out.println("Con reverseOrder sale primero: " + maximos.verPrimero()); // 99
    }
}
