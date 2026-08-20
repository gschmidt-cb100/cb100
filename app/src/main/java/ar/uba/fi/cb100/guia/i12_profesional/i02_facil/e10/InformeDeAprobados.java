package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e10;

import java.util.List;
import java.util.stream.Collectors;

/**
 * e10: armar un texto legible con {@code Collectors.joining}.
 * <p>
 * Un solo pipeline: filter (aprobados, nota ≥ 6) → map (a "Nombre (nota)")
 * → joining con separador ", " y prefijo "Aprobados: ". El collector se
 * ocupa del detalle molesto de los separadores: no hay coma colgando al
 * final ni casos especiales para el primero — el error clásico de la
 * versión imperativa con {@code StringBuilder}.
 */
public final class InformeDeAprobados {

    private InformeDeAprobados() {
    }

    /** Un alumno con su nota final. */
    public record Alumno(String nombre, int nota) {}

    /**
     * Texto con los aprobados (nota ≥ 6) en el orden de la lista, con el
     * formato exacto {@code "Aprobados: Ana (9), Carla (7), Dana (10)"}.
     *
     * @param curso lista de alumnos
     * @return el informe; si nadie aprobó queda {@code "Aprobados: "}
     */
    public static String textoDeAprobados(List<Alumno> curso) {
        return curso.stream()
                .filter(alumno -> alumno.nota() >= 6)
                .map(alumno -> alumno.nombre() + " (" + alumno.nota() + ")")
                .collect(Collectors.joining(", ", "Aprobados: ", ""));
    }

    public static void main(String[] args) {
        List<Alumno> curso = List.of(
                new Alumno("Ana", 9), new Alumno("Beto", 4), new Alumno("Carla", 7),
                new Alumno("Dana", 10), new Alumno("Eva", 3), new Alumno("Fede", 6));
        System.out.println(textoDeAprobados(curso));
        // Aprobados: Ana (9), Carla (7), Dana (10), Fede (6)
    }
}
