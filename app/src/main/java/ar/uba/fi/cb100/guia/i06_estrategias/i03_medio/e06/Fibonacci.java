package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e06;

/**
 * e06: numeros de Fibonacci de forma eficiente en O(n).
 *
 * <p>La definicion natural {@code fib(n) = fib(n-1) + fib(n-2)} calculada de
 * forma recursiva pura es exponencial (O(2^n)) porque recalcula los mismos
 * subproblemas una y otra vez. Aca usamos un enfoque iterativo bottom-up que
 * guarda solo los dos ultimos valores, logrando O(n) tiempo y O(1) memoria.</p>
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>fib(0) = 0</li>
 *   <li>fib(1) = 1</li>
 *   <li>fib(n) = fib(n-1) + fib(n-2) para n &gt;= 2</li>
 * </ul>
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    /**
     * Calcula el n-esimo numero de Fibonacci en O(n).
     *
     * @param n indice no negativo
     * @return el valor de {@code fib(n)}
     * @throws IllegalArgumentException si {@code n} es negativo
     */
    public static long fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        if (n < 2) {
            return n; // casos base: fib(0)=0, fib(1)=1
        }
        long anterior = 0L; // fib(0)
        long actual = 1L;   // fib(1)
        for (int i = 2; i <= n; i++) {
            long siguiente = anterior + actual;
            anterior = actual;
            actual = siguiente;
        }
        return actual;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            System.out.print(fib(i) + " ");
        }
        System.out.println();
        System.out.println("fib(50) = " + fib(50));
    }
}
