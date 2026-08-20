package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e03;

/** Implementación concreta de Figura: círculo definido por su radio. */
public class Circulo implements Figura {

    private final double radio;

    public Circulo(double radio) {
        if (radio < 0) {
            throw new IllegalArgumentException("El radio no puede ser negativo");
        }
        this.radio = radio;
    }

    @Override
    public double area() {
        return Math.PI * radio * radio;
    }
}
