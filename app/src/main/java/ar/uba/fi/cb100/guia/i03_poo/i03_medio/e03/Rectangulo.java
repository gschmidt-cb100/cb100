package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e03;

/** Implementación concreta de Figura: rectángulo definido por base y altura. */
public class Rectangulo implements Figura {

    private final double base;
    private final double altura;

    public Rectangulo(double base, double altura) {
        if (base < 0 || altura < 0) {
            throw new IllegalArgumentException("Las dimensiones no pueden ser negativas");
        }
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double area() {
        return base * altura;
    }
}
