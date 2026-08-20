package ar.uba.fi.cb100.guia.i08_arboles.i02_facil.e04;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * e04: consulta por rango de legajos. La gran ventaja de un {@link TreeMap}
 * sobre un {@code HashMap} es que permite consultas por rango: {@code subMap}
 * devuelve una vista con todas las claves entre dos límites, sin recorrer
 * todo el mapa.
 */
public final class RangoDeLegajos {

    private RangoDeLegajos() {
    }

    /**
     * Devuelve los alumnos cuyo legajo está entre {@code desde} y {@code hasta},
     * ambos inclusive.
     *
     * @param porLegajo mapa legajo → nombre del alumno
     * @param desde     primer legajo del rango (inclusive)
     * @param hasta     último legajo del rango (inclusive)
     * @return vista ordenada con los alumnos del rango
     */
    public static SortedMap<Integer, String> enRango(TreeMap<Integer, String> porLegajo,
                                                     int desde, int hasta) {
        // subMap con los dos límites inclusive: [desde, hasta].
        return porLegajo.subMap(desde, true, hasta, true);
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> alumnos = new TreeMap<>();
        alumnos.put(101234, "Rocío");
        alumnos.put(102500, "Tomás");
        alumnos.put(103800, "Valentina");
        alumnos.put(105999, "Nahuel");
        System.out.println("Alumnos: " + alumnos);
        System.out.println("Legajos 102000..104000: " + enRango(alumnos, 102000, 104000));
    }
}
