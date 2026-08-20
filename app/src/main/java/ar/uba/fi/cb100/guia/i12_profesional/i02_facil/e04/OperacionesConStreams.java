package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e04;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * e04: primeros streams sobre una {@code List<Integer>}.
 * <p>
 * Tres pipelines cortos para fijar el patrón fuente → intermedias →
 * terminal. Ojo con {@link Optional}: {@code max} sobre un stream vacío no
 * devuelve {@code null} ni explota, devuelve {@code Optional.empty()} — el
 * tipo obliga a quien llama a decidir qué hacer con la ausencia.
 */
public final class OperacionesConStreams {

    private OperacionesConStreams() {
    }

    /** Suma de los números pares de la lista (0 si no hay ninguno). */
    public static int sumaDePares(List<Integer> numeros) {
        return numeros.stream()
                .filter(n -> n % 2 == 0)     // intermedia: me quedo con los pares
                .mapToInt(Integer::intValue) // paso a IntStream para sumar sin boxing
                .sum();                      // terminal
    }

    /** Los impares elevados al cuadrado, de mayor a menor. */
    public static List<Integer> imparesAlCuadradoDesc(List<Integer> numeros) {
        return numeros.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * n)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    /** El máximo de la lista, o {@code Optional.empty()} si está vacía. */
    public static Optional<Integer> maximo(List<Integer> numeros) {
        return numeros.stream().max(Comparator.naturalOrder());
    }

    public static void main(String[] args) {
        List<Integer> datos = List.of(3, 8, 5, 2, 7, 4);
        System.out.println("Suma de pares:        " + sumaDePares(datos));          // 14
        System.out.println("Impares^2 desc:       " + imparesAlCuadradoDesc(datos)); // [49, 25, 9]
        System.out.println("Máximo:               " + maximo(datos));                // Optional[8]
        System.out.println("Máximo de vacía:      " + maximo(List.of()));            // Optional.empty
    }
}
