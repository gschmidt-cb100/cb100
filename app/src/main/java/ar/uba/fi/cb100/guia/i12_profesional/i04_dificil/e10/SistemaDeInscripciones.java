package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e10;

import ar.uba.fi.cb100.material.i11_grafos.Grafo;
import ar.uba.fi.cb100.material.i11_grafos.OrdenTopologico;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * TP INTEGRADOR: el sistema de inscripciones de la facultad, con una
 * estructura del curso en cada engranaje — y cada una elegida POR ALGO:
 * <ul>
 *   <li><b>Grafo dirigido + orden topologico (U11)</b>: las correlativas
 *       son un grafo materia -&gt; materia; un plan con ciclo (A pide B y B
 *       pide A) seria imposible de cursar, asi que el constructor lo valida
 *       con {@code OrdenTopologico.tieneCiclo} y lo rechaza de entrada;</li>
 *   <li><b>HashMap (U7)</b>: buscar materias y alumnos por nombre en O(1)
 *       esperado — la operacion mas repetida del sistema;</li>
 *   <li><b>HashSet (U7)</b>: las materias aprobadas de cada alumno;
 *       "aprobo la correlativa?" es un contains O(1);</li>
 *   <li><b>ArrayList (U5)</b>: los inscriptos de cada materia en orden de
 *       llegada; agregar al final es O(1) amortizado;</li>
 *   <li><b>Cola FIFO con ArrayDeque (U5)</b>: la lista de espera; el
 *       primero que espero es el primero que entra cuando se libera un
 *       lugar (offer/poll en O(1));</li>
 *   <li><b>TreeMap (U8) + streams (U12)</b>: el reporte final, ordenado
 *       por materia y con los inscriptos rankeados por promedio.</li>
 * </ul>
 */
public class SistemaDeInscripciones {

    /** Una materia del plan: cupo fijo y correlativas por nombre. */
    public record Materia(String nombre, int cupo, List<String> correlativas) {
        public Materia {
            if (cupo < 1) {
                throw new IllegalArgumentException("el cupo debe ser positivo: " + cupo);
            }
            correlativas = List.copyOf(correlativas);
        }
    }

    /** Un alumno: nombre unico y promedio para el ranking del reporte. */
    public record Alumno(String nombre, double promedio) {}

    private final Map<String, Materia> materias = new HashMap<>();      // U7: busqueda O(1)
    private final Map<String, Alumno> alumnos = new HashMap<>();        // U7: busqueda O(1)
    private final Map<String, Set<String>> aprobadasPorAlumno = new HashMap<>();
    private final Map<String, List<String>> inscriptosPorMateria = new HashMap<>();
    private final Map<String, Queue<String>> esperaPorMateria = new HashMap<>();

    /**
     * Valida el plan al construir: correlativas existentes y SIN ciclos
     * (grafo dirigido correlativa -&gt; materia + tieneCiclo de la U11).
     *
     * @throws IllegalArgumentException si el plan tiene ciclos o
     *         correlativas inexistentes
     */
    public SistemaDeInscripciones(List<Materia> plan) {
        Map<String, Integer> indice = new HashMap<>();
        for (Materia materia : plan) {
            if (indice.putIfAbsent(materia.nombre(), indice.size()) != null) {
                throw new IllegalArgumentException("materia repetida: " + materia.nombre());
            }
        }
        Grafo grafoDeCorrelativas = new Grafo(plan.size(), true);
        for (Materia materia : plan) {
            for (String correlativa : materia.correlativas()) {
                Integer desde = indice.get(correlativa);
                if (desde == null) {
                    throw new IllegalArgumentException(
                            "correlativa inexistente: " + correlativa);
                }
                grafoDeCorrelativas.agregarArista(desde, indice.get(materia.nombre()));
            }
        }
        if (OrdenTopologico.tieneCiclo(grafoDeCorrelativas)) {
            throw new IllegalArgumentException("el plan tiene un ciclo de correlativas");
        }
        for (Materia materia : plan) {
            materias.put(materia.nombre(), materia);
            inscriptosPorMateria.put(materia.nombre(), new ArrayList<>());   // U5
            esperaPorMateria.put(materia.nombre(), new ArrayDeque<>());      // U5: FIFO
        }
    }

    public void registrarAlumno(String nombre, double promedio) {
        if (alumnos.putIfAbsent(nombre, new Alumno(nombre, promedio)) != null) {
            throw new IllegalArgumentException("alumno ya registrado: " + nombre);
        }
        aprobadasPorAlumno.put(nombre, new HashSet<>());                     // U7
    }

    /** Marca una materia como aprobada (habilita sus dependientes). */
    public void aprobar(String alumno, String materia) {
        validarExistencia(alumno, materia);
        aprobadasPorAlumno.get(alumno).add(materia);
    }

    /**
     * Inscribe respetando correlativas, cupo y orden de llegada.
     *
     * @return {@code true} si quedo inscripto; {@code false} si la materia
     *         estaba llena y paso a la lista de espera (FIFO)
     * @throws IllegalStateException si le falta alguna correlativa aprobada
     */
    public boolean inscribir(String alumno, String materia) {
        validarExistencia(alumno, materia);
        Set<String> aprobadas = aprobadasPorAlumno.get(alumno);
        for (String correlativa : materias.get(materia).correlativas()) {
            if (!aprobadas.contains(correlativa)) {                          // O(1), U7
                throw new IllegalStateException(
                        alumno + " no aprobo la correlativa " + correlativa);
            }
        }
        List<String> inscriptos = inscriptosPorMateria.get(materia);
        Queue<String> espera = esperaPorMateria.get(materia);
        if (inscriptos.contains(alumno) || espera.contains(alumno)) {
            throw new IllegalStateException(alumno + " ya esta anotado en " + materia);
        }
        if (inscriptos.size() < materias.get(materia).cupo()) {
            inscriptos.add(alumno);                                          // O(1) amort., U5
            return true;
        }
        espera.offer(alumno);                                                // al final de la cola
        return false;
    }

    /** Baja un alumno; si habia lista de espera, promueve al PRIMERO (FIFO). */
    public void bajar(String alumno, String materia) {
        validarExistencia(alumno, materia);
        List<String> inscriptos = inscriptosPorMateria.get(materia);
        Queue<String> espera = esperaPorMateria.get(materia);
        if (inscriptos.remove(alumno)) {
            String promovido = espera.poll();                                // O(1), U5
            if (promovido != null) {
                inscriptos.add(promovido);
            }
        } else if (!espera.remove(alumno)) {
            throw new IllegalStateException(alumno + " no estaba anotado en " + materia);
        }
    }

    public List<String> inscriptosDe(String materia) {
        return List.copyOf(inscriptosPorMateria.get(materia));
    }

    public List<String> esperaDe(String materia) {
        return List.copyOf(esperaPorMateria.get(materia));
    }

    /**
     * Reporte final con streams (U12): materia -&gt; inscriptos ordenados
     * por promedio descendente (empates por nombre), en un TreeMap (U8)
     * para que las materias salgan en orden alfabetico, determinista.
     */
    public Map<String, List<String>> reporte() {
        return inscriptosPorMateria.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entrada -> entrada.getValue().stream()
                                .map(alumnos::get)
                                .sorted(Comparator.comparingDouble(Alumno::promedio).reversed()
                                        .thenComparing(Alumno::nombre))
                                .map(Alumno::nombre)
                                .toList(),
                        (a, b) -> a,
                        TreeMap::new));
    }

    private void validarExistencia(String alumno, String materia) {
        if (!alumnos.containsKey(alumno)) {
            throw new IllegalArgumentException("alumno desconocido: " + alumno);
        }
        if (!materias.containsKey(materia)) {
            throw new IllegalArgumentException("materia desconocida: " + materia);
        }
    }

    public static void main(String[] args) {
        SistemaDeInscripciones sistema = new SistemaDeInscripciones(List.of(
                new Materia("Algoritmos 1", 30, List.of()),
                new Materia("Algoritmos 2", 2, List.of("Algoritmos 1")),
                new Materia("Sistemas Operativos", 25, List.of("Algoritmos 2"))));

        sistema.registrarAlumno("Ana", 9.0);
        sistema.registrarAlumno("Beto", 7.5);
        sistema.registrarAlumno("Carla", 8.2);
        for (String alumno : List.of("Ana", "Beto", "Carla")) {
            sistema.aprobar(alumno, "Algoritmos 1");
        }

        System.out.println("Ana entra:   " + sistema.inscribir("Ana", "Algoritmos 2"));
        System.out.println("Beto entra:  " + sistema.inscribir("Beto", "Algoritmos 2"));
        System.out.println("Carla entra: " + sistema.inscribir("Carla", "Algoritmos 2")
                + "  (cupo lleno: a la espera)");
        sistema.bajar("Ana", "Algoritmos 2");
        System.out.println("Baja de Ana -> inscriptos: "
                + sistema.inscriptosDe("Algoritmos 2"));
        System.out.println("Reporte: " + sistema.reporte());
    }
}
