package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e04;

/**
 * Ejercicio 04: Record Punto.
 * Representa un punto en el plano y calcula distancias.
 *
 * Punto inmutable en el plano cartesiano.
 */
public record Punto(double x, double y) {

    /**
     * Distancia euclídea entre este punto y otro.
     *
     * @param o otro punto
     * @return distancia
     */
    public double distanciaA(Punto o) {
        double dx = this.x - o.x;
        double dy = this.y - o.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void main(String[] args) {
        var origen = new Punto(0, 0);
        var p = new Punto(3, 4);
        System.out.println("Punto p: " + p);
        System.out.println("Distancia de (0,0) a (3,4): " + origen.distanciaA(p));
    }
}
