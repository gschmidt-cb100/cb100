package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

/** Rectángulo definido por su base y su altura. */
public class Rectangulo extends FiguraBase {

    private final double base;
    private final double altura;

    public Rectangulo(double base, double altura) {
        super("Rectángulo");
        if (base < 0 || altura < 0) {
            throw new IllegalArgumentException("Base y altura no pueden ser negativas");
        }
        this.base = base;
        this.altura = altura;
    }

    public double base() {
        return base;
    }

    public double altura() {
        return altura;
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
