package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e04;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtrado de múltiplos de 3 usando {@link List#removeIf} sobre una copia,
 * de modo de no modificar la lista original recibida.
 */
public final class FiltroMultiplos {

    private FiltroMultiplos() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Devuelve una nueva lista sin los múltiplos de 3 de la lista original.
     * La lista de entrada no se modifica.
     *
     * @param l lista de enteros (no nula)
     * @return copia sin los múltiplos de 3
     */
    public static List<Integer> sinMultiplosDe3(List<Integer> l) {
        List<Integer> copia = new ArrayList<>(l);
        // removeIf recorre y elimina los que cumplen el predicado.
        copia.removeIf(x -> x % 3 == 0);
        return copia;
    }
}
