package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos;

import java.util.Locale;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Un punto del mapa: latitud y longitud en grados decimales. En el Excel
 * viaja en una sola celda con el formato {@code "latitud,longitud"}.
 */
public record Geocodificacion(double latitud, double longitud) {

    public Geocodificacion {
        ValidacionesUtiles.validarFalso(latitud < -90 || latitud > 90, "La latitud va de -90 a 90: " + latitud);
        ValidacionesUtiles.validarFalso(longitud < -180 || longitud > 180, "La longitud va de -180 a 180: " + longitud);
    }

    /**
     * pre: {@code texto} tiene la forma "latitud,longitud" con punto decimal.
     * post: devuelve el punto; si el texto no cumple, lanza {@link IllegalArgumentException}.
     */
    public static Geocodificacion desdeTexto(String texto) {
        ValidacionesUtiles.validarDistintoDeNull(texto, "geocodificación");
        String[] partes = texto.split(",");
        ValidacionesUtiles.validarFalso(partes.length != 2, "La geocodificación debe ser \"latitud,longitud\": " + texto);
        try {
            return new Geocodificacion(Double.parseDouble(partes[0].trim()), Double.parseDouble(partes[1].trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La geocodificación no es numérica: " + texto);
        }
    }

    /** post: devuelve "latitud,longitud" con punto decimal, como lo esperan el Excel y Google Maps. */
    public String aTexto() {
        return String.format(Locale.ROOT, "%.6f,%.6f", latitud, longitud);
    }
}
