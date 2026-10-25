package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/** Un contenedor identificado por código, con su peso en toneladas. */
public record Contenedor(String codigo, double toneladas) implements Carga {

    public Contenedor {
        ValidacionesUtiles.validarDistintoDeNull(codigo, "código del contenedor");
        ValidacionesUtiles.validarFalso(codigo.isBlank(), "El código del contenedor no puede estar vacío");
        ValidacionesUtiles.esMayorQueCero(toneladas, "toneladas del contenedor");
    }

    @Override
    public TipoDeCarga getTipo() {
        return TipoDeCarga.CONTENEDOR;
    }

    @Override
    public double getPeso() {
        return toneladas;
    }

    @Override
    public String getDescripcion() {
        return "contenedor " + codigo + " (" + toneladas + " t)";
    }
}
