package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e04;

/**
 * Ejercicio 4 (facil): Mayor de dos enteros.
 */
public class MayorDeDos {

    private MayorDeDos() {
    }

    /**
     * Devuelve el mayor de dos enteros. Si son iguales devuelve ese valor.
     *
     * @param a primer numero
     * @param b segundo numero
     * @return el mayor de los dos
     */
    public static int mayor(int a, int b) {
        return a >= b ? a : b;
    }

    public static void main(String[] args) {
        System.out.println("mayor(3, 8) = " + mayor(3, 8));
        System.out.println("mayor(10, 10) = " + mayor(10, 10));
    }
}
