package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.util.List;
import java.util.stream.Collectors;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Arma el link de Google Maps para las personas seleccionadas. Con una sola
 * persona es una búsqueda del punto; con varias, un recorrido que pasa por
 * todos los puntos, que es la forma en que Maps acepta varios en una URL.
 */
public final class EnlaceDeMapas {

    /** Google Maps admite una cantidad limitada de puntos en la URL de un recorrido. */
    public static final int MAXIMO_DE_PUNTOS = 10;

    private static final String URL_DE_UN_PUNTO = "https://www.google.com/maps/search/?api=1&query=";
    private static final String URL_DE_RECORRIDO = "https://www.google.com/maps/dir/";

    private EnlaceDeMapas() {
    }

    /**
     * pre: {@code personas} tiene entre 1 y {@link #MAXIMO_DE_PUNTOS} personas.
     * post: devuelve la URL de Google Maps con sus geocodificaciones.
     */
    public static String crear(List<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        ValidacionesUtiles.validarRango(personas.size(), 1, MAXIMO_DE_PUNTOS, "cantidad de personas para el mapa");
        if (personas.size() == 1) {
            return URL_DE_UN_PUNTO + personas.get(0).geocodificacion().aTexto();
        }
        return URL_DE_RECORRIDO + personas.stream()
                .map(persona -> persona.geocodificacion().aTexto())
                .collect(Collectors.joining("/"));
    }
}
