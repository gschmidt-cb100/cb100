package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e02;

import java.util.Arrays;

/**
 * e02: ordenamiento por radix (LSD, del digito menos significativo al mas
 * significativo) para enteros no negativos en base 10.
 *
 * <p>Es un ordenamiento <b>no comparativo</b>: ordena repetidamente por cada
 * digito, de derecha a izquierda, usando un conteo <b>estable</b> en cada paso.
 * La estabilidad es la clave: preserva el orden relativo logrado por los
 * digitos anteriores.</p>
 *
 * <p>Complejidad temporal: {@code O(d * (n + 10))} donde {@code d} es la
 * cantidad de digitos del maximo.</p>
 */
public final class RadixSort {

    private RadixSort() {
    }

    /**
     * Ordena un arreglo de enteros no negativos.
     *
     * @param a arreglo de entrada (no se modifica)
     * @return un arreglo nuevo ordenado de menor a mayor
     * @throws IllegalArgumentException si algun valor es negativo
     */
    public static int[] ordenar(int[] a) {
        for (int valor : a) {
            if (valor < 0) {
                throw new IllegalArgumentException("radix LSD solo admite no negativos: " + valor);
            }
        }
        if (a.length == 0) {
            return new int[0];
        }
        int maximo = Arrays.stream(a).max().getAsInt();
        int[] actual = a.clone();
        // Recorremos cada posicion decimal mientras exista un digito significativo.
        for (int exp = 1; maximo / exp > 0; exp *= 10) {
            actual = ordenarPorDigito(actual, exp);
        }
        return actual;
    }

    /**
     * Counting sort estable usando el digito de {@code a} en la posicion
     * indicada por {@code exp} (1 = unidades, 10 = decenas, ...).
     */
    private static int[] ordenarPorDigito(int[] a, int exp) {
        int[] conteos = new int[10];
        for (int valor : a) {
            int digito = (valor / exp) % 10;
            conteos[digito]++;
        }
        // Suma acumulada: conteos[d] pasa a ser la posicion final (exclusiva)
        // del ultimo elemento con digito d.
        for (int d = 1; d < 10; d++) {
            conteos[d] += conteos[d - 1];
        }
        int[] resultado = new int[a.length];
        // Recorremos de derecha a izquierda para mantener la estabilidad.
        for (int i = a.length - 1; i >= 0; i--) {
            int digito = (a[i] / exp) % 10;
            resultado[--conteos[digito]] = a[i];
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.println("original:  " + Arrays.toString(datos));
        System.out.println("ordenado:  " + Arrays.toString(ordenar(datos)));
    }
}
