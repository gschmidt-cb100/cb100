package ar.uba.fi.cb100.material.i03_poo;

/** Figura concreta: hereda de {@link FiguraBase} y define su área y perímetro. */
public class Circulo extends FiguraBase {

    private final double radio;

    public Circulo(double radio) {
        super("Círculo");
        this.radio = radio;
    }

    @Override
    public double area() {
        return Math.PI * radio * radio;
    }

    @Override
    public double perimetro() {
        return 2 * Math.PI * radio;
    }
}
