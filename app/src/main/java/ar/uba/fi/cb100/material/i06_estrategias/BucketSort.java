package ar.uba.fi.cb100.material.i06_estrategias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <b>Bucket sort</b> (ordenamiento por baldes), en sus tres pasos:
 * <ol>
 *   <li><b>Repartir</b>: cada valor x de [0,1) va al balde ⌊n·x⌋.</li>
 *   <li><b>Ordenar</b> cada balde por dentro (tiene pocos elementos).</li>
 *   <li><b>Concatenar</b> los baldes en orden (0, 1, ..., n-1).</li>
 * </ol>
 * Funciona muy bien cuando los datos están <b>uniformemente distribuidos</b>
 * (acá, reales en [0, 1)): costo promedio O(n). Peor caso (todos los valores
 * caen en el mismo balde): O(n^2).
 */
public class BucketSort {

    public static double[] ordenar(double[] a) {
        int n = a.length;
        if (n == 0) {
            return a.clone();
        }

        // Paso 1 — repartir: n baldes vacíos, y cada valor a su balde.
        List<List<Double>> baldes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            baldes.add(new ArrayList<>());
        }
        for (double x : a) {
            int indice = (int) (n * x);     // el balde de x es ⌊n·x⌋
            baldes.get(indice).add(x);
        }

        // Paso 2 — ordenar cada balde por dentro.
        for (List<Double> balde : baldes) {
            Collections.sort(balde);
        }

        // Paso 3 — concatenar los baldes en orden.
        double[] resultado = new double[n];
        int k = 0;
        for (List<Double> balde : baldes) {
            for (double x : balde) {
                resultado[k++] = x;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        double[] a = {0.42, 0.26, 0.72, 0.17, 0.68, 0.09};
        System.out.println("entrada:  " + Arrays.toString(a));
        System.out.println("ordenado: " + Arrays.toString(ordenar(a)));
    }
}
