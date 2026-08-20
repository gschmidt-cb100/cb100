package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * e05: de imperativo a declarativo. El mismo problema dos veces:
 * quedarse con los mails que contienen "@", pasarlos a minúsculas,
 * eliminar duplicados y devolverlos ordenados.
 * <p>
 * La versión imperativa necesita estado mutable intermedio (una lista, un
 * conjunto para los repetidos, un sort aparte); la declarativa cuenta la
 * misma historia como pipeline: filter → map → distinct → sorted. Mismo
 * costo (el que domina es el orden, O(n log n)), pero la segunda se lee
 * como la especificación del problema.
 */
public final class FiltroDeMails {

    private FiltroDeMails() {
    }

    /** Versión imperativa: CÓMO, paso a paso, con colecciones auxiliares. */
    public static List<String> mailsValidosImperativo(List<String> mails) {
        // LinkedHashSet: elimina duplicados conservando el orden de llegada,
        // aunque acá el orden final lo impone el sort de abajo.
        Set<String> sinRepetidos = new LinkedHashSet<>();
        for (String mail : mails) {
            if (mail.contains("@")) {              // 1) filtrar
                sinRepetidos.add(mail.toLowerCase()); // 2) transformar  3) dedup
            }
        }
        List<String> resultado = new ArrayList<>(sinRepetidos);
        Collections.sort(resultado);               // 4) ordenar
        return resultado;
    }

    /** Versión declarativa: QUÉ quiero, como un pipeline. */
    public static List<String> mailsValidosDeclarativo(List<String> mails) {
        return mails.stream()
                .filter(mail -> mail.contains("@"))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .toList();
    }

    public static void main(String[] args) {
        List<String> crudos = List.of(
                "Ana@fi.uba.ar", "sin-arroba", "beto@GMAIL.com",
                "ana@fi.uba.ar", "Carla@fi.uba.ar");
        System.out.println(mailsValidosImperativo(crudos));
        System.out.println(mailsValidosDeclarativo(crudos));
        // Ambas: [ana@fi.uba.ar, beto@gmail.com, carla@fi.uba.ar]
    }
}
