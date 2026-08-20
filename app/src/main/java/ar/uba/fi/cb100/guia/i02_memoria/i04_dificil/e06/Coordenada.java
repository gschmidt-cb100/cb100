package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e06;

import java.util.Objects;

/**
 * Coordenada 3D inmutable. Implementa equals/hashCode segun el contrato:
 * dos coordenadas son iguales si coinciden x, y y z.
 */
public class Coordenada {
    private final int x;
    private final int y;
    private final int z;

    public Coordenada(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;            // mismo objeto
        }
        if (o == null || getClass() != o.getClass()) {
            return false;           // null u otra clase
        }
        Coordenada otra = (Coordenada) o;
        return x == otra.x && y == otra.y && z == otra.z;
    }

    @Override
    public int hashCode() {
        // Objetos iguales deben tener el mismo hashCode.
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Coordenada(" + x + ", " + y + ", " + z + ")";
    }

    public static void main(String[] args) {
        Coordenada a = new Coordenada(1, 2, 3);
        Coordenada b = new Coordenada(1, 2, 3);
        Coordenada c = new Coordenada(9, 9, 9);
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.equals(c): " + a.equals(c));
        System.out.println("mismo hash a y b: " + (a.hashCode() == b.hashCode()));
    }
}
