package ar.uba.fi.cb100.guia.i02_memoria.i03_medio.e07;

import java.util.Objects;

/**
 * Ejercicio 07 (IdentidadIgualdad).
 *
 * Distingue IDENTIDAD (== compara referencias: son el mismo objeto en
 * memoria) de IGUALDAD (equals compara contenido). Dos objetos pueden ser
 * iguales sin ser el mismo.
 */
public final class IdentidadIgualdad {

    private IdentidadIgualdad() {
    }

    public static boolean sonElMismo(Object a, Object b) {
        return a == b;
    }

    public static boolean sonIguales(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static void main(String[] args) {
        String x = new String("hola");
        String y = new String("hola");
        String alias = x;

        System.out.println("sonElMismo(x, y):     " + sonElMismo(x, y));     // false
        System.out.println("sonIguales(x, y):     " + sonIguales(x, y));     // true
        System.out.println("sonElMismo(x, alias): " + sonElMismo(x, alias)); // true
    }
}
