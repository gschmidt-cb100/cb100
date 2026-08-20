package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reubicación del elemento máximo de una lista al final,
 * conservando el orden relativo del resto de los elementos.
 */
public final class MaximoAlFinal {

    private MaximoAlFinal() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Devuelve una nueva lista con el valor máximo movido al final y el resto
     * de los elementos en su orden original. Si el máximo está repetido, se
     * mueve solo la primera aparición. No modifica la lista original.
     *
     * @param l lista de enteros no vacía (no nula)
     * @return nueva lista con el máximo al final
     * @throws IllegalArgumentException si la lista está vacía
     */
    public static List<Integer> maximoAlFinal(List<Integer> l) {
        if (l.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        int maximo = Collections.max(l);

        List<Integer> resultado = new ArrayList<>(l);
        // Se quita la primera aparición del máximo y se agrega al final.
        resultado.remove(Integer.valueOf(maximo));
        resultado.add(maximo);
        return resultado;
    }
}
