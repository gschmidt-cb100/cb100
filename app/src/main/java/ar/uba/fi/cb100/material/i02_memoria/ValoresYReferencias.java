package ar.uba.fi.cb100.material.i02_memoria;

import java.util.Arrays;

/**
 * La diferencia central de esta unidad: los primitivos guardan el <b>valor</b>,
 * las variables de objeto guardan una <b>referencia</b> al objeto.
 */
public class ValoresYReferencias {

    public static void main(String[] args) {
        // --- Primitivos: al asignar se copia el VALOR ---
        int a = 5;
        int b = a;        // b es una copia independiente
        b = 99;
        System.out.println("a=" + a + " b=" + b);      // a=5 b=99 (a no cambió)

        // --- Objetos: al asignar se copia la REFERENCIA (aliasing) ---
        int[] x = {1, 2, 3};
        int[] y = x;      // y apunta al MISMO arreglo que x
        y[0] = 99;
        System.out.println("x=" + Arrays.toString(x)); // [99, 2, 3] (x también cambió)
        System.out.println("¿mismo objeto? " + (x == y));  // true
    }
}
