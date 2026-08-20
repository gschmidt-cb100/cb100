package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e09;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * e09: Optional sin sustos. Buscar un alumno por nombre puede NO encontrar
 * nada, y en vez de devolver {@code null} (y arriesgar un
 * {@code NullPointerException} lejos del lugar del problema) devolvemos
 * {@link Optional}: el tipo le avisa a quien llama que la ausencia es un
 * resultado posible y lo obliga a decidir qué hacer — un valor por defecto
 * ({@code orElse}), una excepción explícita ({@code orElseThrow}), etc.
 */
public class BuscadorDeAlumnos {

    /** Un alumno con su nota final. */
    public record Alumno(String nombre, int nota) {}

    private final List<Alumno> curso;

    public BuscadorDeAlumnos(List<Alumno> curso) {
        this.curso = List.copyOf(curso);
    }

    /**
     * Busca un alumno por nombre exacto.
     * {@code findFirst} sobre el stream filtrado ya devuelve Optional:
     * lleno si alguien pasó el filtro, vacío si nadie.
     *
     * @param nombre nombre a buscar
     * @return el alumno, o {@code Optional.empty()} si no está en el curso
     */
    public Optional<Alumno> buscarPorNombre(String nombre) {
        return curso.stream()
                .filter(alumno -> alumno.nombre().equals(nombre))
                .findFirst();
    }

    public static void main(String[] args) {
        BuscadorDeAlumnos buscador = new BuscadorDeAlumnos(List.of(
                new Alumno("Ana", 9), new Alumno("Beto", 4), new Alumno("Carla", 7)));

        // Camino 1: valor por defecto si no está.
        Alumno anon = buscador.buscarPorNombre("Zoe")
                .orElse(new Alumno("(sin datos)", 0));
        System.out.println("Con orElse: " + anon);

        // Camino 2: excepción explícita si no está.
        try {
            buscador.buscarPorNombre("Zoe")
                    .orElseThrow(() -> new NoSuchElementException("Zoe no cursa CB100"));
        } catch (NoSuchElementException e) {
            System.out.println("Con orElseThrow: " + e.getMessage());
        }
    }
}
