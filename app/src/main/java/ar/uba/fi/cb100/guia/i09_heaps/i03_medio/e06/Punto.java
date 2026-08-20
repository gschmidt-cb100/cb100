package ar.uba.fi.cb100.guia.i09_heaps.i03_medio.e06;

/**
 * Un punto del plano.
 *
 * @param x abscisa
 * @param y ordenada
 */
public record Punto(double x, double y) {

    /**
     * Distancia euclídea al origen (0, 0).
     *
     * @return sqrt(x² + y²)
     */
    public double distanciaAlOrigen() {
        return Math.hypot(x, y);
    }
}
