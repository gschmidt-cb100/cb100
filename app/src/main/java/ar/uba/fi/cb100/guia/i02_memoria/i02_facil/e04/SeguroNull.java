package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e04;

/**
 * e04 - Manejo defensivo de null.
 *
 * null significa "ninguna referencia". Antes de usar un objeto conviene
 * chequear si es null para evitar NullPointerException.
 */
public class SeguroNull {

    /**
     * Devuelve la cadena recibida, o "" si es null.
     *
     * @param s cadena que puede ser null
     * @return "" si s es null, si no s
     */
    public static String seguro(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println("seguro(null)  = \"" + seguro(null) + "\"");
        System.out.println("seguro(\"hola\") = \"" + seguro("hola") + "\"");
    }
}
