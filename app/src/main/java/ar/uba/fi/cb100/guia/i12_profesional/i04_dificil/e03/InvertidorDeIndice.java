package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e03;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * TECNICA: {@code flatMap} PARA APLANAR + {@code groupingBy} PARA REAGRUPAR.
 *
 * Invertir un indice (materia -&gt; alumnos pasa a alumno -&gt; materias) es
 * el patron clasico de flatMap: cada entrada del mapa original "explota" en
 * varios pares (alumno, materia) — un stream DENTRO de otro stream que
 * flatMap aplana en uno solo — y despues {@code groupingBy} los reagrupa
 * por la otra punta del par. Es el mismo groupingBy del apunte (el HashMap
 * de la U7 armado solo), solo que aca la clave nueva salia del VALOR viejo.
 *
 * Determinismo: usamos {@code TreeMap} (el ABB autobalanceado de la U8)
 * como fabrica del mapa y ordenamos cada lista de materias, asi el
 * resultado es siempre el mismo sin importar el orden de entrada.
 */
public final class InvertidorDeIndice {

    /** Un par (alumno, materia): cada entrada original genera varios. */
    private record Cursada(String alumno, String materia) {}

    /** Invierte materia -&gt; alumnos en alumno -&gt; materias (todo ordenado). */
    public static Map<String, List<String>> invertir(Map<String, List<String>> materiaAAlumnos) {
        return materiaAAlumnos.entrySet().stream()
                .flatMap(entrada -> entrada.getValue().stream()
                        .map(alumno -> new Cursada(alumno, entrada.getKey())))
                .collect(Collectors.groupingBy(
                        Cursada::alumno,
                        TreeMap::new,
                        Collectors.mapping(Cursada::materia,
                                Collectors.collectingAndThen(Collectors.toList(),
                                        materias -> materias.stream().sorted().toList()))));
    }

    private InvertidorDeIndice() {
    }

    public static void main(String[] args) {
        Map<String, List<String>> materias = Map.of(
                "Algoritmos", List.of("Ana", "Beto", "Carla"),
                "Analisis", List.of("Ana", "Carla"),
                "Algebra", List.of("Beto"));
        System.out.println(invertir(materias));
        // {Ana=[Algoritmos, Analisis], Beto=[Algebra, Algoritmos], Carla=[Algoritmos, Analisis]}
    }
}
