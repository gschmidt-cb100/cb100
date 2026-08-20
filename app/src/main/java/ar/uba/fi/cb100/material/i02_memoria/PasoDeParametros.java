package ar.uba.fi.cb100.material.i02_memoria;

import java.util.Arrays;

/**
 * En Java los parámetros se pasan <b>siempre por valor</b>. Para un objeto, lo
 * que se copia es la referencia: reasignarla adentro NO afecta afuera, pero
 * <b>mutar el objeto</b> sí, porque ambas referencias apuntan al mismo objeto.
 */
public class PasoDeParametros {

    static void incrementar(int n) {
        n = n + 1;                    // primitivo: cambia solo la copia local
    }

    static void reasignar(int[] arr) {
        arr = new int[]{9, 9, 9};     // reasigna la copia local de la referencia
    }

    static void mutar(int[] arr) {
        if (arr.length > 0) arr[0] = 99;   // muta el objeto compartido
    }

    public static void main(String[] args) {
        int n = 5;
        incrementar(n);
        System.out.println("n = " + n);            // 5 (sin cambios)

        int[] a = {1, 2, 3};
        reasignar(a);
        System.out.println(Arrays.toString(a));    // [1, 2, 3] (sin cambios)
        mutar(a);
        System.out.println(Arrays.toString(a));    // [99, 2, 3] (el objeto cambió)
    }
}
