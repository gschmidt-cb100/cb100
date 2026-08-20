package ar.uba.fi.cb100.material.i04_complejidad;

/**
 * Dos formas de calcular Fibonacci con complejidades MUY distintas.
 * El recursivo ingenuo recalcula lo mismo una y otra vez:
 * T(n) = T(n−1) + T(n−2) + O(1) → <b>O(2ⁿ)</b> (exponencial).
 * El iterativo lo hace en una pasada: <b>O(n)</b>.
 */
public class FibonacciComparacion {

    /** Exponencial: impráctico para n grande. */
    static long fibNaive(int n) {
        return n < 2 ? n : fibNaive(n - 1) + fibNaive(n - 2);
    }

    /** Lineal: rapidísimo. */
    static long fibIterativo(int n) {
        if (n < 2) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println("fib(30) naive = " + fibNaive(30));
        System.out.println("fib(30) iter  = " + fibIterativo(30));
    }
}
