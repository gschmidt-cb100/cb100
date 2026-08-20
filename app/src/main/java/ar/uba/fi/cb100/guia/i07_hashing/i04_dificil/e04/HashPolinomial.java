package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e04;

/**
 * Hash polinomial de cadenas con la regla de Horner.
 *
 * La idea: interpretar la cadena como un numero en una base dada, donde cada
 * "digito" es el codigo del caracter:
 *
 *   h(s) = (s[0]*base^(n-1) + s[1]*base^(n-2) + ... + s[n-1]) mod modulo
 *
 * Con Horner no hace falta calcular potencias: se recorre la cadena
 * acumulando h = (h * base + caracter) mod modulo. Tomando modulo en cada
 * paso y acumulando en long, el producto intermedio nunca desborda
 * (modulo y base entran en int, el producto entra en long).
 *
 * Con base 31 y sin modulo (dejando desbordar el int) esta es EXACTAMENTE
 * la formula de String.hashCode() de Java.
 *
 * Complejidad: O(n) con n = longitud de la cadena.
 */
public final class HashPolinomial {

    private HashPolinomial() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * Calcula el hash polinomial de s con la base y el modulo dados.
     * El resultado queda siempre en [0, modulo).
     *
     * @param s      cadena a hashear (no null)
     * @param base   base del polinomio (por ejemplo 31)
     * @param modulo modulo positivo que acota el resultado
     */
    public static int hashPolinomial(String s, int base, int modulo) {
        if (modulo <= 0) {
            throw new IllegalArgumentException("El modulo debe ser positivo: " + modulo);
        }
        long h = 0;
        for (int i = 0; i < s.length(); i++) {
            // Aritmetica en long: h < modulo <= 2^31, base < 2^31,
            // entonces h * base + c < 2^62 + 2^31 y no desborda el long.
            h = (h * base + s.charAt(i)) % modulo;
        }
        return (int) h;
    }

    /** Demostracion: comparacion con el hashCode nativo de Java. */
    public static void main(String[] args) {
        int modulo = 1_000_000_007;
        for (String palabra : new String[] { "ana", "hash", "tabla", "clave" }) {
            int propio = hashPolinomial(palabra, 31, modulo);
            int nativo = Math.floorMod(palabra.hashCode(), modulo);
            System.out.printf("%-6s propio=%d nativo=%d%n", palabra, propio, nativo);
        }
        // Con modulo chico sirve directamente como indice de tabla.
        System.out.println("indice de 'ana' en tabla de 8: "
                + hashPolinomial("ana", 31, 8));
    }
}
