package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e08;

import java.util.Set;

/**
 * Verifica la relación de subconjunto entre dos conjuntos.
 */
public final class VerificadorSubconjunto {

    private VerificadorSubconjunto() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Indica si {@code a} es subconjunto de {@code b} (a ⊆ b), es decir,
     * si todos los elementos de {@code a} están también en {@code b}.
     * El conjunto vacío es subconjunto de cualquier conjunto.
     *
     * @param a conjunto candidato a subconjunto (no nulo)
     * @param b conjunto que debería contenerlo (no nulo)
     * @return true si a ⊆ b
     */
    public static boolean esSubconjunto(Set<Integer> a, Set<Integer> b) {
        // containsAll verifica que b contenga a todos los elementos de a.
        return b.containsAll(a);
    }
}
