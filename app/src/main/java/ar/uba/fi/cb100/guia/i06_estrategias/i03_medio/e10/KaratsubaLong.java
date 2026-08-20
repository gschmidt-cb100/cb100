package ar.uba.fi.cb100.guia.i06_estrategias.i03_medio.e10;

/**
 * e10: multiplicacion de enteros con la idea de Karatsuba.
 *
 * <p>La multiplicacion escolar de dos numeros de {@code n} digitos hace
 * O(n^2) productos de un digito. Karatsuba parte cada numero en dos mitades y,
 * mediante un truco algebraico, resuelve el problema con <b>tres</b>
 * multiplicaciones de la mitad del tamano en vez de cuatro, bajando la
 * complejidad a O(n^1.585).</p>
 *
 * <p>Si {@code x = x1 * 10^m + x0} e {@code y = y1 * 10^m + y0}, entonces:</p>
 * <pre>
 *   z2 = x1 * y1
 *   z0 = x0 * y0
 *   z1 = (x1 + x0) * (y1 + y0) - z2 - z0
 *   x * y = z2 * 10^(2m) + z1 * 10^m + z0
 * </pre>
 *
 * <p>Esta implementacion trabaja con {@code long}. El signo se maneja aparte y
 * la recursion opera sobre los valores absolutos. Los resultados deben caber en
 * un {@code long} (no hay aritmetica de precision arbitraria).</p>
 */
public final class KaratsubaLong {

    private KaratsubaLong() {
    }

    /**
     * Multiplica {@code x} por {@code y} con el metodo de Karatsuba.
     *
     * @param x primer factor (puede ser negativo)
     * @param y segundo factor (puede ser negativo)
     * @return el producto {@code x * y}
     */
    public static long multiplicar(long x, long y) {
        // El signo se resuelve aparte; recurrimos sobre los valores absolutos.
        boolean negativo = (x < 0) ^ (y < 0);
        long producto = karatsuba(Math.abs(x), Math.abs(y));
        return negativo ? -producto : producto;
    }

    /** Karatsuba sobre valores no negativos. */
    private static long karatsuba(long x, long y) {
        // Caso base: alguno cabe en un digito decimal => producto directo.
        if (x < 10 || y < 10) {
            return x * y;
        }
        // m = mitad de la cantidad de digitos del mayor de los dos.
        int digitos = Math.max(cantidadDigitos(x), cantidadDigitos(y));
        int m = digitos / 2;
        long potencia = potenciaDe10(m); // 10^m

        long x1 = x / potencia;
        long x0 = x % potencia;
        long y1 = y / potencia;
        long y0 = y % potencia;

        long z2 = karatsuba(x1, y1);
        long z0 = karatsuba(x0, y0);
        long z1 = karatsuba(x1 + x0, y1 + y0) - z2 - z0;

        return z2 * potenciaDe10(2 * m) + z1 * potencia + z0;
    }

    private static int cantidadDigitos(long n) {
        if (n == 0) {
            return 1;
        }
        int d = 0;
        while (n > 0) {
            d++;
            n /= 10;
        }
        return d;
    }

    private static long potenciaDe10(int exp) {
        long r = 1L;
        for (int i = 0; i < exp; i++) {
            r *= 10L;
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println("1234 * 5678 = " + multiplicar(1234, 5678));
        System.out.println("-12 * 34    = " + multiplicar(-12, 34));
        System.out.println("99999 * 99999 = " + multiplicar(99999, 99999));
    }
}
