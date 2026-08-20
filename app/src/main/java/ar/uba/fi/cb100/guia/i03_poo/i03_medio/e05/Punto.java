package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e05;

import java.util.Objects;

/**
 * Punto en el plano con coordenadas x e y.
 * Redefine equals y hashCode de forma coherente: dos puntos son iguales
 * si coinciden ambas coordenadas, y objetos iguales devuelven el mismo hashCode.
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // misma referencia
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // null o distinto tipo
        }
        Punto otro = (Punto) obj;
        return x == otro.x && y == otro.y;
    }

    @Override
    public int hashCode() {
        // Coherente con equals: se basa en los mismos campos.
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        Punto c = new Punto(3, 4);

        System.out.println("a.equals(b): " + a.equals(b)); // true
        System.out.println("a.equals(c): " + a.equals(c)); // false
        System.out.println("a.equals(null): " + a.equals(null)); // false
        System.out.println("hashCode iguales: " + (a.hashCode() == b.hashCode()));
    }
}
