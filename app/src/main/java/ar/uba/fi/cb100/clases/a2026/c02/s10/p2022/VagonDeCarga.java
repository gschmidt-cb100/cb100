package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Vagón que lleva carga: agrega a {@link Vagon} la capacidad de carga en
 * toneladas y cuánta carga hay a bordo. Invariante: {@code 0 <= carga <= capacidad}.
 */
public class VagonDeCarga extends Vagon {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final double capacidadDeCarga;
    private double carga;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud} y {@code capacidadDeCarga} (en toneladas) son mayores que cero.
     * post: crea el vagón vacío. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public VagonDeCarga(double longitud, double capacidadDeCarga) {
        super(longitud);
        ValidacionesUtiles.esMayorQueCero(capacidadDeCarga, "capacidad de carga");
        this.capacidadDeCarga = capacidadDeCarga;
        this.carga = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    public double cargar(double toneladas) {
        validarToneladas(toneladas);
        double entra = Math.min(toneladas, getCapacidadRestanteDeCarga());
        carga += entra;
        return entra;
    }

    public double getCapacidadRestanteDeCarga() {
        return capacidadDeCarga - carga;
    }

    @Override
    public boolean estaVacio() {
        return carga == 0;
    }

    public double descargar(double toneladas) {
        validarToneladas(toneladas);
        double sale = Math.min(toneladas, carga);
        carga -= sale;
        return sale;
    }

    private void validarToneladas(double toneladas) {
        ValidacionesUtiles.esMayorQueCero(toneladas, "toneladas");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + ", " + carga + "/" + capacidadDeCarga + " t";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public double getCapacidadDeCarga() {
        return capacidadDeCarga;
    }

    public double getCarga() {
        return carga;
    }
}
