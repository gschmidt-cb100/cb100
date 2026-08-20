package ar.uba.fi.cb100.guia.i04_complejidad.i04_dificil.e09;

/**
 * Fibonacci en O(log n) mediante "fast doubling".
 *
 * Identidades de duplicación:
 *   F(2k)   = F(k) * (2*F(k+1) - F(k))
 *   F(2k+1) = F(k)^2 + F(k+1)^2
 *
 * Recorriendo los bits de n de más significativo a menos significativo, se
 * duplica el índice en cada paso y, si el bit es 1, se avanza una posición.
 *
 * Complejidad temporal: O(log n), un paso por bit de n.
 * Complejidad espacial: O(1) (versión iterativa sobre los bits).
 *
 * F(0)=0, F(1)=1. Con long, F(92) es el mayor que no desborda.
 */
public final class FibonacciRapido {

    private FibonacciRapido() {
    }

    /** Devuelve el n-ésimo número de Fibonacci. */
    public static long fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo");
        }
        if (n > 92) {
            throw new IllegalArgumentException("n > 92 desborda long: " + n);
        }
        long a = 0; // F(k)
        long b = 1; // F(k+1)
        // Recorre los bits de n desde el más significativo.
        for (int bit = Integer.highestOneBit(n == 0 ? 1 : n); bit != 0; bit >>= 1) {
            // Duplicación: a = F(2k), c = F(2k+1)
            long dosBmenosA = 2 * b - a;
            long f2k = a * dosBmenosA;      // F(2k)
            long f2k1 = a * a + b * b;      // F(2k+1)
            a = f2k;
            b = f2k1;
            if ((n & bit) != 0) {
                // Bit en 1: avanzamos una posición (k -> 2k+1).
                long siguiente = a + b;
                a = b;
                b = siguiente;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 10; n++) {
            System.out.print(fib(n) + " ");
        }
        System.out.println();
        System.out.println("fib(50) = " + fib(50)); // 12586269025
    }
}
