package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e08;

import java.util.Arrays;

/**
 * e08 - Copia defensiva de un arreglo.
 *
 * Arrays.copyOf crea un arreglo NUEVO con los mismos valores.
 * Modificar la copia no afecta al original, porque son objetos distintos.
 */
public class CopiarArreglo {

    /**
     * Copia el arreglo recibido, modifica la copia y la devuelve.
     * El arreglo original queda intacto.
     *
     * @param v arreglo original
     * @return copia modificada (posicion 0 puesta en 99)
     */
    public static int[] copiarYmodificar(int[] v) {
        int[] copia = Arrays.copyOf(v, v.length);   // arreglo nuevo e independiente
        copia[0] = 99;                              // solo cambia la copia
        return copia;
    }

    public static void main(String[] args) {
        int[] original = {1, 2, 3};
        int[] copia = copiarYmodificar(original);
        System.out.println("original = " + Arrays.toString(original));
        System.out.println("copia    = " + Arrays.toString(copia));
    }
}
