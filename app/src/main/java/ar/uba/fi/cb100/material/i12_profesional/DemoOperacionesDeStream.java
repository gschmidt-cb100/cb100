package ar.uba.fi.cb100.material.i12_profesional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Catálogo ejecutable de las operaciones de {@code Stream}: una línea por
 * operación INTERMEDIA y por operación TERMINAL, sobre los mismos datos, con
 * el resultado impreso al lado. Es el programa detrás de la sección
 * "Catálogo de operaciones" de la Unidad 12: lo que dice el apunte es lo que
 * imprime este main.
 */
public final class DemoOperacionesDeStream {

    private DemoOperacionesDeStream() {}

    public record Alumno(String nombre, int nota, List<String> materias) {}

    static final List<Alumno> CURSO = List.of(
            new Alumno("Ana", 9, List.of("Algebra", "Fisica")),
            new Alumno("Beto", 4, List.of("Fisica")),
            new Alumno("Carla", 7, List.of("Algebra", "Quimica")),
            new Alumno("Dana", 10, List.of("Quimica")),
            new Alumno("Eva", 3, List.of()),
            new Alumno("Fede", 6, List.of("Algebra")));

    static final List<Integer> NUMEROS = List.of(5, 3, 8, 3, 1, 8, 9);

    public static void main(String[] args) {
        intermedias();
        terminales();
    }

    static void intermedias() {
        System.out.println("=== INTERMEDIAS ===");
        mostrar("filter", CURSO.stream().filter(a -> a.nota() >= 6).map(Alumno::nombre).toList());
        mostrar("map", CURSO.stream().map(Alumno::nombre).toList());
        mostrar("map (otro tipo)", CURSO.stream().map(a -> a.nombre().length()).toList());
        mostrar("mapToInt + sum", CURSO.stream().mapToInt(Alumno::nota).sum());
        mostrar("mapToDouble + average", CURSO.stream().mapToDouble(Alumno::nota).average().orElse(0));
        mostrar("mapToLong + max", CURSO.stream().mapToLong(Alumno::nota).max().orElse(0));
        mostrar("flatMap", CURSO.stream().flatMap(a -> a.materias().stream()).toList());
        mostrar("flatMap + distinct", CURSO.stream().flatMap(a -> a.materias().stream()).distinct().toList());
        mostrar("flatMapToInt", Stream.of("hola", "che").flatMapToInt(String::chars).count());
        mostrar("mapMulti", NUMEROS.stream().<String>mapMulti((n, salida) -> {
            if (n % 2 == 0) {                 // por cada par emite dos textos; por los impares, nada
                salida.accept("par " + n);
                salida.accept("mitad " + n / 2);
            }
        }).toList());
        mostrar("distinct", NUMEROS.stream().distinct().toList());
        mostrar("sorted()", NUMEROS.stream().sorted().toList());
        mostrar("sorted(comparador)", CURSO.stream()
                .sorted(Comparator.comparingInt(Alumno::nota).reversed()).map(Alumno::nombre).toList());
        List<String> vistos = new ArrayList<>();
        List<Integer> dobles = NUMEROS.stream().peek(n -> vistos.add("vi " + n)).map(n -> n * 2).limit(2).toList();
        mostrar("peek (resultado)", dobles);
        mostrar("peek (lo que espió)", vistos);
        mostrar("limit", NUMEROS.stream().limit(3).toList());
        mostrar("skip", NUMEROS.stream().skip(3).toList());
        mostrar("skip + limit (página 2 de a 2)", NUMEROS.stream().skip(2).limit(2).toList());
        mostrar("takeWhile", NUMEROS.stream().takeWhile(n -> n != 1).toList());
        mostrar("dropWhile", NUMEROS.stream().dropWhile(n -> n != 1).toList());
        mostrar("gather(windowFixed(3))", NUMEROS.stream().gather(Gatherers.windowFixed(3)).toList());
    }

    static void terminales() {
        System.out.println("=== TERMINALES ===");
        StringBuilder texto = new StringBuilder();
        CURSO.stream().filter(a -> a.nota() >= 9).forEach(a -> texto.append(a.nombre()).append(' '));
        mostrar("forEach", texto.toString().trim());
        StringBuilder enOrden = new StringBuilder();
        NUMEROS.parallelStream().forEachOrdered(enOrden::append);
        mostrar("forEachOrdered (paralelo)", enOrden);
        mostrar("toList", CURSO.stream().map(Alumno::nota).toList());
        mostrar("toArray()", Arrays.toString(NUMEROS.stream().distinct().toArray()));
        String[] nombres = CURSO.stream().map(Alumno::nombre).toArray(String[]::new);
        mostrar("toArray(String[]::new)", Arrays.toString(nombres));
        mostrar("collect(toSet)", CURSO.stream().flatMap(a -> a.materias().stream())
                .collect(Collectors.toCollection(java.util.TreeSet::new)));
        mostrar("collect(joining)", CURSO.stream().map(Alumno::nombre).collect(Collectors.joining(", ", "[", "]")));
        Map<Boolean, List<String>> aprobados = CURSO.stream().collect(
                Collectors.partitioningBy(a -> a.nota() >= 6, Collectors.mapping(Alumno::nombre, Collectors.toList())));
        mostrar("collect(partitioningBy)", aprobados);
        Map<Integer, Long> porCantidad = CURSO.stream().collect(
                Collectors.groupingBy(a -> a.materias().size(), java.util.TreeMap::new, Collectors.counting()));
        mostrar("collect(groupingBy, counting)", porCantidad);
        Map<String, Integer> notaPorNombre = CURSO.stream().collect(
                Collectors.toMap(Alumno::nombre, Alumno::nota, (a, b) -> a, java.util.TreeMap::new));
        mostrar("collect(toMap)", notaPorNombre);
        ArrayList<String> aMano = CURSO.stream().map(Alumno::nombre)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        mostrar("collect(proveedor, acumulador, combinador)", aMano);
        mostrar("reduce(identidad, op)", NUMEROS.stream().reduce(0, Integer::sum));
        Optional<Integer> mayor = NUMEROS.stream().reduce(Integer::max);
        mostrar("reduce(op)", mayor);
        mostrar("reduce(op) sobre vacío", Stream.<Integer>empty().reduce(Integer::max));
        mostrar("reduce(identidad, acum, comb)", CURSO.stream().reduce(0, (suma, a) -> suma + a.nota(), Integer::sum));
        mostrar("count", CURSO.stream().filter(a -> a.nota() >= 6).count());
        mostrar("min", CURSO.stream().min(Comparator.comparingInt(Alumno::nota)).map(Alumno::nombre));
        mostrar("max", CURSO.stream().max(Comparator.comparingInt(Alumno::nota)).map(Alumno::nombre));
        mostrar("findFirst", CURSO.stream().filter(a -> a.nota() < 6).findFirst().map(Alumno::nombre));
        mostrar("findFirst sin resultado", CURSO.stream().filter(a -> a.nota() > 10).findFirst().map(Alumno::nombre));
        mostrar("findAny", CURSO.stream().filter(a -> a.nota() == 10).findAny().map(Alumno::nombre));
        mostrar("anyMatch", CURSO.stream().anyMatch(a -> a.nota() == 10));
        mostrar("allMatch", CURSO.stream().allMatch(a -> a.nota() >= 6));
        mostrar("noneMatch", CURSO.stream().noneMatch(a -> a.nota() > 10));
        mostrar("allMatch sobre vacío", Stream.<Alumno>empty().allMatch(a -> a.nota() >= 6));
    }

    private static void mostrar(String operacion, Object resultado) {
        System.out.printf("%-42s %s%n", operacion, resultado);
    }
}
