package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e02;

/**
 * TDA Vagon: un vagón de carga con capacidad fija en kilos.
 *
 * <p>La lógica de SU carga vive acá (delegación): el Tren nunca toca
 * {@code cargaActual} de un vagón, siempre pasa por estos métodos.</p>
 */
public class Vagon {

    private final double capacidadEnKilos;
    private double cargaActualEnKilos;

    /**
     * pre: {@code capacidadEnKilos} es mayor que 0.<br>
     * post: crea un vagón vacío con esa capacidad.
     *
     * @param capacidadEnKilos capacidad máxima de carga.
     * @throws IllegalArgumentException si la capacidad no es positiva.
     */
    public Vagon(double capacidadEnKilos) {
        if (capacidadEnKilos <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que 0, se recibió: " + capacidadEnKilos);
        }
        this.capacidadEnKilos = capacidadEnKilos;
        this.cargaActualEnKilos = 0;
    }

    /**
     * Carga kilos en este vagón, validando que entren.
     *
     * <p>pre: {@code kilos} es mayor que 0 y no supera {@link #lugarLibre()}.<br>
     * post: la carga actual aumenta en {@code kilos}.</p>
     *
     * @param kilos los kilos a cargar.
     * @throws IllegalArgumentException si {@code kilos} no es positivo.
     * @throws IllegalStateException si la carga no entra en el lugar libre.
     */
    public void cargar(double kilos) {
        if (kilos <= 0) {
            throw new IllegalArgumentException(
                    "Los kilos a cargar deben ser mayores que 0, se recibió: " + kilos);
        }
        if (kilos > lugarLibre()) {
            throw new IllegalStateException(
                    "No entran " + kilos + " kg: el lugar libre es " + lugarLibre() + " kg");
        }
        cargaActualEnKilos += kilos;
    }

    /**
     * pre: -.<br>
     * post: devuelve cuántos kilos más admite el vagón (calculable a partir
     * de capacidad y carga: MÉTODO, no atributo).
     *
     * @return el lugar libre en kilos.
     */
    public double lugarLibre() {
        return capacidadEnKilos - cargaActualEnKilos;
    }

    /**
     * pre: -.<br>
     * post: devuelve la carga actual en kilos.
     *
     * @return la carga actual.
     */
    public double cargaActual() {
        return cargaActualEnKilos;
    }

    /**
     * pre: -.<br>
     * post: devuelve la capacidad máxima en kilos.
     *
     * @return la capacidad del vagón.
     */
    public double capacidad() {
        return capacidadEnKilos;
    }

    /**
     * pre: -.<br>
     * post: devuelve la carga y la capacidad del vagón.
     */
    @Override
    public String toString() {
        return "Vagon[" + cargaActualEnKilos + "/" + capacidadEnKilos + " kg]";
    }
}
