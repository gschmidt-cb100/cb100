package ar.uba.fi.cb100.guia.i04_complejidad.i03_medio.e07;

/**
 * Factorial de un entero no negativo, calculado en forma recursiva.
 *
 * Recurrencia: T(n) = T(n-1) + O(1)
 *   - T(n-1): el factorial de n-1.
 *   - O(1): la multiplicacion por n.
 * Desarrollando: T(n) = O(n).
 * Complejidad espacial: O(n) por la pila de llamadas recursivas.
 */
public final class Factorial {

    private Factorial() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Calcula n! = 1 * 2 * ... * n.
     *
     * @param n entero no negativo.
     * @return el factorial de {@code n} (0! = 1).
     * @throws IllegalArgumentException si {@code n} es negativo.
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no esta definido para negativos: " + n);
        }
        if (n <= 1) {
            return 1; // Caso base: 0! = 1! = 1.
        }
        return (long) n * factorial(n - 1);
    }

    public static void main(String[] args) {
        System.out.println("0! = " + factorial(0));   // 1
        System.out.println("5! = " + factorial(5));   // 120
        System.out.println("10! = " + factorial(10)); // 3628800
    }
}
