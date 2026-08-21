package ar.uba.fi.cb100.material.i06_estrategias;

import java.util.Arrays;

/**
 * <b>Radix sort</b> (LSD): ordena enteros no negativos procesando sus dígitos, del
 * menos significativo al más significativo, usando un counting sort <b>estable</b>
 * en cada pasada. Complejidad $O(d \cdot (n + b))$ con $d$ dígitos y base $b$;
 * para números de tamaño acotado es prácticamente $O(n)$.
 */
public class RadixSort {

    public static int[] ordenar(int[] entrada) {
        int[] a = Arrays.copyOf(entrada, entrada.length);
        int max = 0;
        for (int x : a) {
            max = Math.max(max, x);
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {   // una pasada por dígito
            a = ordenarPorDigito(a, exp);
        }
        return a;
    }

    /** Counting sort estable según el dígito en la posición {@code exp}. */
    private static int[] ordenarPorDigito(int[] a, int exp) {
        int[] salida = new int[a.length];
        int[] conteo = new int[10];

        for (int x : a) {
            conteo[(x / exp) % 10]++;
        }
        for (int d = 1; d < 10; d++) {
            conteo[d] += conteo[d - 1];   // posiciones acumuladas
        }
        for (int i = a.length - 1; i >= 0; i--) {                  // de atrás → estable
            int d = (a[i] / exp) % 10;
            salida[--conteo[d]] = a[i];
        }
        return salida;
    }

    public static void main(String[] args) {
        int[] a = {170, 45, 75, 90, 2, 802, 24, 66};
        System.out.println(Arrays.toString(ordenar(a)));   // [2, 24, 45, 66, 75, 90, 170, 802]
    }
}
