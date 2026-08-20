package ar.uba.fi.cb100.material.i02_memoria;

/**
 * {@code ==} compara <b>identidad</b> (¿son el mismo objeto?);
 * {@code equals} compara <b>contenido</b> (¿valen lo mismo?).
 */
public class IdentidadVsIgualdad {

    record Punto(int x, int y) {}

    public static void main(String[] args) {
        String s1 = new String("java");
        String s2 = new String("java");
        System.out.println("s1 == s2      : " + (s1 == s2));       // false (objetos distintos)
        System.out.println("s1.equals(s2) : " + s1.equals(s2));    // true  (mismo contenido)

        Punto p1 = new Punto(1, 2);
        Punto p2 = new Punto(1, 2);
        System.out.println("p1 == p2      : " + (p1 == p2));       // false
        System.out.println("p1.equals(p2) : " + p1.equals(p2));    // true (record compara por valor)
    }
}
