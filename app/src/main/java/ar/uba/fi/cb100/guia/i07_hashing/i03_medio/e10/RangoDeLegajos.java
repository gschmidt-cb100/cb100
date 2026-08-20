package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e10;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * e10: consultar un rango de claves en un diccionario ordenado.
 * Acá HashMap no sirve: necesitamos un {@link TreeMap}, que mantiene las
 * claves ordenadas y ofrece vistas por rango. Usamos la sobrecarga
 * {@code subMap(desde, true, hasta, true)} para que ambos extremos sean
 * inclusivos (la versión de dos argumentos excluye el extremo derecho).
 */
public final class RangoDeLegajos {

    private RangoDeLegajos() {
    }

    /**
     * Devuelve la porción del padrón con legajos entre {@code desde} y
     * {@code hasta}, ambos inclusive. El resultado es una vista del
     * TreeMap original, ordenada por legajo.
     *
     * @param legajos padrón legajo → nombre, ordenado por legajo
     * @param desde   primer legajo del rango (inclusive)
     * @param hasta   último legajo del rango (inclusive)
     * @return sub-diccionario con los legajos del rango
     */
    public static SortedMap<Integer, String> enRango(TreeMap<Integer, String> legajos,
                                                     int desde, int hasta) {
        // subMap con banderas: true = inclusive en ambos extremos.
        return legajos.subMap(desde, true, hasta, true);
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> legajos = new TreeMap<>();
        legajos.put(101, "Ana");
        legajos.put(205, "Beto");
        legajos.put(310, "Carla");
        legajos.put(412, "Diego");
        System.out.println("Padrón: " + legajos);
        System.out.println("Entre 200 y 400: " + enRango(legajos, 200, 400));
    }
}
