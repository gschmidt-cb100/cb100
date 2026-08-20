package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e02;

/**
 * e02: suma de los primeros n naturales (1 + 2 + ... + n) de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: si n &lt;= 0 la suma es 0</li>
 *   <li>Paso recursivo: suma(n) = n + suma(n-1)</li>
 * </ul>
 */
public final class SumaHastaN {

    private SumaHastaN() {
    }

    /**
     * Devuelve 1 + 2 + ... + n recursivamente.
     *
     * @param n limite superior de la suma
     * @return la suma acumulada, o 0 si {@code n <= 0}
     */
    public static long suma(int n) {
        if (n <= 0) {
            return 0L; // caso base
        }
        return n + suma(n - 1); // paso recursivo
    }

    public static void main(String[] args) {
        System.out.println("suma(0) = " + suma(0));
        System.out.println("suma(5) = " + suma(5));
        System.out.println("suma(100) = " + suma(100));
    }
}
