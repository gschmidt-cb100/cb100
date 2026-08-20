package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e09;

/**
 * e09: Punto con constructor copia.
 * El constructor copia crea un objeto nuevo con el mismo estado que
 * otro. Al ser objetos independientes, modificar uno no afecta al otro.
 */
public class Punto {

    private int x;
    private int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Constructor copia: crea un punto nuevo a partir de otro. */
    public Punto(Punto otro) {
        this.x = otro.x;
        this.y = otro.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static void main(String[] args) {
        Punto original = new Punto(1, 2);
        Punto copia = new Punto(original);
        copia.setX(99);
        System.out.println("original x = " + original.getX());
        System.out.println("copia x = " + copia.getX());
    }
}
