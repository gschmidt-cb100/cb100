package ar.uba.fi.cb100.guia.i06_estrategias.i02_facil.e06;

/**
 * e06: inversion de un String de forma recursiva.
 *
 * <p>Definicion:</p>
 * <ul>
 *   <li>Caso base: la cadena vacia (o de un caracter) invertida es ella misma</li>
 *   <li>Paso recursivo: invertir el resto y agregar el primer caracter al final</li>
 * </ul>
 */
public final class InvertirString {

    private InvertirString() {
    }

    /**
     * Devuelve {@code s} con sus caracteres en orden inverso.
     *
     * @param s cadena a invertir (no nula)
     * @return la cadena invertida
     */
    public static String invertir(String s) {
        if (s.isEmpty()) {
            return s; // caso base
        }
        return invertir(s.substring(1)) + s.charAt(0); // paso recursivo
    }

    public static void main(String[] args) {
        System.out.println("invertir(\"hola\") = " + invertir("hola"));
        System.out.println("invertir(\"CB100\") = " + invertir("CB100"));
        System.out.println("invertir(\"neuquen\") = " + invertir("neuquen"));
    }
}
