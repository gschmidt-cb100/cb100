package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e08;

import java.util.Arrays;

/**
 * e08: seleccion del k-esimo menor elemento con el algoritmo Quickselect.
 *
 * <p>Quickselect reutiliza la particion de Quicksort, pero en lugar de ordenar
 * ambas mitades solo recurre sobre la que contiene la posicion buscada. Esto da
 * un costo promedio {@code O(n)} (peor caso {@code O(n^2)}), mejor que ordenar
 * todo el arreglo en {@code O(n log n)}.</p>
 */
public final class QuickSelect {

    private QuickSelect() {
    }

    /**
     * Devuelve el {@code k}-esimo menor elemento (k = 1 es el minimo).
     *
     * @param a arreglo de entrada (no se modifica: se trabaja sobre una copia)
     * @param k posicion buscada en orden creciente, entre {@code 1} y {@code n}
     * @return el valor que quedaria en la posicion {@code k-1} si se ordenara
     * @throws IllegalArgumentException si {@code k} esta fuera de {@code [1, n]}
     */
    public static int kEsimoMenor(int[] a, int k) {
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException("k fuera de rango [1, " + a.length + "]: " + k);
        }
        int[] copia = a.clone();
        return seleccionar(copia, 0, copia.length - 1, k - 1); // pasamos a indice base 0
    }

    /**
     * Busca el elemento cuyo indice ordenado es {@code objetivo} dentro de
     * {@code [izq, der]}.
     */
    private static int seleccionar(int[] a, int izq, int der, int objetivo) {
        if (izq == der) {
            return a[izq]; // caso base: un solo elemento
        }
        int posPivote = particionar(a, izq, der);
        if (objetivo == posPivote) {
            return a[posPivote];
        } else if (objetivo < posPivote) {
            return seleccionar(a, izq, posPivote - 1, objetivo);
        } else {
            return seleccionar(a, posPivote + 1, der, objetivo);
        }
    }

    /**
     * Particion estilo Lomuto: usa {@code a[der]} como pivote y devuelve su
     * posicion final. A la izquierda quedan los menores o iguales; a la derecha
     * los mayores.
     */
    private static int particionar(int[] a, int izq, int der) {
        int pivote = a[der];
        int i = izq;
        for (int j = izq; j < der; j++) {
            if (a[j] <= pivote) {
                intercambiar(a, i, j);
                i++;
            }
        }
        intercambiar(a, i, der);
        return i;
    }

    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int[] datos = {7, 2, 9, 4, 1, 8, 3};
        for (int k = 1; k <= datos.length; k++) {
            System.out.println(k + "-esimo menor = " + kEsimoMenor(datos, k));
        }
        System.out.println("orden real: " + Arrays.toString(datos.clone()));
    }
}
