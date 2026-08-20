package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e03;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * e03: contar cuántas veces aparece cada palabra, edición streams.
 * <p>
 * El clásico de U7 (mapa palabra → contador con {@code merge} o
 * {@code getOrDefault}) resuelto en UNA línea declarativa:
 * {@code groupingBy} agrupa por la palabra misma y {@code counting} cuenta
 * cada grupo. Por debajo {@code groupingBy} arma un {@code HashMap} (U7),
 * así que cada palabra se clasifica en O(1) promedio y el total es O(n).
 */
public final class ContadorDePalabras {

    private ContadorDePalabras() {
    }

    /**
     * Devuelve un mapa palabra → cantidad de apariciones.
     *
     * @param palabras lista de palabras (puede tener repetidas)
     * @return frecuencias de cada palabra
     */
    public static Map<String, Long> frecuencias(List<String> palabras) {
        return palabras.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),     // la clave del grupo es la palabra misma
                        Collectors.counting())); // y el valor, cuántas veces aparece
    }

    public static void main(String[] args) {
        List<String> letra = List.of("que", "sera", "sera", "lo", "que", "sera");
        System.out.println(frecuencias(letra));   // {que=2, sera=3, lo=1} (orden de hash)
    }
}
