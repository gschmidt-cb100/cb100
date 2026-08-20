package ar.uba.fi.cb100.guia.i09_heaps.i02_facil.e07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * e07: las tres palabras más largas de una lista, usando un heap con un
 * comparador compuesto: longitud descendente y, a igual longitud,
 * orden alfabético ascendente. Buen ejemplo de cómo se encadenan
 * comparadores con {@code reversed()} y {@code thenComparing(...)}.
 */
public final class PalabrasLargas {

    private PalabrasLargas() {
    }

    /** Longitud descendente; empate: alfabético ascendente. */
    private static final Comparator<String> MAS_LARGA_PRIMERO =
            Comparator.comparingInt(String::length).reversed()
                    .thenComparing(Comparator.naturalOrder());

    /**
     * Devuelve las tres palabras más largas de {@code palabras}, de la más
     * larga a la más corta; a igual longitud, en orden alfabético.
     * Si hay menos de tres palabras, devuelve todas.
     *
     * @param palabras lista de palabras (no se modifica)
     * @return hasta tres palabras, según el criterio descripto
     */
    public static List<String> tresMasLargas(List<String> palabras) {
        PriorityQueue<String> heap = new PriorityQueue<>(MAS_LARGA_PRIMERO);
        heap.addAll(palabras);
        List<String> resultado = new ArrayList<>();
        while (!heap.isEmpty() && resultado.size() < 3) {
            resultado.add(heap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<String> palabras = List.of("sol", "computadora", "mate", "algoritmo", "rio", "heladera");
        System.out.println("Palabras: " + palabras);
        System.out.println("Las 3 mas largas: " + tresMasLargas(palabras));
    }
}
