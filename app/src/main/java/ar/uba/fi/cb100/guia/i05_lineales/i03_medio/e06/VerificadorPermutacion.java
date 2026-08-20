package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Verifica si dos listas son permutación una de la otra, es decir,
 * si contienen exactamente los mismos elementos con las mismas
 * multiplicidades, sin importar el orden.
 */
public final class VerificadorPermutacion {

    private VerificadorPermutacion() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Indica si {@code b} es una permutación de {@code a}.
     * Estrategia: ordenar copias de ambas listas y compararlas.
     * No modifica las listas originales.
     *
     * @param a primera lista (no nula)
     * @param b segunda lista (no nula)
     * @return true si tienen los mismos elementos con igual frecuencia
     */
    public static boolean esPermutacion(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) {
            return false;
        }
        List<Integer> copiaA = new ArrayList<>(a);
        List<Integer> copiaB = new ArrayList<>(b);
        Collections.sort(copiaA);
        Collections.sort(copiaB);
        return copiaA.equals(copiaB);
    }
}
