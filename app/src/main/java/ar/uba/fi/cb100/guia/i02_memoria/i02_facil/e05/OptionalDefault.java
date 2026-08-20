package ar.uba.fi.cb100.guia.i02_memoria.i02_facil.e05;

import java.util.Optional;

/**
 * e05 - Optional como alternativa explicita a null.
 *
 * Optional representa "puede haber o no un valor". orElse provee un valor
 * por defecto cuando el Optional esta vacio, evitando el manejo manual de null.
 */
public class OptionalDefault {

    /**
     * Devuelve el valor contenido, o "N/A" si el Optional esta vacio.
     *
     * @param o optional de String
     * @return o.orElse("N/A")
     */
    public static String orDefault(Optional<String> o) {
        return o.orElse("N/A");
    }

    public static void main(String[] args) {
        System.out.println("presente -> " + orDefault(Optional.of("dato")));
        System.out.println("vacio    -> " + orDefault(Optional.empty()));
    }
}
