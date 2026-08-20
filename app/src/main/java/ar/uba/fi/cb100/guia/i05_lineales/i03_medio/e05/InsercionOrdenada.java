package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e05;

import java.util.ArrayList;
import java.util.List;

/**
 * Inserción de un elemento en una lista ordenada manteniendo el orden.
 * Es el paso central del algoritmo Insertion Sort.
 */
public final class InsercionOrdenada {

    private InsercionOrdenada() {
        // Clase de utilidad: no se instancia.
    }

    /**
     * Inserta {@code x} en la posición correcta de una lista ordenada
     * ascendentemente, devolviendo una nueva lista (no modifica la original).
     *
     * @param ordenada lista ordenada ascendentemente (no nula)
     * @param x        valor a insertar
     * @return nueva lista ordenada con {@code x} incluido
     */
    public static List<Integer> insertarOrdenado(List<Integer> ordenada, int x) {
        List<Integer> resultado = new ArrayList<>(ordenada.size() + 1);
        boolean insertado = false;

        for (int actual : ordenada) {
            // El primer elemento mayor o igual marca dónde va x.
            if (!insertado && x <= actual) {
                resultado.add(x);
                insertado = true;
            }
            resultado.add(actual);
        }
        // Si x es el mayor de todos, se agrega al final.
        if (!insertado) {
            resultado.add(x);
        }
        return resultado;
    }
}
