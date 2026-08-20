package ar.uba.fi.cb100.material.i12_profesional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <b>Streams</b>: el mismo problema resuelto en imperativo (CÓMO, paso a
 * paso) y en declarativo (QUÉ quiero, como un pipeline).
 * <p>
 * Un stream tiene tres partes: una FUENTE ({@code .stream()}), cero o más
 * operaciones INTERMEDIAS (filter, map, sorted… que devuelven otro stream y
 * no ejecutan nada todavía) y UNA operación TERMINAL (collect, count,
 * findFirst… que dispara todo el pipeline).
 */
public final class DemoStreams {

    private DemoStreams() {}

    public record Alumno(String nombre, int nota) {}

    static final List<Alumno> CURSO = List.of(
            new Alumno("Ana", 9), new Alumno("Beto", 4), new Alumno("Carla", 7),
            new Alumno("Dana", 10), new Alumno("Eva", 3), new Alumno("Fede", 6));

    /** Versión imperativa: describís CÓMO, con estado mutable intermedio. */
    public static List<String> aprobadosImperativo() {
        List<Alumno> aprobados = new ArrayList<>();
        for (Alumno alumno : CURSO) {
            if (alumno.nota() >= 6) {                          // 1) filtrar
                aprobados.add(alumno);
            }
        }
        aprobados.sort(Comparator.comparingInt(Alumno::nota).reversed());  // 2) ordenar
        List<String> nombres = new ArrayList<>();
        for (Alumno alumno : aprobados) {                      // 3) transformar
            nombres.add(alumno.nombre());
        }
        return nombres;
    }

    /** Versión declarativa: describís QUÉ, como una tubería de pasos. */
    public static List<String> aprobadosDeclarativo() {
        return CURSO.stream()                                   // fuente
                .filter(alumno -> alumno.nota() >= 6)           // intermedia: filtrar
                .sorted(Comparator.comparingInt(Alumno::nota)   // intermedia: ordenar
                        .reversed())
                .map(Alumno::nombre)                            // intermedia: transformar
                .toList();                                      // terminal: recolectar
    }

    public static void main(String[] args) {
        System.out.println(aprobadosImperativo());   // [Dana, Ana, Carla, Fede]
        System.out.println(aprobadosDeclarativo());  // [Dana, Ana, Carla, Fede]  (idéntico)

        // reduce: comprimir el stream a UN valor
        int sumaDeNotas = CURSO.stream()
                .mapToInt(Alumno::nota)
                .sum();                                          // 9+4+7+10+3+6
        System.out.println(sumaDeNotas);             // 39

        double promedio = CURSO.stream()
                .mapToInt(Alumno::nota)
                .average()
                .orElse(0.0);
        System.out.println(promedio);                // 6.5

        // Optional: un resultado que puede no existir (¡adiós null!)
        Optional<Alumno> mejor = CURSO.stream()
                .max(Comparator.comparingInt(Alumno::nota));
        System.out.println(mejor.map(Alumno::nombre).orElse("(curso vacío)"));  // Dana

        // collect con joining: armar un texto
        String lista = CURSO.stream()
                .filter(alumno -> alumno.nota() >= 6)
                .map(Alumno::nombre)
                .collect(Collectors.joining(", "));
        System.out.println(lista);                   // Ana, Carla, Dana, Fede

        // contar
        long desaprobados = CURSO.stream()
                .filter(alumno -> alumno.nota() < 6)
                .count();
        System.out.println(desaprobados);            // 2
    }
}
