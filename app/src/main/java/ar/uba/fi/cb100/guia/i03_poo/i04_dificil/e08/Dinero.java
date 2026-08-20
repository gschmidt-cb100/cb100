package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e08;

/**
 * Clase de valor inmutable que representa una cantidad de dinero en centavos.
 * Garantiza que {@code equals} y {@code compareTo} sean CONSISTENTES:
 * {@code a.compareTo(b) == 0} si y sólo si {@code a.equals(b)}.
 */
public final class Dinero implements Comparable<Dinero> {

    private final long centavos;

    public Dinero(long centavos) {
        this.centavos = centavos;
    }

    public long centavos() {
        return centavos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dinero otro)) {
            return false;
        }
        return centavos == otro.centavos;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(centavos);
    }

    @Override
    public int compareTo(Dinero otro) {
        return Long.compare(this.centavos, otro.centavos);
    }

    @Override
    public String toString() {
        long absCentavos = Math.abs(centavos);
        String signo = centavos < 0 ? "-" : "";
        return String.format("%s$%d.%02d", signo, absCentavos / 100, absCentavos % 100);
    }

    public static void main(String[] args) {
        Dinero a = new Dinero(1599); // $15.99
        Dinero b = new Dinero(1599);
        Dinero c = new Dinero(2000); // $20.00

        System.out.println("a = " + a);
        System.out.println("c = " + c);
        System.out.println("a.equals(b): " + a.equals(b) + " | compareTo==0: " + (a.compareTo(b) == 0));
        System.out.println("a.equals(c): " + a.equals(c) + " | compareTo==0: " + (a.compareTo(c) == 0));
        System.out.println("a < c: " + (a.compareTo(c) < 0));
    }
}
