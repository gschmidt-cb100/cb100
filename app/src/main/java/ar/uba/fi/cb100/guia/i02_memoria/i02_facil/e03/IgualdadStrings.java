package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e03;

/**
 * e03 - Igualdad de referencia (==) vs igualdad de contenido (equals).
 *
 * Con objetos, == compara REFERENCIAS (si son el mismo objeto),
 * mientras que equals compara el CONTENIDO.
 * Por eso al usar new String(...) el == da false pero equals da true.
 */
public class IgualdadStrings {

    /**
     * Compara dos Strings por referencia y por contenido.
     *
     * @param s1 primera cadena
     * @param s2 segunda cadena
     * @return {s1 == s2, s1.equals(s2)}
     */
    public static boolean[] comparar(String s1, String s2) {
        return new boolean[]{s1 == s2, s1.equals(s2)};
    }

    public static void main(String[] args) {
        String a = new String("x");
        String b = new String("x");
        boolean[] r = comparar(a, b);
        System.out.println("s1 == s2      -> " + r[0]);
        System.out.println("s1.equals(s2) -> " + r[1]);
    }
}
