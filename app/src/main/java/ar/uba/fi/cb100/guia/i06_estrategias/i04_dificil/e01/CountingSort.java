package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e01;

import java.util.Arrays;

/**
 * e01: ordenamiento por conteo (counting sort).
 *
 * <p>Es un ordenamiento <b>no comparativo</b>: no compara elementos entre si,
 * sino que cuenta cuantas veces aparece cada valor y luego reconstruye el
 * arreglo en orden. Funciona para enteros no negativos en el rango
 * {@code [0, maximo]}.</p>
 *
 * <p>Complejidad temporal: {@code O(n + k)} donde {@code k = maximo}.
 * Complejidad espacial: {@code O(k)}.</p>
 */
public final class CountingSort {

    private CountingSort() {
    }

    /**
     * Ordena un arreglo de enteros no negativos usando conteo.
     *
     * @param a       arreglo de entrada (no se modifica)
     * @param maximo  valor maximo posible presente en {@code a} (>= 0)
     * @return un arreglo nuevo con los mismos elementos ordenados de menor a mayor
     * @throws IllegalArgumentException si {@code maximo} es negativo o si hay
     *                                  algun valor fuera del rango {@code [0, maximo]}
     */
    public static int[] ordenar(int[] a, int maximo) {
        if (maximo < 0) {
            throw new IllegalArgumentException("maximo no puede ser negativo: " + maximo);
        }
        // conteos[v] = cantidad de veces que aparece el valor v
        int[] conteos = new int[maximo + 1];
        for (int valor : a) {
            if (valor < 0 || valor > maximo) {
                throw new IllegalArgumentException(
                        "valor fuera del rango [0, " + maximo + "]: " + valor);
            }
            conteos[valor]++;
        }
        // Reconstruimos el arreglo ordenado recorriendo los valores en orden.
        int[] resultado = new int[a.length];
        int indice = 0;
        for (int valor = 0; valor <= maximo; valor++) {
            for (int i = 0; i < conteos[valor]; i++) {
                resultado[indice++] = valor;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] datos = {4, 2, 2, 8, 3, 3, 1, 0, 7};
        System.out.println("original:  " + Arrays.toString(datos));
        System.out.println("ordenado:  " + Arrays.toString(ordenar(datos, 8)));
    }
}
