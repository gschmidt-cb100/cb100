package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e10;

/**
 * e10 - Identidad de objetos con ==.
 *
 * == entre objetos responde: "son el mismo objeto en memoria?".
 * Dos alias de un mismo objeto dan true; dos objetos distintos (aunque
 * tengan igual contenido) dan false.
 */
public class MismoObjeto {

    /**
     * Indica si a y b son exactamente el mismo objeto.
     *
     * @param a primer objeto
     * @param b segundo objeto
     * @return true si a y b son la misma referencia
     */
    public static boolean mismoObjeto(Object a, Object b) {
        return a == b;
    }

    public static void main(String[] args) {
        String s = new String("hola");
        String alias = s;
        System.out.println("alias          -> " + mismoObjeto(s, alias));
        System.out.println("dos new String -> " + mismoObjeto(new String("hola"), new String("hola")));
    }
}
