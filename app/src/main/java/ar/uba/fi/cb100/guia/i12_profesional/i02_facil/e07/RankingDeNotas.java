package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e07;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * e07: estadísticas del curso con streams, sobre el mismo curso del apunte
 * (Ana 9, Beto 4, Carla 7, Dana 10, Eva 3, Fede 6).
 * <p>
 * Tres pipelines: {@code average} (reduce a un número), {@code sorted +
 * limit} (top 3) y {@code groupingBy + counting} (histograma nota →
 * cantidad, un HashMap de U7 armado por el collector).
 */
public final class RankingDeNotas {

    private RankingDeNotas() {
    }

    /** Un alumno con su nota final. */
    public record Alumno(String nombre, int nota) {}

    /** El curso del apunte de la unidad. */
    public static final List<Alumno> CURSO = List.of(
            new Alumno("Ana", 9), new Alumno("Beto", 4), new Alumno("Carla", 7),
            new Alumno("Dana", 10), new Alumno("Eva", 3), new Alumno("Fede", 6));

    /** Promedio de notas del curso (0 si el curso está vacío). */
    public static double promedio(List<Alumno> curso) {
        return curso.stream()
                .mapToInt(Alumno::nota)
                .average()          // devuelve OptionalDouble: puede no haber notas
                .orElse(0.0);
    }

    /** Los nombres de los tres mejores promedios, de mayor a menor nota. */
    public static List<String> mejoresTres(List<Alumno> curso) {
        return curso.stream()
                .sorted(Comparator.comparingInt(Alumno::nota).reversed())
                .limit(3)
                .map(Alumno::nombre)
                .toList();
    }

    /** Histograma nota → cantidad de alumnos con esa nota. */
    public static Map<Integer, Long> distribucion(List<Alumno> curso) {
        return curso.stream()
                .collect(Collectors.groupingBy(Alumno::nota, Collectors.counting()));
    }

    public static void main(String[] args) {
        System.out.println("Promedio:     " + promedio(CURSO));       // 6.5
        System.out.println("Mejores tres: " + mejoresTres(CURSO));    // [Dana, Ana, Carla]
        System.out.println("Distribución: " + distribucion(CURSO));
    }
}
