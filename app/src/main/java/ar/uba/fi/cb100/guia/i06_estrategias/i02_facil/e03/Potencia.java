package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e03;

/**
 * e03: calculo de una potencia de exponente entero no negativo, de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: base^0 = 1</li>
 *   <li>Paso recursivo: base^exp = base * base^(exp-1)</li>
 * </ul>
 */
public final class Potencia {

    private Potencia() {
    }

    /**
     * Calcula {@code base} elevado a {@code exp} recursivamente.
     *
     * @param base la base
     * @param exp  el exponente, no negativo
     * @return {@code base^exp}
     * @throws IllegalArgumentException si {@code exp} es negativo
     */
    public static long potencia(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo: " + exp);
        }
        if (exp == 0) {
            return 1L; // caso base: todo numero elevado a 0 es 1
        }
        return base * potencia(base, exp - 1); // paso recursivo
    }

    public static void main(String[] args) {
        System.out.println("2^10 = " + potencia(2, 10));
        System.out.println("3^4 = " + potencia(3, 4));
        System.out.println("5^0 = " + potencia(5, 0));
    }
}
