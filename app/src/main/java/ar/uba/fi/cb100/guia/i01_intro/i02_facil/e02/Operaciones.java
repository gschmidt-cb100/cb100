package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e02;

/**
 * Ejercicio 2 (facil): Operaciones aritmeticas basicas.
 * Suma, resta y producto con enteros; division con resultado real.
 */
public class Operaciones {

    private Operaciones() {
    }

    /** Devuelve la suma de dos enteros. */
    public static int suma(int a, int b) {
        return a + b;
    }

    /** Devuelve la resta de dos enteros. */
    public static int resta(int a, int b) {
        return a - b;
    }

    /** Devuelve el producto de dos enteros. */
    public static int producto(int a, int b) {
        return a * b;
    }

    /**
     * Devuelve la division real de dos enteros.
     *
     * @throws ArithmeticException si el divisor es cero
     */
    public static double division(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        // Se castea a double para evitar la division entera.
        return (double) a / b;
    }

    public static void main(String[] args) {
        System.out.println("suma(2, 3) = " + suma(2, 3));
        System.out.println("resta(5, 2) = " + resta(5, 2));
        System.out.println("producto(4, 3) = " + producto(4, 3));
        System.out.println("division(7, 2) = " + division(7, 2));
    }
}
