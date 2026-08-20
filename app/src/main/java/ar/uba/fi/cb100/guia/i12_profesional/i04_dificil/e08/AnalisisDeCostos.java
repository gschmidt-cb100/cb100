package ar.uba.fi.cb100.guia.i12_profesional.i04_dificil.e08;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TECNICA: MODELO DE COSTOS PARA ELEGIR LA ESTRUCTURA (detector de la
 * "estructura equivocada").
 *
 * El bug de performance mas comun en la profesion no es un algoritmo malo:
 * es un {@code contains} sobre una {@code ArrayList} adentro de un bucle.
 * Este analizador pone numeros al instinto de la U4: dado un escenario
 * (cuantas busquedas, cuantas inserciones, que tamanio promedio), estima
 * las "operaciones elementales" de cada candidata con su complejidad:
 * <ul>
 *   <li><b>ArrayList</b> (U5): busqueda lineal ~ tamanio/2 comparaciones
 *       (una busqueda fallida recorre todo, una exitosa la mitad en
 *       promedio); insertar al final ~ 1 (amortizado, U5);</li>
 *   <li><b>HashSet</b> (U7): busqueda e insercion ~ 1 (esperado);</li>
 *   <li><b>TreeSet</b> (U8): ambas ~ log2(tamanio) por el descenso en el
 *       arbol autobalanceado.</li>
 * </ul>
 *
 * Es un MODELO, no un benchmark: ignora constantes, caches y colisiones.
 * Pero para elegir estructura alcanza con el ORDEN DE MAGNITUD, y el
 * modelo reproduce el del apunte: 10.000 busquedas fallidas sobre 100.000
 * elementos ~ 10^9 operaciones con lista contra ~ 10^4 con hash — la misma
 * brecha que los 3236 ms vs 10 ms medidos en clase.
 */
public final class AnalisisDeCostos {

    /** El perfil de uso: cuantas operaciones y sobre cuantos elementos. */
    public record Escenario(long busquedas, long inserciones, long tamanoPromedio) {
        public Escenario {
            if (busquedas < 0 || inserciones < 0 || tamanoPromedio < 1) {
                throw new IllegalArgumentException("escenario invalido: " + this);
            }
        }
    }

    public static final String ARRAY_LIST = "ArrayList";
    public static final String HASH_SET = "HashSet";
    public static final String TREE_SET = "TreeSet";

    /** Operaciones elementales estimadas por estructura (orden fijo de claves). */
    public static Map<String, Long> estimaciones(Escenario escenario) {
        long log2 = log2(escenario.tamanoPromedio());
        Map<String, Long> costos = new LinkedHashMap<>();
        costos.put(ARRAY_LIST,
                escenario.busquedas() * (escenario.tamanoPromedio() / 2)
                        + escenario.inserciones());
        costos.put(HASH_SET, escenario.busquedas() + escenario.inserciones());
        costos.put(TREE_SET, (escenario.busquedas() + escenario.inserciones()) * log2);
        return costos;
    }

    /** La estructura de menor costo estimado (primera en caso de empate). */
    public static String optima(Escenario escenario) {
        return estimaciones(escenario).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    /** log2 entero (piso): log2(1)=0, log2(100000)=16. */
    private static long log2(long n) {
        return 63 - Long.numberOfLeadingZeros(n);
    }

    private AnalisisDeCostos() {
    }

    public static void main(String[] args) {
        // El escenario del apunte: 10.000 busquedas fallidas x 100.000 elementos.
        Escenario delApunte = new Escenario(10_000, 0, 100_000);
        System.out.println("Escenario del apunte " + delApunte + ":");
        estimaciones(delApunte).forEach((estructura, costo) ->
                System.out.printf("  %-10s ~ %,d operaciones%n", estructura, costo));
        System.out.println("  Optima: " + optima(delApunte));
        // ArrayList ~ 5x10^8 (orden 10^9) vs HashSet 10^4: coherente con
        // los 3236 ms vs 10 ms medidos en clase.
    }
}
