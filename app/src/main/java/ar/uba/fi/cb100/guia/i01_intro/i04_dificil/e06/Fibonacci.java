package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e06;

/**
 * Ejercicio 06 - Fibonacci iterativo.
 * Calcula el n-esimo numero de Fibonacci sin recursion.
 */
public class Fibonacci {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private Fibonacci() {
    }

    /**
     * Devuelve el n-esimo numero de Fibonacci (fib(0)=0, fib(1)=1).
     *
     * @param n indice no negativo
     * @return valor de Fibonacci en la posicion n
     */
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El indice no puede ser negativo: " + n);
        }
        if (n == 0) {
            return 0L;
        }

        long anterior = 0L;
        long actual = 1L;

        // Iteramos acumulando los dos ultimos valores
        for (int i = 2; i <= n; i++) {
            long siguiente = anterior + actual;
            anterior = actual;
            actual = siguiente;
        }
        return actual;
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            System.out.println("fib(" + i + ") = " + fibonacci(i));
        }
    }
}
