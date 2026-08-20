package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e07;

/**
 * e07 - Objetos inmutables con record.
 *
 * Un record es inmutable: sus componentes no cambian. En vez de modificar,
 * se crea un nuevo objeto. conX devuelve un Punto NUEVO y deja el original intacto.
 */
public record Punto(int x, int y) {

    /**
     * Devuelve un nuevo Punto con la coordenada x cambiada.
     *
     * @param nx nueva coordenada x
     * @return nuevo Punto(nx, y); el original no se modifica
     */
    public Punto conX(int nx) {
        return new Punto(nx, y);
    }

    public static void main(String[] args) {
        Punto p = new Punto(1, 2);
        Punto q = p.conX(99);
        System.out.println("original = " + p);
        System.out.println("nuevo    = " + q);
    }
}
