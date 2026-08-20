package ar.uba.fi.cb100.material.i04_complejidad;

/**
 * Complejidad <b>O(n)</b> (lineal): un solo bucle que recorre los n elementos.
 */
public class SumaArreglo {

    public static long sumar(int[] a) {
        long suma = 0;
        for (int x : a) {        // n iteraciones, cada una O(1)  ->  O(n)
            suma += x;
        }
        return suma;
    }

    public static void main(String[] args) {
        System.out.println(sumar(new int[]{1, 2, 3, 4, 5}));   // 15
    }
}
