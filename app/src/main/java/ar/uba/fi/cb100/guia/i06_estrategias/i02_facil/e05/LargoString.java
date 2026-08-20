package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e05;

/**
 * e05: calculo del largo de un String sin usar {@code length()}, de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: el largo de la cadena vacia es 0</li>
 *   <li>Paso recursivo: 1 + largo del substring que quita el primer caracter</li>
 * </ul>
 */
public final class LargoString {

    private LargoString() {
    }

    /**
     * Devuelve la cantidad de caracteres de {@code s} sin usar {@code length()}.
     *
     * @param s cadena a medir (no nula)
     * @return la longitud de {@code s}
     */
    public static int largo(String s) {
        if (s.isEmpty()) {
            return 0; // caso base
        }
        return 1 + largo(s.substring(1)); // paso recursivo: quita el primer caracter
    }

    public static void main(String[] args) {
        System.out.println("largo(\"\") = " + largo(""));
        System.out.println("largo(\"hola\") = " + largo("hola"));
        System.out.println("largo(\"algoritmos\") = " + largo("algoritmos"));
    }
}
