package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e02;

import java.util.Arrays;

/**
 * e02 - Aliasing de arreglos.
 *
 * Un arreglo es un objeto: la variable guarda una REFERENCIA.
 * Al hacer int[] a = v; ambas variables apuntan al MISMO arreglo (alias).
 * Modificar a través del alias modifica el arreglo original.
 */
public class AliasingArreglo {

    /**
     * Crea un alias del arreglo recibido y lo modifica a través de él.
     *
     * @param v arreglo original
     * @return el mismo arreglo v, ya modificado en la posicion 0
     */
    public static int[] conAlias(int[] v) {
        int[] a = v;   // a y v son alias: apuntan al mismo objeto
        a[0] = 99;     // modifica el arreglo compartido
        return v;
    }

    public static void main(String[] args) {
        int[] original = {1, 2, 3};
        System.out.println("antes  = " + Arrays.toString(original));
        conAlias(original);
        System.out.println("despues = " + Arrays.toString(original));
    }
}
