package ar.uba.fi.cb100.material.i03_poo;

/** Otra figura concreta con su propia implementación de área y perímetro. */
public class Rectangulo extends FiguraBase {

    private final double base;
    private final double altura;

    public Rectangulo(double base, double altura) {
        super("Rectángulo");
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double area() {
        return base * altura;
    }

    @Override
    public double perimetro() {
        return 2 * (base + altura);
    }
}
