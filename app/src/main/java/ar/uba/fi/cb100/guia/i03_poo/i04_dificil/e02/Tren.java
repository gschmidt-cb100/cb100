package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e02;

/**
 * TDA Tren: una formación de vagones sobre un arreglo nativo cuya cantidad
 * máxima de vagones se fija por parámetro del constructor.
 *
 * <p>Delegación: el Tren decide EN QUÉ vagón cargar, pero el CÓMO (validar
 * que entre, actualizar la carga) es responsabilidad del {@link Vagon}.</p>
 *
 * <p>Invariante: las posiciones [0, cantidadDeVagones) del arreglo tienen
 * vagones enganchados, en orden de enganche.</p>
 */
public class Tren {

    private final Vagon[] vagones;
    private int cantidadDeVagones;

    /**
     * pre: {@code maximoDeVagones} es mayor que 0.<br>
     * post: crea un tren sin vagones que admite hasta {@code maximoDeVagones}.
     *
     * @param maximoDeVagones cantidad máxima de vagones enganchables.
     * @throws IllegalArgumentException si el máximo no es positivo.
     */
    public Tren(int maximoDeVagones) {
        if (maximoDeVagones <= 0) {
            throw new IllegalArgumentException(
                    "El máximo de vagones debe ser mayor que 0, se recibió: " + maximoDeVagones);
        }
        this.vagones = new Vagon[maximoDeVagones];
        this.cantidadDeVagones = 0;
    }

    /**
     * Engancha un vagón al final de la formación. Se recibe el TDA Vagon
     * ENTERO, no su capacidad suelta.
     *
     * <p>pre: {@code vagon} no es null y hay lugar en la formación.<br>
     * post: el vagón queda enganchado al final.</p>
     *
     * @param vagon el vagón a enganchar.
     * @throws IllegalArgumentException si {@code vagon} es null.
     * @throws IllegalStateException si la formación está completa.
     */
    public void engancharVagon(Vagon vagon) {
        if (vagon == null) {
            throw new IllegalArgumentException("El vagón no puede ser null");
        }
        if (cantidadDeVagones == vagones.length) {
            throw new IllegalStateException(
                    "La formación está completa (máximo: " + vagones.length + " vagones)");
        }
        vagones[cantidadDeVagones] = vagon;
        cantidadDeVagones++;
    }

    /**
     * Desengancha el último vagón de la formación.
     *
     * <p>pre: hay al menos un vagón enganchado.<br>
     * post: el último vagón deja de pertenecer al tren y se devuelve;
     * la posición liberada queda en null para no retener la referencia.</p>
     *
     * @return el vagón desenganchado.
     * @throws IllegalStateException si no hay vagones.
     */
    public Vagon desengancharUltimo() {
        if (cantidadDeVagones == 0) {
            throw new IllegalStateException("No hay vagones para desenganchar");
        }
        cantidadDeVagones--;
        Vagon desenganchado = vagones[cantidadDeVagones];
        vagones[cantidadDeVagones] = null;
        return desenganchado;
    }

    /**
     * Carga un cargamento ENTERO en el PRIMER vagón (en orden de enganche)
     * que tenga lugar suficiente. El tren delega en el vagón: pregunta
     * {@code lugarLibre()} y ordena {@code cargar(kilos)}.
     *
     * <p>pre: {@code kilos} es mayor que 0 y algún vagón tiene lugar
     * suficiente para el cargamento completo (no se parte).<br>
     * post: la carga queda en el primer vagón con lugar suficiente.</p>
     *
     * @param kilos los kilos a cargar.
     * @throws IllegalArgumentException si {@code kilos} no es positivo.
     * @throws IllegalStateException si el cargamento no entra entero en ningún vagón.
     */
    public void cargar(double kilos) {
        if (kilos <= 0) {
            throw new IllegalArgumentException(
                    "Los kilos a cargar deben ser mayores que 0, se recibió: " + kilos);
        }
        for (int i = 0; i < cantidadDeVagones; i++) {
            if (vagones[i].lugarLibre() >= kilos) {
                vagones[i].cargar(kilos);
                return;
            }
        }
        throw new IllegalStateException(
                "Ningún vagón tiene lugar para un cargamento de " + kilos + " kg");
    }

    /**
     * pre: -.<br>
     * post: devuelve la suma de las cargas de todos los vagones (0 si no hay).
     * Es calculable recorriendo: MÉTODO, no atributo.
     *
     * @return la carga total en kilos.
     */
    public double cargaTotal() {
        double total = 0;
        for (int i = 0; i < cantidadDeVagones; i++) {
            total += vagones[i].cargaActual();
        }
        return total;
    }

    /**
     * pre: -.<br>
     * post: devuelve el vagón con más carga, o null si no hay vagones
     * ("sin valor" = null, nunca un centinela).
     *
     * @return el vagón más cargado, o null.
     */
    public Vagon vagonMasCargado() {
        Vagon maximo = null;
        for (int i = 0; i < cantidadDeVagones; i++) {
            if (maximo == null || vagones[i].cargaActual() > maximo.cargaActual()) {
                maximo = vagones[i];
            }
        }
        return maximo;
    }

    /**
     * pre: -.<br>
     * post: devuelve cuántos vagones tiene la formación.
     *
     * @return la cantidad de vagones enganchados.
     */
    public int cantidadDeVagones() {
        return cantidadDeVagones;
    }

    /**
     * pre: -.<br>
     * post: devuelve la formación completa, vagón por vagón.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("Tren (")
                .append(cantidadDeVagones).append('/').append(vagones.length).append(" vagones)");
        for (int i = 0; i < cantidadDeVagones; i++) {
            texto.append("\n  ").append(i).append(": ").append(vagones[i]);
        }
        return texto.toString();
    }

    /**
     * Demostración breve: el reparto va al primer vagón con lugar suficiente.
     */
    public static void main(String[] args) {
        Tren tren = new Tren(3);
        tren.engancharVagon(new Vagon(1000));
        tren.engancharVagon(new Vagon(2000));

        tren.cargar(800);   // entra en el primero
        tren.cargar(500);   // el primero tiene 200 de lugar: va al segundo
        System.out.println(tren);
        System.out.println("cargaTotal(): " + tren.cargaTotal() + " kg");
        System.out.println("vagonMasCargado(): " + tren.vagonMasCargado());

        Vagon desenganchado = tren.desengancharUltimo();
        System.out.println("desengancharUltimo(): " + desenganchado);
        System.out.println(tren);
    }
}
