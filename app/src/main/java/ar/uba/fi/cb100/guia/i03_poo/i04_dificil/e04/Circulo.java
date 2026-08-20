package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e04;

/** Círculo definido por su radio. */
public class Circulo extends FiguraBase {

    private final double radio;

    public Circulo(double radio) {
        super("Círculo");
        if (radio < 0) {
            throw new IllegalArgumentException("El radio no puede ser negativo");
        }
        this.radio = radio;
    }

    public double radio() {
        return radio;
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
