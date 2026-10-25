package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/** Carga a granel (trigo, arena, soja): un material y su peso en toneladas. */
public record Granel(String material, double toneladas) implements Carga {

    public Granel {
        ValidacionesUtiles.validarDistintoDeNull(material, "material");
        ValidacionesUtiles.validarFalso(material.isBlank(), "El material no puede estar vacío");
        ValidacionesUtiles.esMayorQueCero(toneladas, "toneladas de granel");
    }

    @Override
    public TipoDeCarga getTipo() {
        return TipoDeCarga.GRANEL;
    }

    @Override
    public double getPeso() {
        return toneladas;
    }

    @Override
    public String getDescripcion() {
        return toneladas + " t de " + material;
    }
}
