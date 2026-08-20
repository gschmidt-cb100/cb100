package ar.uba.fi.cb100.guia.i10_tecnicas.i02_facil.e02;

/**
 * e02: variante de la escalera donde los pasos pueden ser de 1, 2 o 3
 * escalones.
 *
 * <p><b>Técnica: programación dinámica por tabulación.</b> La recurrencia
 * ahora suma tres términos: formas3(n) = formas3(n-1) + formas3(n-2)
 * + formas3(n-3), según cuál haya sido el último paso. Igual que en e01,
 * los subproblemas se superponen, así que en vez de recursar llenamos una
 * tabla de abajo hacia arriba en O(n). Este ejercicio muestra que la DP se
 * generaliza sola: agregar un paso posible es agregar un término a la suma.</p>
 */
public final class EscalerasTriples {

    private EscalerasTriples() {
    }

    /**
     * Cantidad de formas de subir {@code n} escalones con pasos de 1, 2 o 3,
     * calculada por tabulación.
     *
     * @param n cantidad de escalones (n &gt;= 0)
     * @return cantidad de formas distintas de subir
     */
    public static long formas3(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0, vino " + n);
        }
        if (n == 0) {
            return 1;
        }
        long[] tabla = new long[n + 1];
        tabla[0] = 1; // quedarse quieto
        for (int i = 1; i <= n; i++) {
            tabla[i] = tabla[i - 1];
            if (i >= 2) {
                tabla[i] += tabla[i - 2];
            }
            if (i >= 3) {
                tabla[i] += tabla[i - 3];
            }
        }
        return tabla[n];
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 6; n++) {
            System.out.println("formas3(" + n + ") = " + formas3(n));
        }
    }
}
