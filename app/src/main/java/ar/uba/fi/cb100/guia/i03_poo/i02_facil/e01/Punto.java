package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e01;

/**
 * e01: Punto con campos privados, constructor y getters.
 * Ejemplo basico de encapsulamiento: los atributos no se acceden
 * directamente, sino a traves de metodos publicos.
 */
public class Punto {

    // Los campos son privados: solo la propia clase los ve directamente.
    private final int x;
    private final int y;

    /** Construye un punto a partir de sus coordenadas. */
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Devuelve la coordenada x. */
    public int getX() {
        return x;
    }

    /** Devuelve la coordenada y. */
    public int getY() {
        return y;
    }

    public static void main(String[] args) {
        Punto p = new Punto(3, 4);
        System.out.println("x = " + p.getX());
        System.out.println("y = " + p.getY());
    }
}
