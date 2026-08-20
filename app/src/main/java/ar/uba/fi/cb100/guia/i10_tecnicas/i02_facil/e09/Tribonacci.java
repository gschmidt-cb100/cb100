package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e09;

/**
 * e09: la sucesión de Tribonacci: t(0)=0, t(1)=1, t(2)=1 y de ahí en
 * adelante cada término es la suma de los TRES anteriores.
 *
 * <p><b>Técnica: programación dinámica por tabulación.</b> La recursión
 * directa cuesta O(3^n) porque recalcula los mismos términos una y otra
 * vez (el mismo problema que Fibonacci, agravado). Tabulando de abajo
 * hacia arriba cada término se calcula una sola vez, en O(n) tiempo.
 * Como sólo miramos los tres últimos valores, alcanza con tres variables
 * rodantes: O(1) de espacio, sin arreglo auxiliar.</p>
 */
public final class Tribonacci {

    private Tribonacci() {
    }

    /**
     * Devuelve el término {@code n} de la sucesión de Tribonacci
     * (t0=0, t1=1, t2=1), calculado por tabulación.
     *
     * @param n índice del término pedido (n &gt;= 0)
     * @return el valor de t(n)
     */
    public static long tribonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0, vino " + n);
        }
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }
        // Tres variables rodantes en lugar de la tabla completa.
        long a = 0; // t(i-3)
        long b = 1; // t(i-2)
        long c = 1; // t(i-1)
        for (int i = 3; i <= n; i++) {
            long siguiente = a + b + c;
            a = b;
            b = c;
            c = siguiente;
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.print("Primeros terminos:");
        for (int n = 0; n <= 10; n++) {
            System.out.print(" " + tribonacci(n));
        }
        System.out.println();
        System.out.println("tribonacci(25) = " + tribonacci(25));
    }
}
