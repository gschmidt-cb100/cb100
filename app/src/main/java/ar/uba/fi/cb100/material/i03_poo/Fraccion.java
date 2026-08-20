package ar.uba.fi.cb100.material.i03_poo;

import java.util.Objects;

/**
 * Muestra los <b>métodos estándar</b> que suele tener una clase de valor:
 * {@code equals}, {@code hashCode}, {@code compareTo} ({@link Comparable}),
 * {@code toString}, y un <b>constructor copia</b> (preferible a {@code clone}).
 * La fracción se guarda siempre simplificada (invariante).
 */
public final class Fraccion implements Comparable<Fraccion> {

    private final int numerador;
    private final int denominador;

    public Fraccion(int numerador, int denominador) {
        if (denominador == 0) throw new IllegalArgumentException("denominador cero");
        int signo = denominador < 0 ? -1 : 1;
        int g = mcd(Math.abs(numerador), Math.abs(denominador));
        this.numerador = signo * numerador / g;
        this.denominador = Math.abs(denominador) / g;
    }

    /** Constructor copia: crea una fracción nueva igual a otra. */
    public Fraccion(Fraccion otra) {
        this.numerador = otra.numerador;
        this.denominador = otra.denominador;
    }

    private static int mcd(int a, int b) {
        return b == 0 ? Math.max(a, 1) : mcd(b, a % b);
    }

    public double valor() {
        return (double) numerador / denominador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraccion f)) return false;
        return numerador == f.numerador && denominador == f.denominador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerador, denominador);
    }

    @Override
    public int compareTo(Fraccion o) {           // compara sin perder precisión
        return Long.compare((long) numerador * o.denominador,
                            (long) o.numerador * denominador);
    }

    @Override
    public String toString() {
        return numerador + "/" + denominador;
    }

    public static void main(String[] args) {
        Fraccion a = new Fraccion(1, 2);
        Fraccion b = new Fraccion(2, 4);          // se simplifica a 1/2
        System.out.println(a + " equals " + b + " ? " + a.equals(b));      // true
        System.out.println("mismo hashCode ? " + (a.hashCode() == b.hashCode())); // true
        System.out.println("1/2 compareTo 3/4: " + a.compareTo(new Fraccion(3, 4))); // negativo
        Fraccion copia = new Fraccion(a);
        System.out.println("copia: " + copia + " equals a ? " + a.equals(copia));   // true
    }
}
