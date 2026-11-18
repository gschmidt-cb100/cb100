package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Filtros <b>sin índice</b>: se recorren todas las personas con un stream y
 * se dejan las que cumplen. O(n) siempre. Es lo que se usa para los campos
 * que no tienen índice (provincia y país), y sirve de vara para ver lo que
 * los índices ahorran.
 */
public final class FiltroPorFuerzaBruta {

    /** Los campos que se filtran recorriendo todo. */
    public enum Campo {
        PROVINCIA("Provincia", Persona::provincia),
        PAIS("País", Persona::pais);

        private final String titulo;
        private final Function<Persona, String> extractor;

        Campo(String titulo, Function<Persona, String> extractor) {
            this.titulo = titulo;
            this.extractor = extractor;
        }

        String valorDe(Persona persona) {
            return extractor.apply(persona);
        }

        @Override
        public String toString() {
            return titulo;
        }
    }

    private FiltroPorFuerzaBruta() {
    }

    /**
     * pre: ningún argumento es null.
     * post: devuelve las personas cuyo valor en {@code campo} contiene el texto
     *       (sin distinguir mayúsculas), ordenadas por apellido y nombre, y el tiempo que llevó.
     */
    public static ResultadoDeBusqueda filtrar(List<Persona> personas, Campo campo, String texto) {
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        ValidacionesUtiles.validarDistintoDeNull(texto, "texto");
        String buscado = CampoDeBusqueda.normalizar(texto);
        long inicio = System.nanoTime();
        List<Persona> encontradas = personas.stream()
                .filter(persona -> CampoDeBusqueda.normalizar(campo.valorDe(persona)).contains(buscado))
                .sorted(Comparator.comparing(Persona::apellido).thenComparing(Persona::nombre))
                .toList();
        long nanos = System.nanoTime() - inicio;
        return new ResultadoDeBusqueda("fuerza bruta sobre " + campo, encontradas, nanos);
    }

    /** post: devuelve cuántas personas hay por cada valor del campo, ordenado alfabéticamente. */
    public static Map<String, Long> contarPor(List<Persona> personas, Campo campo) {
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        return personas.stream()
                .collect(Collectors.groupingBy(campo::valorDe, TreeMap::new, Collectors.counting()));
    }
}
