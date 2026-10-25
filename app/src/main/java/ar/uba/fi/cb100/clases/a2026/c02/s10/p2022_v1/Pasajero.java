package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/** Un pasajero es una carga de tipo PASAJERO con un peso promedio fijo. */
public record Pasajero(String nombre) implements Carga {

    /** Peso promedio [t] con el que se computa cada pasajero. */
    public static final double PESO_PROMEDIO = 0.08;

    public Pasajero {
        ValidacionesUtiles.validarDistintoDeNull(nombre, "nombre del pasajero");
        ValidacionesUtiles.validarFalso(nombre.isBlank(), "El nombre del pasajero no puede estar vacío");
    }

    @Override
    public TipoDeCarga getTipo() {
        return TipoDeCarga.PASAJERO;
    }

    @Override
    public double getPeso() {
        return PESO_PROMEDIO;
    }

    @Override
    public String getDescripcion() {
        return "pasajero " + nombre;
    }
}
