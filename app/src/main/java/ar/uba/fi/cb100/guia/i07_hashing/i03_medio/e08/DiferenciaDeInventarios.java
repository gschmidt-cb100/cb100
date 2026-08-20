package ar.uba.fi.cb100.guia.i07_hashing.i03_medio.e08;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * e08: comparar dos "fotos" de un inventario (ayer y hoy) y quedarse
 * sólo con los cambios. Un producto que falta en uno de los dos mapas
 * cuenta como cantidad 0: así un alta aparece con delta positivo y una
 * baja con delta negativo. La unión de claves se arma con un HashSet.
 */
public final class DiferenciaDeInventarios {

    private DiferenciaDeInventarios() {
    }

    /**
     * Calcula la diferencia de stock por producto entre ayer y hoy.
     * Sólo se incluyen los productos cuya cantidad cambió (delta distinto de 0).
     * Un producto ausente en alguno de los mapas cuenta como 0.
     *
     * @param ayer mapa producto → cantidad de ayer
     * @param hoy  mapa producto → cantidad de hoy
     * @return mapa producto → (hoy − ayer), sólo con deltas distintos de 0
     */
    public static Map<String, Integer> cambios(Map<String, Integer> ayer, Map<String, Integer> hoy) {
        // Unión de productos: los de ayer y los de hoy, sin repetir.
        Set<String> productos = new HashSet<>(ayer.keySet());
        productos.addAll(hoy.keySet());

        Map<String, Integer> deltas = new HashMap<>();
        for (String producto : productos) {
            // getOrDefault: si el producto no está, su cantidad es 0.
            int delta = hoy.getOrDefault(producto, 0) - ayer.getOrDefault(producto, 0);
            if (delta != 0) {
                deltas.put(producto, delta);
            }
        }
        return deltas;
    }

    public static void main(String[] args) {
        Map<String, Integer> ayer = Map.of("yerba", 10, "azúcar", 5, "café", 3);
        Map<String, Integer> hoy = Map.of("yerba", 7, "azúcar", 5, "galletitas", 12);
        System.out.println("Ayer: " + ayer);
        System.out.println("Hoy:  " + hoy);
        System.out.println("Cambios: " + cambios(ayer, hoy));
    }
}
