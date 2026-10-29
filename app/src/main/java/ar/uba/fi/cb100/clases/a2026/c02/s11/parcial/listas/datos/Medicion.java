package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Medicion (clase de datos).
 * Una medición de temperatura tomada en una ciudad, con su estado.
 */
public class Medicion {

//ATRIBUTOS

    private final Ciudad ciudad;
    private final double temperatura;
    private final EstadoMedicion estado;

//CONSTRUCTORES

    /**
     * @param ciudad ciudad donde se tomó la medición, no null
     * @param temperatura temperatura medida
     * @param estado estado de la medición, no null
     * @throws IllegalArgumentException si la ciudad o el estado son null
     */
    public Medicion(Ciudad ciudad, double temperatura, EstadoMedicion estado) {
        ValidacionesUtiles.validarDistintoDeNull(ciudad, "ciudad");
        ValidacionesUtiles.validarDistintoDeNull(estado, "estado");
        this.ciudad = ciudad;
        this.temperatura = temperatura;
        this.estado = estado;
    }

//CONSULTAS

    /**
     * @return true si la medición está en estado VALIDA
     */
    public boolean esValida() {
        return this.estado == EstadoMedicion.VALIDA;
    }

//GETTERS

    public Ciudad getCiudad() {
        return ciudad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public EstadoMedicion getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return ciudad + ": " + temperatura + "° [" + estado + "]";
    }
}
