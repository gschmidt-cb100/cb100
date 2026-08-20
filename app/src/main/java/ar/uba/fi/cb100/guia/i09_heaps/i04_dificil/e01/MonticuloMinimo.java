package ar.uba.fi.cb100.guia.i09_heaps.i04_dificil.e01;

/**
 * Monticulo de minimo (min-heap) implementado A MANO sobre un arreglo,
 * sin usar PriorityQueue.
 *
 * Invariante de heap: cada nodo es MENOR O IGUAL que sus hijos. Con el
 * arbol guardado por niveles en un arreglo, el padre del indice i esta
 * en (i - 1) / 2 y sus hijos en 2*i + 1 y 2*i + 2. Gracias al invariante,
 * el minimo vive siempre en la raiz (posicion 0).
 *
 * Este ejercicio solo pide insertar y mirar el minimo:
 *  - encolar: O(log n), el elemento nuevo entra como ultima hoja y "flota"
 *    intercambiandose con su padre mientras sea mas chico que el.
 *  - verMinimo / tamanio / estaVacio: O(1).
 *
 * El arreglo interno se redimensiona al doble cuando se llena, asi que
 * encolar es O(log n) amortizado aun contando las copias.
 */
public class MonticuloMinimo<T extends Comparable<T>> {

    private static final int CAPACIDAD_INICIAL = 8;

    /** Arreglo que guarda el arbol por niveles. Usamos Object[] porque Java no permite new T[]. */
    private Object[] datos;

    /** Cantidad de elementos guardados (las posiciones validas son 0..tamanio-1). */
    private int tamanio;

    public MonticuloMinimo() {
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
     * Devuelve el minimo SIN sacarlo: por el invariante, es la raiz.
     *
     * @throws IllegalStateException si el monticulo esta vacio.
     */
    public T verMinimo() {
        if (estaVacio()) {
            throw new IllegalStateException("El monticulo esta vacio");
        }
        return elemento(0);
    }

    /**
     * Agrega un elemento manteniendo el invariante de heap. O(log n).
     * Entra como ultima hoja y flota hasta que su padre sea menor o igual.
     */
    public void encolar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se admiten valores null");
        }
        asegurarCapacidad();
        datos[tamanio] = valor;
        flotar(tamanio);
        tamanio++;
    }

    /** Si el arreglo se lleno, lo copia a uno del DOBLE de capacidad. */
    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamanio);
            datos = nuevo;
        }
    }

    /**
     * Sube el elemento de la posicion i intercambiandolo con su padre
     * (i - 1) / 2 mientras sea estrictamente menor que el.
     */
    private void flotar(int i) {
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (elemento(i).compareTo(elemento(padre)) >= 0) {
                break; // El padre ya es menor o igual: el invariante se cumple.
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    private void intercambiar(int i, int j) {
        Object aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }

    /** Cast centralizado: solo guardamos T, asi que es seguro. */
    @SuppressWarnings("unchecked")
    private T elemento(int i) {
        return (T) datos[i];
    }

    /** Demostracion: insertamos desordenado y el minimo siempre queda en la raiz. */
    public static void main(String[] args) {
        MonticuloMinimo<Integer> monticulo = new MonticuloMinimo<>();
        for (int valor : new int[] {42, 17, 99, 3, 25}) {
            monticulo.encolar(valor);
            System.out.println("Encole " + valor + " -> minimo actual = " + monticulo.verMinimo());
        }
        System.out.println("tamanio = " + monticulo.tamanio());
    }
}
