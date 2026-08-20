package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e01;

/**
 * e01: calculo del factorial de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: 0! = 1</li>
 *   <li>Paso recursivo: n! = n * (n-1)!</li>
 * </ul>
 */
public final class Factorial {

    private Factorial() {
    }

    /**
     * Calcula {@code n!} recursivamente.
     *
     * @param n numero no negativo
     * @return el factorial de {@code n}
     * @throws IllegalArgumentException si {@code n} es negativo
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        if (n == 0) {
            return 1L; // caso base
        }
        return n * factorial(n - 1); // paso recursivo
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 5; i++) {
            System.out.println(i + "! = " + factorial(i));
        }
        System.out.println("10! = " + factorial(10));
    }
}
