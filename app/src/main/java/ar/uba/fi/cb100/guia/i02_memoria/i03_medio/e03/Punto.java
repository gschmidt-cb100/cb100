package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e03;

import java.util.Objects;

/**
 * Ejercicio 03 (Punto).
 *
 * Clase de valor (NO record) que implementa correctamente el contrato
 * equals/hashCode: si dos objetos son equals, deben compartir hashCode.
 */
public final class Punto {

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Punto otro = (Punto) o;
        return x == otro.x && y == otro.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Punto(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Punto a = new Punto(1, 2);
        Punto b = new Punto(1, 2);
        Punto c = new Punto(3, 4);
        System.out.println(a + " equals " + b + " -> " + a.equals(b)); // true
        System.out.println("mismo hashCode -> " + (a.hashCode() == b.hashCode())); // true
        System.out.println(a + " equals " + c + " -> " + a.equals(c)); // false
    }
}
