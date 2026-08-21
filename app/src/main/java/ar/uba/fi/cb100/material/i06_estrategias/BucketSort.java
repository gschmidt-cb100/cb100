package ar.uba.fi.cb100.material.i06_estrategias;

import java.util.Arrays;

/**
 * <b>Bucket sort</b>: reparte los valores en "baldes" (buckets) según su rango,
 * ordena cada balde por separado y los concatena. Funciona muy bien cuando los
 * datos están <b>uniformemente distribuidos</b> (acá, reales en $[0, 1)$), con
 * costo promedio $O(n)$.
 */
public class BucketSort {

    public static double[] ordenar(double[] a) {
        int n = a.length;
        if (n == 0) {
            return a.clone();
        }

        // n baldes; cada uno junta los valores de un subrango de [0,1).
        double[][] baldes = new double[n][0];
        for (double x : a) {
            int i = (int) (n * x);               // balde según el valor
            baldes[i] = agregar(baldes[i], x);
        }

        double[] resultado = new double[n];
        int k = 0;
        for (double[] balde : baldes) {
            Arrays.sort(balde);                  // ordenar cada balde
            for (double x : balde) {
                resultado[k++] = x;   // concatenar
            }
        }
        return resultado;
    }

    private static double[] agregar(double[] arr, double x) {
        double[] nuevo = Arrays.copyOf(arr, arr.length + 1);
        nuevo[arr.length] = x;
        return nuevo;
    }

    public static void main(String[] args) {
        double[] a = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        System.out.println(Arrays.toString(ordenar(a)));
    }
}
