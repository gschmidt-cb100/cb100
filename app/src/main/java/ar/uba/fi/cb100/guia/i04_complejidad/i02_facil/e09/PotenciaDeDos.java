package ar.uba.fi.cb100.guia.i04_complejidad.i02_facil.e09;

/**
 * e09 - Determina si un entero es una potencia de 2.
 *
 * Complejidad: O(log n).
 * Justificacion: mientras el numero sea par se lo divide por 2. La cantidad
 * de divisiones es log_2(n), por lo que el costo es O(log n).
 */
public final class PotenciaDeDos {

    private PotenciaDeDos() {
    }

    /**
     * Indica si n es una potencia de 2. El 1 es potencia (2^0); el 0 y los
     * numeros negativos no lo son.
     *
     * @param n numero entero
     * @return true si n es una potencia de 2
     */
    public static boolean esPotenciaDe2(int n) {
        if (n <= 0) {
            return false; // 0 y negativos no son potencias de 2
        }
        while (n % 2 == 0) { // ~log_2(n) iteraciones -> O(log n)
            n /= 2;
        }
        // Es potencia de 2 si, tras quitar todos los factores 2, queda 1.
        return n == 1;
    }

    public static void main(String[] args) {
        System.out.println("esPotenciaDe2(1) = " + esPotenciaDe2(1));   // true
        System.out.println("esPotenciaDe2(16) = " + esPotenciaDe2(16)); // true
        System.out.println("esPotenciaDe2(12) = " + esPotenciaDe2(12)); // false
        System.out.println("esPotenciaDe2(0) = " + esPotenciaDe2(0));   // false
    }
}
