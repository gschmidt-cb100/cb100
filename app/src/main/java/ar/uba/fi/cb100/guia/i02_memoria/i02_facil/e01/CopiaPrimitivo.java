package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e01;

import java.util.Arrays;

/**
 * e01 - Copia de valor entre tipos primitivos.
 *
 * Cuando se copia un primitivo (int a = 5; int b = a;) se copia el VALOR.
 * Modificar b luego no afecta a a: son dos casillas de memoria independientes.
 */
public class CopiaPrimitivo {

    /**
     * Reproduce la secuencia:
     *   int a = 5;
     *   int b = a;   // copia del valor
     *   b = 99;      // solo cambia b
     * y devuelve {a, b}.
     *
     * @return arreglo {a, b} = {5, 99}
     */
    public static int[] valores() {
        int a = 5;
        int b = a;   // b recibe una COPIA del valor de a
        b = 99;      // reasignar b no toca a a
        return new int[]{a, b};
    }

    public static void main(String[] args) {
        System.out.println("valores() = " + Arrays.toString(valores()));
    }
}
