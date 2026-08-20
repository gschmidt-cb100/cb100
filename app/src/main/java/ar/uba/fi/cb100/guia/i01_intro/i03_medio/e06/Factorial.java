package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e06;

/**
 * Ejercicio 06: Factorial iterativo.
 * Calcula n! usando un bucle (sin recursión).
 */
public class Factorial {

    /**
     * Factorial iterativo de n.
     *
     * @param n entero no negativo
     * @return n!
     * @throws IllegalArgumentException si n < 0
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no está definido para n < 0: " + n);
        }
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("0! = " + factorial(0));
        System.out.println("1! = " + factorial(1));
        System.out.println("5! = " + factorial(5));
    }
}
