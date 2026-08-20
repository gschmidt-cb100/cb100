package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e07;

/** Cuadrado: implementa el area como lado al cuadrado. */
public class Cuadrado extends Forma {

    private final double lado;

    public Cuadrado(double lado) {
        this.lado = lado;
    }

    @Override
    public double area() {
        return lado * lado;
    }

    public static void main(String[] args) {
        Forma f = new Cuadrado(3);
        System.out.println("area = " + f.area());
    }
}
