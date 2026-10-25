package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * La implementación común de {@link VagonCargable}: un arreglo de cargas con
 * un máximo de cantidad y un máximo de peso. Lo único que cada subclase
 * decide es <b>qué tipos de carga admite</b> ({@link #admiteTipo}); el resto
 * (aceptar, agregar, quitar, lleno, vacío, pesos) es igual para todos los
 * vagones cargables y por eso vive acá, una sola vez.
 * <p>
 * Invariantes: {@code 0 <= cantidad <= cargas.length} y
 * {@code getPesoDeCarga() <= pesoMaximo}.
 */
public abstract class VagonConCargas extends Vagon implements VagonCargable {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Carga[] cargas;
    private int cantidad;
    private final double pesoMaximo;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud}, {@code cantidadMaximaDeCargas} y {@code pesoMaximo} son mayores que cero.
     * post: crea el vagón vacío, con lugar para esa cantidad de cargas y ese peso [t].
     */
    protected VagonConCargas(double longitud, int cantidadMaximaDeCargas, double pesoMaximo) {
        super(longitud);
        ValidacionesUtiles.esMayorQueCero(cantidadMaximaDeCargas, "cantidad máxima de cargas");
        ValidacionesUtiles.esMayorQueCero(pesoMaximo, "peso máximo");
        this.cargas = new Carga[cantidadMaximaDeCargas];
        this.cantidad = 0;
        this.pesoMaximo = pesoMaximo;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public boolean aceptaCarga(Carga carga) {
        ValidacionesUtiles.validarDistintoDeNull(carga, "carga");
        return admiteTipo(carga.getTipo())
                && !estaLleno()
                && carga.getPeso() <= getPesoDisponible();
    }

    @Override
    public void agregarCarga(Carga carga) {
        ValidacionesUtiles.validarDistintoDeNull(carga, "carga");
        ValidacionesUtiles.validarFalso(!admiteTipo(carga.getTipo()),
                "Este vagón no admite " + carga.getTipo() + ": " + carga.getDescripcion());
        ValidacionesUtiles.validarEstadoFalso(estaLleno(), "El vagón está lleno: " + this);
        ValidacionesUtiles.validarFalso(carga.getPeso() > getPesoDisponible(),
                "No entra por peso: " + carga.getDescripcion() + ", disponibles " + getPesoDisponible() + " t");
        cargas[cantidad] = carga;
        cantidad++;
    }

    @Override
    public void quitarCarga(Carga carga) {
        int posicion = posicionDe(carga);
        ValidacionesUtiles.validarFalso(posicion < 0, "Esa carga no está en el vagón: " + carga);
        for (int i = posicion; i < cantidad - 1; i++) {
            cargas[i] = cargas[i + 1];           // corre las siguientes un lugar
        }
        cantidad--;
        cargas[cantidad] = null;                 // no retener la referencia
    }

    @Override
    public boolean contiene(Carga carga) {
        return posicionDe(carga) >= 0;
    }

    @Override
    public boolean estaVacio() {
        return cantidad == 0;
    }

    @Override
    public boolean estaLleno() {
        return cantidad == cargas.length || getPesoDisponible() <= 0;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /** post: devuelve el índice de la carga en el arreglo, o -1 si no está. */
    private int posicionDe(Carga carga) {
        for (int i = 0; i < cantidad; i++) {
            if (cargas[i].equals(carga)) {
                return i;
            }
        }
        return -1;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + ", " + cantidad + "/" + cargas.length + " cargas, "
                + getPesoDeCarga() + "/" + pesoMaximo + " t";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    @Override
    public int getCantidadDeCargas() {
        return cantidad;
    }

    @Override
    public int getLugaresLibres() {
        return cargas.length - cantidad;
    }

    @Override
    public double getPesoDeCarga() {
        double total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += cargas[i].getPeso();
        }
        return total;
    }

    @Override
    public double getPesoDisponible() {
        return pesoMaximo - getPesoDeCarga();
    }

    /** post: devuelve el peso máximo [t] que admite el vagón. */
    public double getPesoMaximo() {
        return pesoMaximo;
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de cargas.
     * post: devuelve la carga de esa posición, en orden de subida.
     */
    public Carga getCarga(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, cantidad, "posición de la carga");
        return cargas[posicion - 1];
    }
}
