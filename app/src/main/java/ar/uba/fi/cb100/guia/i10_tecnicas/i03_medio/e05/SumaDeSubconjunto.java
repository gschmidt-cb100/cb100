package ar.uba.fi.cb100.guia.i10_tecnicas.i03_medio.e05;

import java.util.Arrays;

/**
 * e05: ¿existe un subconjunto que sume exactamente el objetivo?
 *
 * <p><b>Técnica: programación dinámica</b> booleana con tabla de una sola
 * dimensión (versión comprimida de la tabla 2D de la mochila).</p>
 *
 * <p><b>Tabla:</b> {@code alcanzable[s]} = true si algún subconjunto de los
 * elementos ya procesados suma exactamente {@code s}. Caso base
 * {@code alcanzable[0] = true} (el subconjunto vacío suma 0). Transición:
 * al procesar el elemento {@code x}, {@code alcanzable[s] |= alcanzable[s - x]}.
 * <b>Clave:</b> {@code s} se recorre de mayor a menor; si fuera de menor a
 * mayor, el mismo elemento se usaría más de una vez en la misma pasada
 * (sería el problema de la moneda con repetición, no el de subconjuntos).</p>
 *
 * <p>Costo: O(n · objetivo) en tiempo, O(objetivo) en memoria.</p>
 */
public final class SumaDeSubconjunto {

    private SumaDeSubconjunto() {
    }

    /**
     * Indica si algún subconjunto de {@code a} suma exactamente el objetivo.
     *
     * @param a        elementos disponibles (todos &gt;= 0), cada uno usable a lo sumo una vez
     * @param objetivo suma buscada, debe ser &gt;= 0
     * @return true si existe tal subconjunto (para objetivo 0 siempre: el vacío)
     */
    public static boolean existe(int[] a, int objetivo) {
        if (objetivo < 0) {
            throw new IllegalArgumentException("objetivo negativo: " + objetivo);
        }
        boolean[] alcanzable = new boolean[objetivo + 1];
        alcanzable[0] = true;                       // el subconjunto vacío suma 0
        for (int x : a) {
            // De mayor a menor: cada elemento aporta a lo sumo una vez.
            for (int s = objetivo; s >= x; s--) {
                if (alcanzable[s - x]) {
                    alcanzable[s] = true;
                }
            }
        }
        return alcanzable[objetivo];
    }

    public static void main(String[] args) {
        int[] a = {3, 34, 4, 12, 5, 2};
        System.out.println("Conjunto: " + Arrays.toString(a));
        System.out.println("¿Suma 9?  " + existe(a, 9) + " (4 + 5)");
        System.out.println("¿Suma 30? " + existe(a, 30));
    }
}
