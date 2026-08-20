package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e06;

import java.util.Arrays;

/**
 * e06 - Reasignar el parametro NO afecta a la variable de afuera.
 *
 * Java pasa los argumentos por VALOR (tambien las referencias).
 * Reasignar v dentro del metodo solo cambia la copia local de la referencia;
 * la variable del llamador sigue apuntando al arreglo original.
 * (Distinto de mutar v[i], que si afectaria al objeto compartido.)
 */
public class ReasignarNoAfecta {

    /**
     * Reasigna el parametro a un arreglo nuevo. No tiene efecto afuera.
     *
     * @param v arreglo recibido (su referencia es una copia local)
     */
    public static void reasignar(int[] v) {
        v = new int[]{9, 9, 9};   // solo cambia la copia local de la referencia
    }

    public static void main(String[] args) {
        int[] original = {1, 2, 3};
        System.out.println("antes   = " + Arrays.toString(original));
        reasignar(original);
        System.out.println("despues = " + Arrays.toString(original));
    }
}
