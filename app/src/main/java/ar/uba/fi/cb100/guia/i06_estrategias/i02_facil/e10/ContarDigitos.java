package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e10;

/**
 * e10: cantidad de digitos de un numero, calculada de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: un numero con un solo digito (entre 0 y 9) tiene 1 digito</li>
 *   <li>Paso recursivo: 1 + cantidad de digitos de n/10</li>
 *   <li>Para negativos se trabaja con el valor absoluto</li>
 * </ul>
 */
public final class ContarDigitos {

    private ContarDigitos() {
    }

    /**
     * Devuelve la cantidad de digitos de {@code n} recursivamente.
     * Por convencion, el 0 tiene 1 digito y los negativos se cuentan como su absoluto.
     *
     * @param n numero a analizar
     * @return la cantidad de digitos
     */
    public static int cantidadDeDigitos(long n) {
        long abs = Math.abs(n);
        if (abs < 10) {
            return 1; // caso base: un unico digito (incluye el 0)
        }
        return 1 + cantidadDeDigitos(abs / 10); // paso recursivo
    }

    public static void main(String[] args) {
        System.out.println("digitos de 0 = " + cantidadDeDigitos(0));
        System.out.println("digitos de 7 = " + cantidadDeDigitos(7));
        System.out.println("digitos de 12345 = " + cantidadDeDigitos(12345));
        System.out.println("digitos de -908 = " + cantidadDeDigitos(-908));
    }
}
