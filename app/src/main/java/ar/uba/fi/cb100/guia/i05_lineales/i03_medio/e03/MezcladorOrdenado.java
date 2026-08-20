package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e03;

import java.util.ArrayList;
import java.util.List;

/**
 * Mezcla de dos listas ya ordenadas en una única lista ordenada.
 * Corresponde al paso "merge" del algoritmo Merge Sort.
 */
public final class MezcladorOrdenado {

    private MezcladorOrdenado() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Combina dos listas ordenadas ascendentemente en una nueva lista ordenada.
     * Recorre ambas con dos índices tomando siempre el menor disponible.
     * No modifica las listas de entrada.
     *
     * @param a lista ordenada (no nula)
     * @param b lista ordenada (no nula)
     * @return nueva lista con todos los elementos, en orden ascendente
     */
    public static List<Integer> mezclarOrdenadas(List<Integer> a, List<Integer> b) {
        List<Integer> resultado = new ArrayList<>(a.size() + b.size());
        int i = 0;
        int j = 0;

        while (i < a.size() && j < b.size()) {
            if (a.get(i) <= b.get(j)) {
                resultado.add(a.get(i));
                i++;
            } else {
                resultado.add(b.get(j));
                j++;
            }
        }
        // Se agrega el remanente de la lista que no se terminó de recorrer.
        while (i < a.size()) {
            resultado.add(a.get(i));
            i++;
        }
        while (j < b.size()) {
            resultado.add(b.get(j));
            j++;
        }
        return resultado;
    }
}
