package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e03;

import java.util.Arrays;

/**
 * Min-heap A MANO construido con HEAPIFY: en vez de encolar de a uno
 * (n inserciones, O(n log n)), copiamos el arreglo completo y hundimos
 * cada nodo interno desde n/2 - 1 hasta 0.
 *
 * Por que n/2 - 1: en un arreglo por niveles, las posiciones desde n/2
 * en adelante son HOJAS (no tienen hijos) y ya son mini-heaps de un
 * elemento; el ultimo nodo con al menos un hijo es n/2 - 1. Hundiendo
 * de atras hacia adelante, cuando llegamos a un nodo sus dos subarboles
 * ya son heaps validos.
 *
 * Aunque cada hundir cuesta O(log n), la suma total es O(n): la mayoria
 * de los nodos esta cerca de las hojas y baja poquito.
 */
public class MonticuloConHeapify<T extends Comparable<T>> {

    /** Arreglo que guarda el arbol por niveles. */
    private Object[] datos;

    /** Cantidad de elementos guardados. */
    private int tamanio;

    /** Privado: la unica forma de construirlo es con el metodo de fabrica desde(). */
    private MonticuloConHeapify(Object[] datos, int tamanio) {
        this.datos = datos;
        this.tamanio = tamanio;
    }

    /**
     * Construye un min-heap con TODOS los valores en O(n): copia el arreglo
     * y hunde cada nodo interno desde n/2 - 1 hasta 0.
     */
    public static <T extends Comparable<T>> MonticuloConHeapify<T> desde(T[] valores) {
        if (valores == null) {
            throw new IllegalArgumentException("El arreglo no puede ser null");
        }
        Object[] copia = Arrays.copyOf(valores, valores.length, Object[].class);
        MonticuloConHeapify<T> monticulo = new MonticuloConHeapify<>(copia, valores.length);
        for (int i = monticulo.tamanio / 2 - 1; i >= 0; i--) {
            monticulo.hundir(i);
        }
        return monticulo;
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
     * Copia del arreglo interno (solo las posiciones ocupadas), para poder
     * inspeccionar en los tests como quedo el heap despues del heapify.
     */
    public Object[] aArreglo() {
        return Arrays.copyOf(datos, tamanio);
    }

    /**
     * Devuelve el minimo SIN sacarlo.
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T verMinimo() {
        if (estaVacio()) {
            throw new IllegalStateException("El monticulo esta vacio");
        }
        return elemento(0);
    }

    /** Saca y devuelve el minimo: ultima hoja a la raiz + hundir. O(log n). */
    public T desencolarMinimo() {
        T minimo = verMinimo();
        tamanio--;
        datos[0] = datos[tamanio];
        datos[tamanio] = null;
        if (tamanio > 0) {
            hundir(0);
        }
        return minimo;
    }

    /** Baja el elemento de i comparando con el MENOR de sus dos hijos. */
    private void hundir(int i) {
        while (true) {
            int izquierdo = 2 * i + 1;
            int derecho = 2 * i + 2;
            int menor = i;
            if (izquierdo < tamanio && elemento(izquierdo).compareTo(elemento(menor)) < 0) {
                menor = izquierdo;
            }
            if (derecho < tamanio && elemento(derecho).compareTo(elemento(menor)) < 0) {
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

    /** Demostracion: el ejemplo de la guia, paso a paso. */
    public static void main(String[] args) {
        MonticuloConHeapify<Integer> monticulo =
                MonticuloConHeapify.desde(new Integer[] {12, 5, 9, 3, 20, 7});
        System.out.println("Interno tras heapify = " + Arrays.toString(monticulo.aArreglo()));
        StringBuilder salida = new StringBuilder("Desencolando: ");
        while (!monticulo.estaVacio()) {
            salida.append(monticulo.desencolarMinimo()).append(' ');
        }
        System.out.println(salida); // 3 5 7 9 12 20
    }
}
