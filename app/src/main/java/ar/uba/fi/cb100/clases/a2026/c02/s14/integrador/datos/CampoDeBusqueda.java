package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos;

import java.util.Locale;
import java.util.function.Function;

/**
 * Los campos por los que se arman índices. Cada uno sabe extraer de una
 * persona su <b>clave</b> ya normalizada (minúsculas, sin espacios de más),
 * que es lo que se compara al buscar.
 */
public enum CampoDeBusqueda {
    DNI("DNI", persona -> String.valueOf(persona.dni())),
    NOMBRE("Nombre", Persona::nombre),
    APELLIDO("Apellido", Persona::apellido);

    private final String titulo;
    private final Function<Persona, String> extractor;

    CampoDeBusqueda(String titulo, Function<Persona, String> extractor) {
        this.titulo = titulo;
        this.extractor = extractor;
    }

    /** post: devuelve la clave normalizada de la persona para este campo. */
    public String claveDe(Persona persona) {
        return normalizar(extractor.apply(persona));
    }

    /** post: devuelve el texto en minúsculas y sin espacios en los extremos. */
    public static String normalizar(String texto) {
        return texto.trim().toLowerCase(Locale.ROOT);
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
