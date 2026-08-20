package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e08;

/**
 * e08: determinar si un numero es par o impar usando recursion mutua,
 * sin utilizar los operadores {@code %} ni {@code /}.
 *
 * <p>Idea: un numero es par si su antecesor es impar, y es impar si su
 * antecesor es par. Se apoya en dos casos base:</p>
 * <ul>
 *   <li>0 es par (y no es impar)</li>
 *   <li>se trabaja con el valor absoluto para soportar negativos</li>
 * </ul>
 */
public final class ParImpar {

    private ParImpar() {
    }

    /**
     * Indica si {@code n} es par usando recursion mutua con {@link #esImpar(int)}.
     *
     * @param n numero entero (puede ser negativo)
     * @return {@code true} si {@code n} es par
     */
    public static boolean esPar(int n) {
        int abs = Math.abs(n);
        if (abs == 0) {
            return true; // caso base: 0 es par
        }
        return esImpar(abs - 1); // n es par si n-1 es impar
    }

    /**
     * Indica si {@code n} es impar usando recursion mutua con {@link #esPar(int)}.
     *
     * @param n numero entero (puede ser negativo)
     * @return {@code true} si {@code n} es impar
     */
    public static boolean esImpar(int n) {
        int abs = Math.abs(n);
        if (abs == 0) {
            return false; // caso base: 0 no es impar
        }
        return esPar(abs - 1); // n es impar si n-1 es par
    }

    public static void main(String[] args) {
        for (int i = -3; i <= 5; i++) {
            System.out.println(i + " -> par=" + esPar(i) + ", impar=" + esImpar(i));
        }
    }
}
