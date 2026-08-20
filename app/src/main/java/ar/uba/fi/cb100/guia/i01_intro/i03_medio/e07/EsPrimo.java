package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e07;

/**
 * Ejercicio 07: Es primo.
 * Determina si un entero es primo.
 */
public class EsPrimo {

    /**
     * Indica si n es primo. Los números menores a 2 no son primos.
     *
     * @param n entero a evaluar
     * @return true si n es primo
     */
    public static boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        for (int n : new int[]{0, 1, 2, 7, 9, 13}) {
            System.out.println(n + " es primo? " + esPrimo(n));
        }
    }
}
