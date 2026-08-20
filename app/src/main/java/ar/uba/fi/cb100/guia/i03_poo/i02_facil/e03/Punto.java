package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e03;

import java.util.Objects;

/**
 * e03: Punto con equals y hashCode.
 * Dos puntos con las mismas coordenadas se consideran iguales.
 * Regla: si equals devuelve true, hashCode debe coincidir.
 */
public class Punto {

    private final int x;
    private final int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Dos puntos son iguales si tienen las mismas coordenadas. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Punto)) {
            return false;
        }
        Punto otro = (Punto) obj;
        return x == otro.x && y == otro.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static void main(String[] args) {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        System.out.println("iguales: " + a.equals(b));
        System.out.println("mismo hashCode: " + (a.hashCode() == b.hashCode()));
    }
}
