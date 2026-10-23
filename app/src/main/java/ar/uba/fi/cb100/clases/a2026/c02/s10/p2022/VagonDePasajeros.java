package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Vagón que lleva pasajeros: agrega a {@link Vagon} la capacidad de pasajeros
 * y cuántos hay a bordo. Invariante: {@code 0 <= pasajeros <= capacidad}.
 */
public class VagonDePasajeros extends Vagon {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int capacidadDePasajeros;
    private int pasajeros;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code longitud} y {@code capacidadDePasajeros} son mayores que cero.
     * post: crea el vagón vacío. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public VagonDePasajeros(double longitud, int capacidadDePasajeros) {
        super(longitud);
        ValidacionesUtiles.esMayorQueCero(capacidadDePasajeros, "capacidad de pasajeros");
        this.capacidadDePasajeros = capacidadDePasajeros;
        this.pasajeros = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    public int subirPasajeros(int cantidad) {
        validarCantidad(cantidad);
        int suben = Math.min(cantidad, getCapacidadRestanteDePasajeros());
        pasajeros += suben;
        return suben;
    }

    public int getCapacidadRestanteDePasajeros() {
        return capacidadDePasajeros - pasajeros;
    }

    public int bajarPasajeros(int cantidad) {
        validarCantidad(cantidad);
        int bajan = Math.min(cantidad, pasajeros);
        pasajeros -= bajan;
        return bajan;
    }

    private void validarCantidad(int cantidad) {
        ValidacionesUtiles.esMayorQueCero(cantidad, "cantidad de pasajeros");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public boolean estaVacio() {
        return pasajeros == 0;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + pasajeros + "/" + capacidadDePasajeros + " pasajeros";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getCapacidadDePasajeros() {
        return capacidadDePasajeros;
    }

    public int getPasajeros() {
        return pasajeros;
    }
}
