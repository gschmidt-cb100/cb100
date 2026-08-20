package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e09;

/**
 * e09 - Igualdad en records: == vs equals.
 *
 * Cada new crea un objeto distinto, por eso == da false.
 * Pero un record genera automaticamente equals por CONTENIDO,
 * por eso dos Par(1,2) son equals aunque sean objetos distintos.
 */
public class IgualdadRecords {

    /** Par de enteros. El record genera equals/hashCode por contenido. */
    public static record Par(int a, int b) {
    }

    /**
     * Compara dos Par con el mismo contenido por referencia y por contenido.
     *
     * @return {p1 == p2, p1.equals(p2)} = {false, true}
     */
    public static boolean[] comparar() {
        Par p1 = new Par(1, 2);
        Par p2 = new Par(1, 2);
        return new boolean[]{p1 == p2, p1.equals(p2)};
    }

    public static void main(String[] args) {
        boolean[] r = comparar();
        System.out.println("p1 == p2      -> " + r[0]);
        System.out.println("p1.equals(p2) -> " + r[1]);
    }
}
