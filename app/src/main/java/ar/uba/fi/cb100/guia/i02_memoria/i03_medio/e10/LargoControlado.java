package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e10;

/**
 * Ejercicio 10 (NpeControlada).
 *
 * Muestra la NullPointerException: invocar un metodo sobre una referencia
 * null la lanza. La version "segura" chequea null antes de usar la
 * referencia y devuelve un valor por defecto.
 */
public final class LargoControlado {

    private LargoControlado() {
    }

    /**
     * Lanza NullPointerException si s es null.
     */
    public static int largo(String s) {
        return s.length();
    }

    /**
     * Version defensiva: si s es null devuelve 0.
     */
    public static int largoSeguro(String s) {
        return s == null ? 0 : s.length();
    }

    public static void main(String[] args) {
        System.out.println("largo(\"hola\"): " + largo("hola"));       // 4
        System.out.println("largoSeguro(null): " + largoSeguro(null)); // 0
        try {
            largo(null);
        } catch (NullPointerException e) {
            System.out.println("largo(null) lanzo NullPointerException");
        }
    }
}
