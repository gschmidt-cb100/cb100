package ar.uba.fi.cb100.guia.i07_hashing.i02_facil.e04;

import java.util.HashMap;
import java.util.Map;

/**
 * e04: inventario de productos con stock, encapsulando un {@link Map}.
 * A diferencia de los ejercicios de métodos estáticos, acá el mapa es
 * el <em>estado interno</em> de un objeto: un TDA construido sobre un diccionario.
 */
public class Inventario {

    /** Estado interno: producto → cantidad en stock. */
    private final Map<String, Integer> stock = new HashMap<>();

    /**
     * Agrega {@code cant} unidades de {@code producto} al inventario.
     * Si el producto no existía, lo da de alta.
     *
     * @param producto nombre del producto
     * @param cant     cantidad a agregar (debe ser positiva)
     */
    public void agregarStock(String producto, int cant) {
        if (cant <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva: " + cant);
        }
        stock.merge(producto, cant, Integer::sum);
    }

    /**
     * Quita {@code cant} unidades de {@code producto}.
     *
     * @param producto nombre del producto
     * @param cant     cantidad a quitar (debe ser positiva)
     * @throws IllegalArgumentException si el producto no existe o no alcanza el stock
     */
    public void quitarStock(String producto, int cant) {
        if (cant <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva: " + cant);
        }
        int actual = stockDe(producto);
        if (actual < cant) {
            throw new IllegalArgumentException(
                    "Stock insuficiente de '" + producto + "': hay " + actual + ", se piden " + cant);
        }
        if (actual == cant) {
            // Si queda en cero lo sacamos del mapa: no ocupa lugar de más.
            stock.remove(producto);
        } else {
            stock.put(producto, actual - cant);
        }
    }

    /**
     * Devuelve el stock actual de {@code producto}, o 0 si no está en el inventario.
     *
     * @param producto nombre del producto
     * @return cantidad disponible
     */
    public int stockDe(String producto) {
        return stock.getOrDefault(producto, 0);
    }

    public static void main(String[] args) {
        Inventario inv = new Inventario();
        inv.agregarStock("yerba", 10);
        inv.agregarStock("yerba", 5);
        inv.quitarStock("yerba", 8);
        System.out.println("Stock de yerba: " + inv.stockDe("yerba"));
        System.out.println("Stock de azucar: " + inv.stockDe("azucar"));
    }
}
