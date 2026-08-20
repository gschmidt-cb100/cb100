package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e07;

import java.util.HashSet;
import java.util.Set;

/**
 * Diferencia de conjuntos A − B: elementos que están en A pero no en B.
 */
public final class DiferenciaConjuntos {

    private DiferenciaConjuntos() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Calcula A − B sobre una copia de {@code a}, sin modificar los originales.
     *
     * @param a conjunto minuendo (no nulo)
     * @param b conjunto sustraendo (no nulo)
     * @return nuevo conjunto con los elementos de A que no están en B
     */
    public static Set<Integer> diferencia(Set<Integer> a, Set<Integer> b) {
        Set<Integer> resultado = new HashSet<>(a);
        // removeAll elimina de la copia todo lo que aparezca en b.
        resultado.removeAll(b);
        return resultado;
    }
}
