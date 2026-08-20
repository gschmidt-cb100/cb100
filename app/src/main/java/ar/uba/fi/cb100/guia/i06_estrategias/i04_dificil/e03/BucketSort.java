package ar.uba.fi.cb100.guia.i06_estrategias.i04_dificil.e03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * e03: ordenamiento por cubetas (bucket sort) para numeros reales uniformemente
 * distribuidos en el intervalo {@code [0, 1)}.
 *
 * <p>Idea: se reparten los {@code n} valores en {@code n} cubetas segun su
 * magnitud (la cubeta de {@code x} es {@code floor(n * x)}). Cada cubeta se
 * ordena por separado y luego se concatenan en orden. Si los datos estan
 * bien distribuidos, el costo promedio es {@code O(n)}.</p>
 */
public final class BucketSort {

    private BucketSort() {
    }

    /**
     * Ordena un arreglo de reales en {@code [0, 1)}.
     *
     * @param a arreglo de entrada (no se modifica)
     * @return un arreglo nuevo ordenado de menor a mayor
     * @throws IllegalArgumentException si algun valor no esta en {@code [0, 1)}
     */
    public static double[] ordenar(double[] a) {
        int n = a.length;
        if (n == 0) {
            return new double[0];
        }
        // Una cubeta (lista) por cada franja del intervalo.
        List<List<Double>> cubetas = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            cubetas.add(new ArrayList<>());
        }
        for (double valor : a) {
            if (valor < 0.0 || valor >= 1.0) {
                throw new IllegalArgumentException("valor fuera de [0, 1): " + valor);
            }
            int indice = (int) (n * valor);
            cubetas.get(indice).add(valor);
        }
        // Ordenamos cada cubeta y concatenamos en orden.
        double[] resultado = new double[n];
        int pos = 0;
        for (List<Double> cubeta : cubetas) {
            Collections.sort(cubeta);
            for (double valor : cubeta) {
                resultado[pos++] = valor;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        double[] datos = {0.42, 0.11, 0.99, 0.01, 0.5, 0.5, 0.73};
        System.out.println("original:  " + Arrays.toString(datos));
        System.out.println("ordenado:  " + Arrays.toString(ordenar(datos)));
    }
}
